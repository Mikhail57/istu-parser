package ru.mustakimov.istuparser.controller

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import ru.mustakimov.istuparser.model.Teacher
import java.io.StringReader

@RestController
@RequestMapping("/schedule")
class ScheduleController(private val restTemplate: RestTemplate) {

    @GetMapping("teachers")
    fun getTeachers(): List<Teacher> {
        val entity = restTemplate.getForEntity<String>("http://www.istu.edu/schedule/export/schedule_prep_exp.txt")
        val body = entity.body ?: throw Exception("TROLOLO")
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

}