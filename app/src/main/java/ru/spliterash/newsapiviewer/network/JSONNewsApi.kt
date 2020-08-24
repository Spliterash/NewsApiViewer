package ru.spliterash.newsapiviewer.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.spliterash.newsapiviewer.datamodels.NewsApiResponse

interface JSONNewsApi {
    @GET("/v2/top-headlines")
    fun topHeadlines(
        @Query("apiKey") key: String,
        @Query("country") country: String,
        @Query("pageSize") size: Int,
        @Query("page") page: Int
    ): Call<NewsApiResponse>
}