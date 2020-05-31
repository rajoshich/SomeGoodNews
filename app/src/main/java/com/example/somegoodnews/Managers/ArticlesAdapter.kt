package com.example.somegoodnews.Managers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.somegoodnews.R

class ArticlesAdapter(allArticles: List<NewsArticle>): RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    private var allArticles: List<NewsArticle> = allArticles.toList()
    var onArticleClickListener: ((article: NewsArticle) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allArticles.size
    }
    fun change(newArticles: List<NewsArticle>) {
        val callback =
            ArticleDiffCallback(allArticles, newArticles)
        val diffRes = DiffUtil.calculateDiff(callback)
        diffRes.dispatchUpdatesTo(this)
        // Testing
        Log.i("fuck", "old: $allArticles, new: $newArticles")

        allArticles = newArticles
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currArticle = allArticles[position]
        holder.bind(currArticle)
    }

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvNewsHeading by lazy {itemView.findViewById<TextView>(R.id.tvArticleHeadline)}
        private val tvNewsCategory by lazy {itemView.findViewById<TextView>(R.id.tvArticleCategory)}
        private val tvNewsSource by lazy {itemView.findViewById<TextView>(R.id.tvArticleSource)}
        fun bind(article: NewsArticle) {
            tvNewsHeading.text = article.headline
            tvNewsCategory.text = article.category
            tvNewsSource.text = article.source
            itemView.setOnClickListener {
                onArticleClickListener?.invoke(article)
            }
        }

    }
}