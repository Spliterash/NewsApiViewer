package ru.spliterash.newsapiviewer.datamodels


import ru.spliterash.newsapiviewer.objects.Utils
import java.io.Serializable
import java.util.*

data class NewsApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>,
) : Serializable {
    data class Article(
        val title: String,
        val description: String?,
        val url: String,
        val urlToImage: String?,
        val publishedAt: Date
    ) : Serializable {
        fun cropDescription(maxSymbols: Int): String {
            return if (description == null)
                ""
            else
                Utils.cropText(description, maxSymbols)

        }

        fun howLongAgo(): String {
            return Utils.formatTime(publishedAt)
        }
    }
}
