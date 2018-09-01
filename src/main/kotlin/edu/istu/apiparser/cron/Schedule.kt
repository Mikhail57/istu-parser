package edu.istu.apiparser.cron

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import edu.istu.apiparser.model.Class
import edu.istu.apiparser.repository.ScheduleWrapper
import java.io.StringReader

@Component
class Schedule(private val restTemplate: RestTemplate, private val wrapper: ScheduleWrapper) {
    @Scheduled(cron = "0 */5 * * * *")
    fun getSchedule() {
        val entity = restTemplate.getForEntity<String>("http://www.istu.edu/schedule/export/schedule_exp.txt")
        val body = entity.body ?: throw Exception("Server response error")
        val reader = CSVReaderBuilder(StringReader(body))
                .withCSVParser(CSVParserBuilder().withSeparator('|').build())
                .build()
        val headers = reader.readNext().map { it.trim() }
        reader.readNext()
        wrapper.schedule = reader.filter {
            it.size == headers.size
        }.map {
            it.map(String::trim)
        }.map {
            val day = it[0].toInt()
            val everyWeek = it[1].toInt()
            val beginTime = it[2]
            val endTime = it[3]
            val aud = it[4]
            val prep = it[5]
            val title = it[6]
            val type = it[7]
            val faculty = it[8]
            val group = it[9]
            val course = it[10].toInt()
            val ngroup = it[11]
            val dayBegin = it[12]
            val dayEnd = it[13]
            val prepId = it[14]
            Class(title, aud, group, prep, prepId, beginTime, endTime, course, faculty, dayBegin, dayEnd, type, day, everyWeek)
        }
        println("--------------------------")
        println("       GOT SCHEDULE       ")
        println("--------------------------")
    }
}