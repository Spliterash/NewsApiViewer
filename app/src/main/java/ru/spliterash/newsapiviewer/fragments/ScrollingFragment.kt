package ru.spliterash.newsapiviewer.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.scrolling_fragment.*
import ru.spliterash.newsapiviewer.R
import ru.spliterash.newsapiviewer.ResponseManager
import ru.spliterash.newsapiviewer.adapters.NewsAdapter
import ru.spliterash.newsapiviewer.datamodels.NewsApiResponse
import ru.spliterash.newsapiviewer.objects.IOnArticleClick


class ScrollingFragment : Fragment(R.layout.scrolling_fragment), IOnArticleClick {
    private lateinit var adapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        toolbar.title = getString(R.string.header)
        toolbar.inflateMenu(R.menu.main_menu)

        ResponseManager.instance.apply {
            createAdapter(list)
            loadIfNotExists(adapter)

            toolbar.menu.getItem(0).setOnMenuItemClickListener {
                if (loadInProgress) {
                    Snackbar.make(
                        view,
                        "Вы не можете обновить, пока идёт загрузка",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else
                    ResponseManager.instance.reload(adapter)
                true
            }
        }
    }


    private fun createAdapter(list: MutableList<NewsApiResponse.Article?>) {
        val layoutManager = LinearLayoutManager(context)
        recycler.layoutManager = layoutManager
        adapter = NewsAdapter(list, this)
        recycler.adapter = adapter
    }

    override fun onClick(article: NewsApiResponse.Article) {
        findNavController().navigate(
            ScrollingFragmentDirections.actionScrollingFragmentToNewsFragment(
                article
            )
        )
    }
}