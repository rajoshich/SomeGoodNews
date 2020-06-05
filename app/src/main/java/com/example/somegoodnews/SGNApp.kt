package com.example.somegoodnews

import android.app.Application
import com.example.somegoodnews.Managers.DataManager
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.Managers.UserManager

class SGNApp: Application() {
    lateinit var dataManager: DataManager
    lateinit var userManager: UserManager
    var lastArticle: NewsArticle? = null
    override fun onCreate() {
        super.onCreate()
        dataManager = DataManager()
        dataManager.fetchData()

        userManager = UserManager()
    }

    // Doesn't work because UserManager was used to log in and stuff
    // In another activity: UserActivity?
    fun likeArticle(article: NewsArticle, pos: Int) {
        userManager.addLikedArticle(pos)
    }
}