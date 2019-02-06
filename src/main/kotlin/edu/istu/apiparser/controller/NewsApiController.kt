package edu.istu.apiparser.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import edu.istu.apiparser.model.NewsPostShort
import edu.istu.apiparser.repository.NewsNetworkRepository
import edu.istu.apiparser.repository.NewsWrapper

@RestController
@RequestMapping("news")
class NewsApiController(private val newsWrapper: NewsWrapper) {

    private val newsRepository = NewsNetworkRepository()

    @GetMapping("")
    fun listNews(@RequestParam("page", defaultValue = "1") page: Int): List<NewsPostShort> {
        if (page > 0 && page <= NewsWrapper.CACHED_PAGES && newsWrapper.news.size >= page)
            return newsWrapper.news[page - 1]
        return newsRepository.getNews(page)
    }
}