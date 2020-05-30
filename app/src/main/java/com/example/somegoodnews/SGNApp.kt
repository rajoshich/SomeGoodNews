package com.example.somegoodnews

import android.app.Application
import com.example.somegoodnews.Managers.DataManager
import com.example.somegoodnews.Managers.NewsArticle

class SGNApp: Application() {
    lateinit var dataManager: DataManager
    var lastArticle: NewsArticle? = null
    override fun onCreate() {
        super.onCreate()
        dataManager = DataManager()
        dataManager.fetchData()
    }
}