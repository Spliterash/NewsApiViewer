package ru.spliterash.newsapiviewer

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Только запускает приложение
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this.applicationContext
        setContentView(R.layout.activity_main)
    }

    companion object {
        private lateinit var context: Context
        fun getResources(): Resources = context.resources
    }
}