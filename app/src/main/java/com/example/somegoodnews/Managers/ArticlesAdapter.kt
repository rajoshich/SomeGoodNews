package com.example.somegoodnews.Managers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.somegoodnews.R

class ArticlesAdapter(allArticles: HashMap<String, NewsArticle>): RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    private var allArticles: HashMap<String, NewsArticle> = allArticles
    var onArticleClickListener: ((article: NewsArticle) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allArticles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currArticle = allArticles[position]
        holder.bind(currArticle)
    }

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(article: NewsArticle) {
            itemView.setOnClickListener {
                onArticleClickListener?.invoke(article)
            }
        }

    }
}