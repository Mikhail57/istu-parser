package edu.istu.apiparser.controller

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import edu.istu.apiparser.model.Class
import edu.istu.apiparser.model.Faculty
import edu.istu.apiparser.model.Teacher
import edu.istu.apiparser.repository.ScheduleWrapper
import java.io.StringReader

@RestController
@RequestMapping("/schedule")
class ScheduleController(private val restTemplate: RestTemplate, private val wrapper: ScheduleWrapper) {

    @GetMapping("teachers")
    fun getTeachers(): List<Teacher> {
        val entity = restTemplate.getForEntity<String>("http://www.istu.edu/schedule/export/schedule_prep_exp.txt")
        val body = entity.body ?: throw Exception("Server response error")
        val reader = CSVReaderBuilder(StringReader(body))
                .withCSVParser(CSVParserBuilder().withSeparator('|').build())
                .build()
        val headers = reader.readNext().map { it.trim() }
        reader.readNext()
        val teachers = reader.filter {
            it.size == headers.size
        }.map {
            it.map { elem ->
                elem.trim()
            }
        }.map {
            Teacher(it[0].toLong(), it[2], it[1])
        }
        return teachers
    }

    @GetMapping("groups")
    fun getGroups(): List<Faculty> {
        return wrapper.schedule
                .groupBy { it.faculty }
                .mapValues {
                    it.value
                            .groupBy { it.course }
                            .mapValues { it.value.map { it.group }.toSet().toList() }
                }
                .map { Faculty(it.key, it.value) }
//        return wrapper.schedule.groupBy { it.course }.mapValues { it.value.map { it.group }.toSet().toList() }
    }

    @GetMapping("/group/{group}")
    fun getScheduleForGroup(@PathVariable(name = "group") group: String): List<Class> {
        return wrapper.schedule.filter { it.group == group }
    }


    @GetMapping("/group/all")
    fun getSchedule(): List<Class> {
        return wrapper.schedule
    }

}