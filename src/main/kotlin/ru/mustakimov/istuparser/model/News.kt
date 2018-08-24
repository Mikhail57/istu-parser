package ru.mustakimov.istuparser.model

data class News(
        val id: Long,
        val title: String,
        val date: String,
        val categories: List<Category>,
        val url: String,
        val image: String
)