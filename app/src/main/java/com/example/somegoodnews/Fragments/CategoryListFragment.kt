package com.example.somegoodnews.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Listeners.OnArticleClickListener
import com.example.somegoodnews.Listeners.OnUpdateListListener
import com.example.somegoodnews.Managers.ArticlesAdapter
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import kotlinx.android.synthetic.main.newslist_fragment.*

class CategoryListFragment : Fragment() {
    private lateinit var articlesAdapter: ArticlesAdapter
    lateinit var app: SGNApp
    private var onUpdateListListener: OnUpdateListListener? = null
    var onArticleClickListener: OnArticleClickListener? = null
    private val categoryArticles: MutableList<NewsArticle> = mutableListOf()
    companion object {
        const val TAG = "CATEGORY_LIST_FRAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = (context?.applicationContext as SGNApp)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnUpdateListListener) {
            onUpdateListListener = context
        }
        if (context is OnArticleClickListener) {
            onArticleClickListener = context
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

        getCategoryArticles()
        articlesAdapter = ArticlesAdapter(categoryArticles, context, TAG)
        rvNewsList.adapter = articlesAdapter

        articlesAdapter.onArticleClickListener = {
            app.lastArticle = it
            onArticleClickListener?.onArticleClicked(it)
        }

    }

    private fun getCategoryArticles() {
        val category = arguments?.get("CATEGORY")
        val allArticles = app.dataManager.articles
        for (article in allArticles) {
            if (article.category.trim() == category) {
                categoryArticles.add(article)
            }
        }
    }
}

