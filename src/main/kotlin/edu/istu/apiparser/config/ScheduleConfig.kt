package edu.istu.apiparser.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import edu.istu.apiparser.repository.ScheduleWrapper

@Configuration
class ScheduleConfig {

    @Bean
    fun getScheduleWrapper() = ScheduleWrapper(arrayListOf())
}