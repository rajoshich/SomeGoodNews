package com.example.somegoodnews

import CustomPageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.somegoodnews.Fragments.CategoryFragment
import com.example.somegoodnews.Fragments.NewsListFragment
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.Managers.OnArticleClickListener
import com.example.somegoodnews.Managers.OnUpdateListListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_layout.*

class MainActivity : AppCompatActivity(), OnUpdateListListener, OnArticleClickListener {

    lateinit var pageAdapter: CustomPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as SGNApp).dataManager.onUpdateListListener = this

        // Stuff to setup tab layout thing
        setSupportActionBar(toolbarMain)
        pageAdapter = CustomPageAdapter(supportFragmentManager)
        // Add all the fragments needed for tab layout
        pageAdapter.addFragments(NewsListFragment(), "All Articles")
        pageAdapter.addFragments(CategoryFragment(), "Categories")
        viewPager.adapter = pageAdapter
        tabLayout.setupWithViewPager(viewPager)

        // Add news list fragment
//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.mainActivity, NewsListFragment.getInstance(), NewsListFragment.TAG)
//            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            .commit()
    }

    override fun onUpdateList() {
        val newsListFragment = supportFragmentManager.findFragmentByTag(NewsListFragment.TAG) as NewsListFragment
        newsListFragment.updateList()
    }

    override fun onArticleClicked(newsArticle: NewsArticle) {
        TODO("Not yet implemented")
    }
}
