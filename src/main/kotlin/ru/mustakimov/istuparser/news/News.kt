package ru.mustakimov.istuparser.news

data class News(
        val id: Long,
        val title: String,
        val date: String,
        val categories: List<Category>,
        val url: String,
        val image: String
)