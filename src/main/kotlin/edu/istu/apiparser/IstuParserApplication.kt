package edu.istu.apiparser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class IstuParserApplication

fun main(args: Array<String>) {
    runApplication<IstuParserApplication>(*args)
}
