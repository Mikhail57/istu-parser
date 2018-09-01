package edu.istu.apiparser.repository

import edu.istu.apiparser.model.Category
import edu.istu.apiparser.model.News
import org.jsoup.Jsoup
import kotlin.streams.toList

class NewsNetworkRepository {
    fun getNews(page: Int): List<News> {
        val webpage = Jsoup.connect("https://www.istu.edu/news/?PAGEN_1=$page").get()
        val news = webpage.select(".newslist-item")
        val lst = news.stream().parallel().map {
            val doc = Jsoup.connect(it.selectFirst("h4 a").absUrl("href")).get()
            doc.selectFirst(".content").children().next().next().text()
        }.toList().map { if (it.length > 128) it.substring(0, 128) else it }
        return news.mapIndexed { index, element ->
            val titleLinkElem = element.selectFirst("h4 a")
            News(
                    id = titleLinkElem.attr("href").split("/")[2].toLong(),
                    title = titleLinkElem.text(),
                    date = element.selectFirst(".news-date").text(),
                    categories = element.select(".new-razdel-item").map { cat -> getCategory(cat.text()) },
                    url = titleLinkElem.absUrl("href"),
                    image = element.selectFirst(".listnews-img").absUrl("src"),
                    shortDesc = lst[index])
        }
    }

    // Для добавления категорий необходимо добавлять их в конец списка
    private val categories = listOf("Общественная жизнь", "Инновации", "Наука",
            "Международная деятельность", "Образование", "Воспитание, культура, спорт, здоровье", "Студентам",
            "Об университете", "Спорт", "Абитуриентам", "Культура", "Сотрудникам и преподавателям",
            "Выпускникам и работодателям", "Национальный исследовательский университет", "Пресс-служба")

    private fun getCategory(title: String) = Category(categories.indexOf(title), title)
}