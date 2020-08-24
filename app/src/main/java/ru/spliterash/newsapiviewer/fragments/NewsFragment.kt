package ru.spliterash.newsapiviewer.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.news_view.*
import ru.spliterash.newsapiviewer.R
import ru.spliterash.newsapiviewer.objects.Utils


class NewsFragment : Fragment(R.layout.news_view) {
    private val args: NewsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.title = getString(R.string.view_news)
        toolbar.inflateMenu(R.menu.news_view_menu)
        toolbar.navigationIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_arrow_back, null)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        toolbar.menu.getItem(0).setOnMenuItemClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(args.article.url))
            startActivity(browserIntent)
            true
        }
        Utils.fillArticle(args.article, view, false)
    }
}