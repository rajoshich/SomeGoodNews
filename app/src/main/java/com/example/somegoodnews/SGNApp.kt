package com.example.somegoodnews

import TabsAdapter
import android.app.Application
import com.example.somegoodnews.Managers.DataManager
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.Fragments.UserLoginFragment
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseUser

class SGNApp: Application() {
    lateinit var dataManager: DataManager
    var lastArticle: NewsArticle? = null
    var currentUser: FirebaseUser? = null
    override fun onCreate() {
        super.onCreate()
        dataManager = DataManager()
        dataManager.fetchData()
        dataManager.fetchLikedData(currentUser?.email.toString())
    }

}