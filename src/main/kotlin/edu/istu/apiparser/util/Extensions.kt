package edu.istu.apiparser.util

import edu.istu.apiparser.model.NewsPost
import edu.istu.apiparser.model.NewsPostShort
import org.jsoup.Jsoup
import java.lang.Math.min

fun NewsPost.toShort(): NewsPostShort {
    val fullDescription = Jsoup.parse(this.content, this.url).text()
    val shortDesc = fullDescription.substring(0, min(fullDescription.length, 300)) + "..."
    return NewsPostShort(
            id = this.id,
            categories = this.categories,
            date = this.date,
            image = this.mainImageUrl,
            title = this.title,
            url = this.url,
            shortDesc = shortDesc
    )
}