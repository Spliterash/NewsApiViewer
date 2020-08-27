package ru.spliterash.newsapiviewer.objects

import ru.spliterash.newsapiviewer.MainActivity
import ru.spliterash.newsapiviewer.R
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
    }

    init {
        throw RuntimeException("Can't be initialized")
    }
}