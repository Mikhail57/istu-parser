package edu.istu.apiparser.repository

import edu.istu.apiparser.model.NewsPostShort

class NewsWrapper(
        @Volatile
        var news: List<List<NewsPostShort>>
) {
    companion object {
        const val CACHED_PAGES = 15
    }
}