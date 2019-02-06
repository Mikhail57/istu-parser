package edu.istu.apiparser.repository

import edu.istu.apiparser.model.NewsPost

class NewsWrapper(
        @Volatile
        var news: List<List<NewsPost>>
) {
    companion object {
        const val CACHED_PAGES = 15
    }
}