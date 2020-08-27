package ru.spliterash.newsapiviewer.datamodels


import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import kotlinx.android.synthetic.main.article.view.*
import ru.spliterash.newsapiviewer.R
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

        fun fillArticle(view: View, cropText: Boolean) {
            view.title.text = title
            view.description.text =
                if (cropText) this.cropDescription(100) else this.description
            view.date.text = this.howLongAgo()
            val factor = view.context.resources.displayMetrics.density
            view.imageView.layoutParams.height = (factor * 80).toInt()
            Glide
                .with(view.context)
                .load(this.urlToImage)
                .placeholder(
                    ColorDrawable(
                        ContextCompat.getColor(
                            view.context,
                            R.color.secondary
                        )
                    )
                )
                .into(object : ImageViewTarget<Drawable>(view.imageView) {
                    override fun setResource(resource: Drawable?) {
                        if (resource != null) {
                            view.imageView.setImageDrawable(resource)
                            view.imageView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                        }
                    }
                })
        }
    }
}
