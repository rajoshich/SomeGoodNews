package com.example.somegoodnews

import android.app.Application
import com.example.somegoodnews.Managers.DataManager

class SGNApp: Application() {
    lateinit var dataManager: DataManager

    override fun onCreate() {
        super.onCreate()
        dataManager = DataManager()
        dataManager.fetchData()
    }
}