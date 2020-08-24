package ru.spliterash.newsapiviewer.objects

import ru.spliterash.newsapiviewer.datamodels.NewsApiResponse

interface IOnArticleClick {
    fun onClick(article: NewsApiResponse.Article)

}
