package com.example.somegoodnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.somegoodnews.Fragments.NewsListFragment
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.Managers.OnArticleClickListener
import com.example.somegoodnews.Managers.OnUpdateListListener

class MainActivity : AppCompatActivity(), OnUpdateListListener, OnArticleClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (applicationContext as SGNApp).dataManager.onUpdateListListener = this
        // Add news list fragment
        supportFragmentManager
            .beginTransaction()
            .add(R.id.mainActivity, NewsListFragment.getInstance(), NewsListFragment.TAG)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    override fun onUpdateList() {
        val newsListFragment = supportFragmentManager.findFragmentByTag(NewsListFragment.TAG) as NewsListFragment
        newsListFragment.updateList()
    }

    override fun onArticleClicked(newsArticle: NewsArticle) {
        TODO("Not yet implemented")
    }
}
