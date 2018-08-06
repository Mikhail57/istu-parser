package ru.mustakimov.istuparser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IstuParserApplication

fun main(args: Array<String>) {
    runApplication<IstuParserApplication>(*args)
}
