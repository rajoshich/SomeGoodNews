package com.example.somegoodnews.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Managers.ArticlesAdapter
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import kotlinx.android.synthetic.main.news_list.*

class NewsListFragment: Fragment() {
    private lateinit var articlesAdapter: ArticlesAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var articles = (context?.applicationContext as SGNApp).dataManager.articles
        articles.let {
            articlesAdapter = ArticlesAdapter(articles)
            rvNewsList.adapter = articlesAdapter
            articlesAdapter.onArticleClickListener = {
                // TO DO
            }
        }
    }
}