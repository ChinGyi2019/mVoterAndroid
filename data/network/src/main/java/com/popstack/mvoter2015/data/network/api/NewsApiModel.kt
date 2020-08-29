package com.popstack.mvoter2015.data.network.api

import com.popstack.mvoter2015.domain.news.model.News
import com.popstack.mvoter2015.domain.news.model.NewsId
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@JsonClass(generateAdapter = true)
data class GetNewsListResponse(
  @Json(name = "data") val data: List<NewsApiModel>
)

@JsonClass(generateAdapter = true)
data class NewsApiModel(
  @Json(name = "id") val newsId: String,
  @Json(name = "attributes") val attributes: NewsApiAttributes
) {

  fun mapToNews(): News {
    return News(
      id = NewsId(newsId),
      title = attributes.title,
      summary = attributes.summary,
      body = attributes.summary, //TODO: Change to body after API is done
      publishedDate = LocalDate.parse(attributes.publishedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)),
      imageUrl = attributes.previewImage,
      url = attributes.url
    )
  }
}

@JsonClass(generateAdapter = true)
data class NewsApiAttributes(
  @Json(name = "title") val title: String,
  @Json(name = "summary") val summary: String,
  @Json(name = "published_date") val publishedDate: String,
  @Json(name = "preview_image") val previewImage: String?,
  @Json(name = "url") val url: String
)