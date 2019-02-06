package edu.istu.apiparser.repository

import edu.istu.apiparser.model.Category
import edu.istu.apiparser.model.Image
import edu.istu.apiparser.model.NewsPost
import org.jsoup.Jsoup
import kotlin.streams.toList

class NewsNetworkRepository {
    fun getNews(page: Int): List<NewsPost> {
        val webpage = Jsoup.connect("https://www.istu.edu/news/?PAGEN_1=$page").get()
        val news = webpage.select(".newslist-item")
        return news.stream().parallel().map { element ->
            val titleLinkElem = element.selectFirst("h4 a")
            val id = titleLinkElem.attr("href").split("/")[2].toLong()
            getNewsPost(id)
        }.toList()
    }

    fun getNewsPost(postId: Long): NewsPost {
        val url = "https://www.istu.edu/news/$postId"
        val document = Jsoup.connect(url).get()
        val allPageContent = document.selectFirst(".content").children()
        val pageContent = allPageContent.next().next()
        val content = pageContent.not(".img-gall")
        val gallery = pageContent.select(".img-gall")
        val images = if (gallery.size > 0) gallery[0].children().map { element ->
            when {
                element.`is`("a") -> Image(
                        full = element.absUrl("href"),
                        mini = element.children().first()?.absUrl("src") ?: TODO("Error on post with id $postId")
                )
                element.`is`("img") -> Image(
                        full = element.absUrl("src"),
                        mini = element.absUrl("src")
                )
                else -> TODO("Unknown element at gallery of post $postId: $element")
            }
        } else listOf()
        val date = allPageContent.select(".new-date").text()
        val categories = allPageContent.select(".new-razdel-item").map { cat -> getCategory(cat.text()) }
        val title = allPageContent.select("h1").text()
        val titleImage = allPageContent.select(".news-header").select("img")[0].absUrl("src")
        return NewsPost(
                id = postId,
                url = url,
                title = title,
                date = date,
                categories = categories,
                content = content.html(),
                mainImageUrl = titleImage,
                images = images
        )
    }

    // Для добавления категорий необходимо добавлять их в конец списка
    private val categories = listOf("Общественная жизнь", "Инновации", "Наука",
            "Международная деятельность", "Образование", "Воспитание, культура, спорт, здоровье", "Студентам",
            "Об университете", "Спорт", "Абитуриентам", "Культура", "Сотрудникам и преподавателям",
            "Выпускникам и работодателям", "Национальный исследовательский университет", "Пресс-служба")

    private fun getCategory(title: String) = Category(categories.indexOf(title), title)
}