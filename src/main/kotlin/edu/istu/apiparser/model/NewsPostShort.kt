package edu.istu.apiparser.model

data class NewsPostShort(
        val id: Long,
        val title: String,
        val date: String,
        val categories: List<Category>,
        val url: String,
        val image: String,
        val shortDesc: String
)