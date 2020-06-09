package com.example.somegoodnews

import TabsAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Fragments.*
import com.example.somegoodnews.Listeners.*
import com.example.somegoodnews.Managers.NewsArticle
import com.firebase.ui.auth.data.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_article.*
import kotlinx.android.synthetic.main.newsarticle_fragment.*
import kotlinx.android.synthetic.main.tab_layout.*


class MainActivity : AppCompatActivity(),
    OnUpdateListListener,
    OnUpdateLikes,
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
                // create submit fragment
                checkSignedIn()
                replaceFragment(SubmitNewsFragment.getInstance(), SubmitNewsFragment.TAG)
                Log.i("yes", "testing submit")
                return@OnNavigationItemSelectedListener true
            }
            R.id.liked -> {
                checkSignedIn()
                val frag = supportFragmentManager.findFragmentByTag(LikedFragment.TAG) as? LikedFragment
                fragmentContainer.visibility = VISIBLE
                if(frag == null) {
                    replaceFragment(LikedFragment(), LikedFragment.TAG)
                } else {
                    frag.updateLiked()
                }
                Log.i("yes", "testing liked")
                return@OnNavigationItemSelectedListener true
            }
            R.id.profile -> {
                replaceFragment(UserLoginFragment(), UserLoginFragment.TAG)
                fragmentContainer.visibility = VISIBLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    lateinit var pageAdapter: TabsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set listeners
        val app = (applicationContext as SGNApp)
        app.dataManager.onUpdateListListener = this
        app.dataManager.onUpdateLikes = this
        // Stuff to setup tab layout thing
        setSupportActionBar(toolbarMain)
        pageAdapter = TabsAdapter(supportFragmentManager)
        // Add all the fragments needed for tab layout
        pageAdapter.addFragments(NewsListFragment(), "All Articles")
        pageAdapter.addFragments(CategoryFragment(), "Categories")
        viewPager.adapter = pageAdapter
        tabLayout.setupWithViewPager(viewPager)
        // bottom nav bar
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Immediately prompt user to log in
        replaceFragment(UserLoginFragment.getInstance(), UserLoginFragment.TAG)
    }

    override fun onUpdateList() {
        // Gets the newsListFragment from the viewpager since there is no TAG
        val newsListFragment = pageAdapter.instantiateItem(viewPager, viewPager.currentItem) as NewsListFragment
        newsListFragment.onUpdateList()

        Log.i("fuck", "Main update list called")
    }

    private fun replaceFragment(fragment: Fragment, tag: String?) {
        fragmentContainer.visibility = VISIBLE
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag)
        fragmentTransaction.commit()
    }

    // When article is clicked
    override fun onArticleClicked(newsArticle: NewsArticle) {
        val frag = supportFragmentManager.findFragmentByTag(NewsArticleFragment.TAG) as? NewsArticleFragment
        if(frag == null) {
            replaceFragment(NewsArticleFragment(), NewsArticleFragment.TAG)
            Log.i("fuck", "NEW")
        } else {
            fragmentContainer.visibility = VISIBLE
            frag.updateArticle()
            Log.i("fuck", "OLD")
        }

    }

    // Like article when long clicked or like button is clicked
    override fun likeArticle(newsArticle: NewsArticle, pos: Int) {
        val app = (applicationContext as SGNApp)
        val currUser = app.currentUser?.email
        if(currUser != null) {
            app.dataManager.likeArticle(newsArticle, pos, currUser, this)
        } else {
            Toast.makeText(this, "Not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    override fun updateLikesList() {
        val frag = supportFragmentManager.findFragmentByTag(LikedFragment.TAG) as? LikedFragment
        if(frag == null) {
            Log.i("fuck", "Main update null")
        }
        frag?.updateLiked()
        Log.i("fuck", "Main update")
    }

    private fun checkSignedIn() {
        val currentUser = (applicationContext as SGNApp).currentUser
        if(currentUser == null) {
            var frag = supportFragmentManager.findFragmentByTag(UserLoginFragment.TAG) as? UserLoginFragment
            if(frag == null) {
                frag = UserLoginFragment()
            }
            replaceFragment(frag, UserLoginFragment.TAG)
        }
    }
}
