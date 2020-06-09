package com.example.somegoodnews.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Listeners.OnUpdateListListener
import com.example.somegoodnews.Managers.ArticlesAdapter
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import kotlinx.android.synthetic.main.newslist_fragment.*

class CategoryListFragment: Fragment() {
    private lateinit var articlesAdapter: ArticlesAdapter
    lateinit var app: SGNApp
    private var onUpdateListListener: OnUpdateListListener? = null

    private val categoryArticles: MutableList<NewsArticle> = mutableListOf()
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
//        testSignIn.setOnClickListener {
//            startActivity(Intent(context, UserActivity::class.java))
//        }
         val category = arguments?.get("CATEGORY")
        Log.i("poop2", category as String)
        val allArticles = app.dataManager.articles
        Log.i("poop", allArticles.toString())
        for (article in allArticles) {
            if (article.category == category) {
                categoryArticles.add(article)
            }
        }

        articlesAdapter = ArticlesAdapter(categoryArticles)
        rvNewsList.adapter = articlesAdapter

        articlesAdapter.onArticleLongClickListener = { newsArticle: NewsArticle, i: Int ->
            Log.i("fuck", "category list long click")
        }
    }
}

