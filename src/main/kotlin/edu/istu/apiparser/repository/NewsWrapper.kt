package edu.istu.apiparser.repository

import edu.istu.apiparser.model.News

class NewsWrapper(
        @Volatile
        var news: List<List<News>>
) {
    companion object {
        const val CACHED_PAGES = 15
    }
}