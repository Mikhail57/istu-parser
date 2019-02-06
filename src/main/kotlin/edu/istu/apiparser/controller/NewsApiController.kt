package edu.istu.apiparser.controller

import edu.istu.apiparser.model.NewsPost
import edu.istu.apiparser.model.NewsPostShort
import edu.istu.apiparser.repository.NewsNetworkRepository
import edu.istu.apiparser.repository.NewsWrapper
import edu.istu.apiparser.util.toShort
import org.jsoup.Jsoup
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("news")
class NewsApiController(private val newsWrapper: NewsWrapper) {

    private val newsRepository = NewsNetworkRepository()

    @GetMapping("")
    fun listNews(@RequestParam("page", defaultValue = "1") page: Int): List<NewsPostShort> {
        if (page > 0 && page <= NewsWrapper.CACHED_PAGES && newsWrapper.news.size >= page)
            return newsWrapper.news[page - 1].map(NewsPost::toShort)
        return newsRepository.getNews(page).map(NewsPost::toShort)
    }

    @GetMapping("/{id}")
    fun getNewsPost(@PathVariable(value = "id") postId: Long): NewsPost? {
//        println("Getting post $postId")
        newsWrapper.news.forEach {
            val post = it.find { post -> (post.id == postId) }
            if (post != null) {
                return post
            }
        }
        return newsRepository.getNewsPost(postId)
    }
}