package com.example.somegoodnews

import TabsAdapter
import android.app.Application
import com.example.somegoodnews.Managers.DataManager
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.Fragments.UserLoginFragment
import com.google.firebase.auth.FirebaseUser

class SGNApp: Application() {
    lateinit var dataManager: DataManager
    lateinit var userManager: UserLoginFragment
    var lastArticle: NewsArticle? = null
    var currentUser: FirebaseUser? = null
    lateinit var pagerAdapter: TabsAdapter
    override fun onCreate() {
        super.onCreate()
        dataManager = DataManager()
        dataManager.fetchData()

        userManager = UserLoginFragment()
    }

    // Doesn't work because UserManager was used to log in and stuff
    // In another activity: UserActivity?
    fun likeArticle(article: NewsArticle, pos: Int) {
        userManager.addLikedArticle(pos)
    }
}