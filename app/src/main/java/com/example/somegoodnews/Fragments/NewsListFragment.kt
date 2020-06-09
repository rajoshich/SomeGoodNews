package com.example.somegoodnews.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Listeners.OnArticleClickListener
import com.example.somegoodnews.Listeners.OnArticleLongClickListener
import com.example.somegoodnews.Managers.ArticlesAdapter
import com.example.somegoodnews.Listeners.OnUpdateListListener
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import kotlinx.android.synthetic.main.newslist_fragment.*

class NewsListFragment: Fragment() {
    private lateinit var articlesAdapter: ArticlesAdapter
    lateinit var app: SGNApp
    private var onUpdateListListener: OnUpdateListListener? = null
    var onArticleClickListener: OnArticleClickListener? = null
    var onArticleLongClickListener: OnArticleLongClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = (context?.applicationContext as SGNApp)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnUpdateListListener) {
            onUpdateListListener = context
        }

        if(context is OnArticleClickListener) {
            onArticleClickListener = context
        }

        if(context is OnArticleLongClickListener) {
            onArticleLongClickListener = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.newslist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articles = app.dataManager.articles
        articlesAdapter = ArticlesAdapter(articles, context)
        rvNewsList.adapter = articlesAdapter

        articlesAdapter.onArticleClickListener = {
            app.lastArticle = it
            onArticleClickListener?.onArticleClicked(it)
            Log.i("fuck", "news list click")

        }

        articlesAdapter.onArticleLongClickListener = { newsArticle: NewsArticle, i: Int ->
            onArticleLongClickListener?.likeArticle(newsArticle, i)
        }
        // Testing
        Log.i("fuck", "From fragment: $articles")
    }


    fun onUpdateList() {
        val articles = app.dataManager.articles
        articlesAdapter.change(articles)
        articlesAdapter.updateLikes(app.dataManager.likedArticlePos)
        // Testing
        val numArticles = articles.size
        Log.i("fuck", "Updated List, list now has $numArticles articles")

    }
}