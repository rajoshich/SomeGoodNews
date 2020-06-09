package com.example.somegoodnews.Managers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import com.squareup.picasso.Picasso

class ArticlesAdapter(allArticles: List<NewsArticle>, context: Context?, frag: String?): RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {
    private var allArticles: List<NewsArticle> = allArticles.toList()
    private var likedArticles: List<NewsArticle>? = null
    private val context = context
    private val isFrag: String? = frag
    var onArticleClickListener: ((article: NewsArticle) -> Unit)? = null
    var onArticleLongClickListener: ((article: NewsArticle, position: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allArticles.size
    }
    fun change(newArticles: List<NewsArticle>) {
        updateLikes()
        val callback =
            ArticleDiffCallback(allArticles, newArticles, likedArticles)
        val diffRes = DiffUtil.calculateDiff(callback)
        diffRes.dispatchUpdatesTo(this)
        // Testing
        Log.i("poopy", "old: $allArticles, new: $newArticles")
        allArticles = newArticles
    }
    fun updateLikes() {
        this.likedArticles = (context?.applicationContext as SGNApp).dataManager.likedArticles
        Log.i("poopy", "liked articles: " + this.likedArticles.toString())
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currArticle = allArticles[position]
        holder.bind(currArticle, position)
    }

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvNewsHeading by lazy {itemView.findViewById<TextView>(R.id.tvArticleHeadline)}
        private val tvNewsCategory by lazy {itemView.findViewById<TextView>(R.id.tvArticleCategory)}
        private val tvNewsSource by lazy {itemView.findViewById<TextView>(R.id.tvArticleSource)}
        private val ivArticleImage by lazy {itemView.findViewById<ImageView>(R.id.ivArticleImage)}
        private val ibLikeArticle by lazy {itemView.findViewById<Button>(R.id.ibLikeArticle)}
        fun bind(article: NewsArticle, position: Int) {
            tvNewsHeading.text = article.headline
            tvNewsCategory.text = article.category
            tvNewsSource.text = article.source
            if(!article.img.isNullOrEmpty()) {
                Picasso.get().load(article.img).into(ivArticleImage)
            }
            ibLikeArticle.setBackgroundResource(R.drawable.btn_rounded)
            ibLikeArticle.text = "ADD TO LIKED"
            if(likedArticles != null) {
                likedArticles?.forEach {
                    if(it.headline == article.headline) {
                        Log.i("poopy", it.headline + " liked article")
                        context?.let {
                            // TODO: Ashmann: I thought this was your code to show that the post has been liked already
                            ibLikeArticle.setBackgroundResource(R.drawable.clicked_btn_rounded)
                            ibLikeArticle.text = "REMOVE FROM LIKED"
                        }
                    }
                }
            }
            if(isFrag != null) {
                ibLikeArticle.visibility = View.GONE
            }

            itemView.setOnClickListener {
                onArticleClickListener?.invoke(article)
            }
            itemView.setOnLongClickListener {
                onArticleLongClickListener?.invoke(article, position)
                true
            }
            ibLikeArticle.setOnClickListener {
                onArticleLongClickListener?.invoke(article, position)
            }

        }

    }
}