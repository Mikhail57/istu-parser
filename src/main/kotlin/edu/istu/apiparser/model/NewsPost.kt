package edu.istu.apiparser.model

data class NewsPost(
        val id: Long,
        val title: String,
        val date: String,
        val categories: List<Category>,
        val url: String,
        val mainImageUrl: String,
        val content: String,
        val images: List<Image>
)