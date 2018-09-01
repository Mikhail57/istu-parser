package edu.istu.apiparser.config

import edu.istu.apiparser.repository.NewsWrapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import edu.istu.apiparser.repository.ScheduleWrapper

@Configuration
class ScheduleConfig {

    @Bean
    fun getScheduleWrapper() = ScheduleWrapper(arrayListOf())

    @Bean
    fun getNewsWrapper() = NewsWrapper(arrayListOf())
}