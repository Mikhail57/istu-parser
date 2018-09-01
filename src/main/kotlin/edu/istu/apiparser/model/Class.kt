package edu.istu.apiparser.model

data class Class(
        val title: String,
        val room: String,
        val group: String,
        val teacher: String,
        val teacherId: String,
        val beginTime: String,
        val endTime: String,
        val course: Int,
        val faculty: String,
        val dayBegin: String,
        val dayEnd: String,
        val type: String,
        /*
        day 1-6, everyWeek 1 - только по нечетным
        day 1-6, everyWeek 2 - каждую неделю
        day 8-12, everyWeek 1 - только по четным
         */
        val day: Int,
        val everyWeek: Int
)