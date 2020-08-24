package ru.spliterash.newsapiviewer.adapters

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import kotlinx.android.synthetic.main.article.view.*
import ru.spliterash.newsapiviewer.R
import ru.spliterash.newsapiviewer.ResponseManager
import ru.spliterash.newsapiviewer.datamodels.NewsApiResponse
import ru.spliterash.newsapiviewer.objects.IOnArticleClick
import ru.spliterash.newsapiviewer.objects.ListNotify
import ru.spliterash.newsapiviewer.objects.Utils
import java.util.function.Consumer


class NewsAdapter(
    private val list: MutableList<NewsApiResponse.Article?>,
    private val callback: IOnArticleClick
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ListNotify {

    private class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView)
    private class LoadVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            ItemVH(LayoutInflater.from(parent.context).inflate(R.layout.article, parent, false))
        } else {
            LoadVH(
                LayoutInflater.from(parent.context).inflate(R.layout.items_loading, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == list.size - 1) {
            holder.itemView.post {
                ResponseManager.instance.loadNextPage(this)
            }
        }
        if (holder is ItemVH) {
            val element = list[position] ?: return
            holder.itemView.apply {
                Utils.fillArticle(element, this, true)
                setOnClickListener {
                    callback.onClick(element)
                }

            }
        }
    }


    override fun notifyChanged() {
        notifyDataSetChanged()
    }

    override fun startProgressBar() {
        list.add(null)
        notifyItemInserted(list.size - 1)
    }

    override fun stopProgressBar() {
        list.removeAt(list.size - 1)
        notifyItemRemoved(list.size - 1)
    }

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

}