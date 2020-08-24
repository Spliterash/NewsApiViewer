package ru.spliterash.newsapiviewer.objects

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import kotlinx.android.synthetic.main.article.view.*
import ru.spliterash.newsapiviewer.MainActivity
import ru.spliterash.newsapiviewer.R
import ru.spliterash.newsapiviewer.datamodels.NewsApiResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

@Suppress("MemberVisibilityCanBePrivate")
class Utils private constructor() {
    companion object {
        fun formatTime(publishedAt: Date): String {
            val secondSpend = ((System.currentTimeMillis() - publishedAt.time) / 1000).toInt()
            val minutes = floor(secondSpend / 60.toDouble()).toInt()
            val back = MainActivity.getResources().getString(R.string.back)
            if (minutes < 60) return MainActivity.getResources().getQuantityString(
                R.plurals.minutes,
                minutes,
                minutes
            ) + " " + back
            val hours = floor(minutes / 60.0).toInt()
            return if (hours < 24) {
                MainActivity.getResources()
                    .getQuantityString(R.plurals.hours, hours, hours) + " " + back
            } else getLocalizedTime(publishedAt)
        }

        fun getLocalizedTime(date: Date): String {
            val formatter = SimpleDateFormat("d MMMM", Locale.getDefault())
            formatter.timeZone = TimeZone.getDefault()
            return formatter.format(date)
        }

        fun cropText(text: String, maxSymbols: Int): String {
            if (maxSymbols < 1)
                throw RuntimeException("Max symbols can be bigger 0")
            if (text.length <= maxSymbols)
                return text
            val subText = text.substring(0, maxSymbols)
            val spaceIndex = subText.lastIndexOf(' ')
            if (spaceIndex == -1)
                return subText
            return "${subText.substring(0, spaceIndex)}..."
        }

        fun fillArticle(article: NewsApiResponse.Article, view: View, cropText: Boolean) {
            view.apply {
                title.text = article.title
                description.text =
                    if (cropText) article.cropDescription(100) else article.description
                date.text = article.howLongAgo()
                val factor = view.context.resources.displayMetrics.density
                imageView.layoutParams.height = (factor * 80).toInt()
                Glide
                    .with(context)
                    .load(article.urlToImage)
                    .placeholder(
                        ColorDrawable(
                            ContextCompat.getColor(
                                view.context,
                                R.color.secondary
                            )
                        )
                    )
                    .into(object : ImageViewTarget<Drawable>(imageView) {
                        override fun setResource(resource: Drawable?) {
                            if (resource != null) {
                                imageView.setImageDrawable(resource)
                                imageView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            }
                        }
                    })
            }
        }
    }

    init {
        throw RuntimeException("Can't be initialized")
    }
}