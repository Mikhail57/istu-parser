package ru.mustakimov.istuparser.controller

import org.jsoup.Jsoup
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.mustakimov.istuparser.model.Category
import ru.mustakimov.istuparser.model.News
import kotlin.streams.toList

@RestController
@RequestMapping("news")
class NewsApiController {

    @GetMapping("")
    fun listNews(@RequestParam("page", defaultValue = "1") page: Int): List<News> {
        val webpage = Jsoup.connect("https://www.istu.edu/news/?PAGEN_1=$page").get()
        val news = webpage.select(".newslist-item")
        val lst = news.stream().parallel().map {
            val doc = Jsoup.connect(it.selectFirst("h4 a").absUrl("href")).get()
            doc.selectFirst(".content").html()
        }.toList().map { "--------------------------\n$it\n--------------------------" }
        println(lst)
        return news.map {
            val titleLinkElem = it.selectFirst("h4 a")
            News(
                    id = titleLinkElem.attr("href").split("/")[2].toLong(),
                    title = titleLinkElem.text(),
                    date = it.selectFirst(".news-date").text(),
                    categories = it.select(".new-razdel-item").map { cat -> getCategory(cat.text()) },
                    url = titleLinkElem.absUrl("href"),
                    image = it.selectFirst(".listnews-img").absUrl("src"))
        }
    }


    // Для добавления категорий необходимо добавлять их в конец списка
    private val categories = listOf("Общественная жизнь", "Инновации", "Наука",
            "Международная деятельность", "Образование", "Воспитание, культура, спорт, здоровье", "Студентам",
            "Об университете", "Спорт", "Абитуриентам", "Культура", "Сотрудникам и преподавателям",
            "Выпускникам и работодателям", "Национальный исследовательский университет", "Пресс-служба")

    private fun getCategory(title: String) = Category(categories.indexOf(title), title)
}