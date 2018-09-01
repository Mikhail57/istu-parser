package edu.istu.apiparser.controller

import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import edu.istu.apiparser.model.Category
import edu.istu.apiparser.model.News
import edu.istu.apiparser.repository.NewsNetworkRepository
import edu.istu.apiparser.repository.NewsWrapper
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import kotlin.streams.toList

@RestController
@RequestMapping("news")
class NewsApiController(private val newsWrapper: NewsWrapper) {

    private val newsRepository = NewsNetworkRepository()

    @GetMapping("")
    fun listNews(@RequestParam("page", defaultValue = "1") page: Int): List<News> {
        if (page > 0 && page <= NewsWrapper.CACHED_PAGES && newsWrapper.news.size >= page)
            return newsWrapper.news[page - 1]
        return newsRepository.getNews(page)
    }
}