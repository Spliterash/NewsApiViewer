package ru.spliterash.newsapiviewer.objects

interface ListNotify{
    fun notifyChanged()
    fun startProgressBar()
    fun stopProgressBar()
}