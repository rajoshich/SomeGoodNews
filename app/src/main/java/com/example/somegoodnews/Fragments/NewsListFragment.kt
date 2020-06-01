package com.example.somegoodnews.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Managers.ArticlesAdapter
import com.example.somegoodnews.Listeners.OnUpdateListListener
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import kotlinx.android.synthetic.main.newslist_fragment.*

class NewsListFragment: Fragment() {
    private lateinit var articlesAdapter: ArticlesAdapter
    lateinit var app: SGNApp
    private var onUpdateListListener: OnUpdateListListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = (context?.applicationContext as SGNApp)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnUpdateListListener) {
            onUpdateListListener = context
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
        articlesAdapter = ArticlesAdapter(articles)
        rvNewsList.adapter = articlesAdapter

        articlesAdapter.onArticleClickListener = {
            // add new fragment??
            app.lastArticle = it
        }
        // Testing
        Log.i("fuck", "From fragment: $articles")
    }


    fun onUpdateList() {
        val articles = app.dataManager.articles
        articlesAdapter.change(articles)

        // Testing
        val numArticles = articles.size
        Log.i("fuck", "Updated List, list now has $numArticles articles")

    }
}