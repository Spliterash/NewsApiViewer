package ru.spliterash.newsapiviewer

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.spliterash.newsapiviewer.datamodels.NewsApiResponse
import ru.spliterash.newsapiviewer.network.NetworkService
import ru.spliterash.newsapiviewer.objects.ListNotify
import java.util.*
import kotlin.math.ceil

class ResponseManager private constructor() {

    val list: LinkedList<NewsApiResponse.Article?> = LinkedList<NewsApiResponse.Article?>();
    var elementCount: Int = -1
    var lastPage: Int = 0

    var loadInProgress: Boolean = false;

    @Suppress("MemberVisibilityCanBePrivate")
    fun loadNextPage(notify: ListNotify) {
        if (loadInProgress)
            return
        if (lastPage != 0 && lastPage >= getPageCount())
            return
        notify.startProgressBar()
        loadInProgress = true
        val newPage = lastPage + 1

        NetworkService.instance
            .getNews(newPage, object : Callback<NewsApiResponse> {
                override fun onResponse(
                    call: Call<NewsApiResponse>,
                    response: Response<NewsApiResponse>
                ) {
                    notify.stopProgressBar()
                    loadInProgress = false
                    response.body()?.apply {
                        lastPage = newPage
                        elementCount = this.totalResults
                        list.addAll(this.articles)
                        notify.notifyChanged()
                    }
                }

                override fun onFailure(call: Call<NewsApiResponse>, t: Throwable) {
                    notify.stopProgressBar()
                    loadInProgress = false
                }
            })
    }

    fun getPageCount(): Int =
        if (elementCount == -1) 0 else ceil(elementCount.toDouble() / NetworkService.PAGE_SIZE.toDouble()).toInt()

    fun loadIfNotExists(notify: ListNotify) {
        if (elementCount == -1)
            loadNextPage(notify)
    }

    fun reload(notify: ListNotify) {
        if (!loadInProgress) {
            list.clear()
            notify.notifyChanged()
            elementCount = -1
            lastPage = 0
            loadNextPage(notify)
        }
    }

    companion object {
        val instance: ResponseManager = ResponseManager()
    }

}