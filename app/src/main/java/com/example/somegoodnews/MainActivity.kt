package com.example.somegoodnews

import TabsAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Fragments.CategoryFragment
import com.example.somegoodnews.Fragments.NewsListFragment
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.Listeners.OnArticleClickListener
import com.example.somegoodnews.Listeners.OnUpdateListListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_layout.*


class MainActivity : AppCompatActivity(),
    OnUpdateListListener,
    OnArticleClickListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.home -> {
                item.setIcon(R.drawable.news_selected)
                Log.i("yes", "testing home")
                replaceFragment(NewsListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.submit -> {
                Log.i("yes", "testing submit")
                replaceFragment(NewsListFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.liked -> {
                Log.i("yes", "testing liked")
                //replaceFragment(NewsListFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    lateinit var pageAdapter: TabsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as SGNApp).dataManager.onUpdateListListener = this

        // Stuff to setup tab layout thing
        setSupportActionBar(toolbarMain)
        pageAdapter = TabsAdapter(supportFragmentManager)
        // Add all the fragments needed for tab layout
        pageAdapter.addFragments(NewsListFragment(), "All Articles")
        pageAdapter.addFragments(CategoryFragment(), "Categories")
        viewPager.adapter = pageAdapter
        tabLayout.setupWithViewPager(viewPager)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onUpdateList() {
        // Gets the newsListFragment from the viewpager since there is no TAG
        val newsListFragment = pageAdapter.instantiateItem(viewPager, viewPager.currentItem) as NewsListFragment
        newsListFragment.onUpdateList()

        Log.i("fuck", "Main update list called")
    }

    override fun onArticleClicked(newsArticle: NewsArticle) {
        TODO("Not yet implemented")
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
