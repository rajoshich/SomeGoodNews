package com.example.somegoodnews

import TabsAdapter
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Fragments.*
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.Listeners.OnArticleClickListener
import com.example.somegoodnews.Listeners.OnArticleLongClickListener
import com.example.somegoodnews.Listeners.OnUpdateListListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.newsarticle_fragment.*
import kotlinx.android.synthetic.main.tab_layout.*


class MainActivity : AppCompatActivity(),
    OnUpdateListListener,
    OnArticleClickListener,
    OnArticleLongClickListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.home -> {
                Log.i("yes", "testing home")
                item.setIcon(R.drawable.news_selected)
                fragmentContainer.visibility = GONE
                return@OnNavigationItemSelectedListener true
            }
            R.id.submit -> {
                var currentUser = (applicationContext as SGNApp).currentUser
                Log.i("yes", "main: " + currentUser.toString())
                if(currentUser == null) {
                    replaceFragment(UserLoginFragment.getInstance(), UserLoginFragment.TAG)
                } else {
                    // create submit fragment
                    Log.i("yes", "testing submit")
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.liked -> {
                var currentUser = (applicationContext as SGNApp).currentUser
                Log.i("yes", "main: " + currentUser.toString())
                if(currentUser == null) {
                    replaceFragment(UserLoginFragment.getInstance(), UserLoginFragment.TAG)
                } else {
                    val frag = supportFragmentManager.findFragmentByTag(LikedFragment.TAG) as? LikedFragment
                    if(frag == null) {
                        replaceFragment(LikedFragment(), "LIKEDFRAG")
                    } else {
                        frag.updateLiked()
                    }
                    Log.i("yes", "testing liked")
                }
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
        val frag = supportFragmentManager.findFragmentByTag(NewsArticleFragment.TAG) as? NewsArticleFragment
        if(frag == null) {

            replaceFragment(NewsListFragment(), "NEWSARTICLE")
        } else {
            frag.updateArticle()
        }

    }

    private fun replaceFragment(fragment: Fragment, tag: String?) {
        fragmentContainer.visibility = VISIBLE
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag)
        fragmentTransaction.commit()
    }
}
