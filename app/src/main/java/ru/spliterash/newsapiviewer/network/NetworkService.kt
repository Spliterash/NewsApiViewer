package ru.spliterash.newsapiviewer.network

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.spliterash.newsapiviewer.datamodels.NewsApiResponse

class NetworkService private constructor() {
    private val mRetrofit: Retrofit
    private val api: JSONNewsApi


    fun getNews(page: Int, callback: Callback<NewsApiResponse>): Unit = api
        .topHeadlines(
            "afacfbb0a9a942a8b99a388cf94c94f2",
            "ru",
            PAGE_SIZE,
            page
        )
        .enqueue(callback)


    companion object {
        // Для теста загрузки
        // Само собой в реальном приложении я бы использовал как минимум 50 ибо текстовые данные весят не особо много
        const val PAGE_SIZE = 20
        private const val BASE_URL = "https://newsapi.org"
        val instance = NetworkService()
    }

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = mRetrofit.create(JSONNewsApi::class.java)
    }
}