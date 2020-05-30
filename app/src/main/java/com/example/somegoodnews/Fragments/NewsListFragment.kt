package com.example.somegoodnews.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Managers.ArticlesAdapter
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import kotlinx.android.synthetic.main.news_list.*

class NewsListFragment: Fragment() {
    companion object {
        val TAG: String = NewsListFragment::class.java.simpleName
        fun getInstance() : NewsListFragment {
            return NewsListFragment()
        }

    }
    private lateinit var articlesAdapter: ArticlesAdapter
    lateinit var app: SGNApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = (context?.applicationContext as SGNApp)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.news_list, container, false)
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

    fun updateList() {
        val articles = app.dataManager.articles
        articlesAdapter.change(articles)

        // Testing
        val numArticles = articles.size
        Log.i("fuck", "Updated List, list now has $numArticles articles")

    }
}