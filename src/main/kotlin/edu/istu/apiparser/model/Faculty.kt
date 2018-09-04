package edu.istu.apiparser.model

data class Faculty(
        val title: String,
        val courses: Map<Int, List<String>>
)