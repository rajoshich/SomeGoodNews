package com.example.somegoodnews.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Listeners.OnUpdateLikes
import com.example.somegoodnews.Listeners.OnUpdateListListener
import com.example.somegoodnews.Managers.ArticlesAdapter
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_liked.*
import kotlinx.android.synthetic.main.item_article.*

class LikedFragment: Fragment() {
    lateinit var app: SGNApp
    private lateinit var articlesAdapter:ArticlesAdapter
    var onUpdateLikes: OnUpdateLikes? = null
    var currentUser: FirebaseUser? = null
    var onUpdateListListener:OnUpdateListListener? = null
    companion object {
        val TAG: String = "LIKEDFRAG"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = (context?.applicationContext as SGNApp).currentUser

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        app = (context.applicationContext as SGNApp)
        if(context is OnUpdateLikes) {
            onUpdateLikes = context
        }
        if(context is OnUpdateListListener) {
            onUpdateListListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_liked, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userEmail = app.currentUser?.email.toString()
        app.dataManager.fetchLikedData(userEmail)
        val articles = app.dataManager.likedArticles?.toList()
        if(articles != null) {
            articlesAdapter = ArticlesAdapter(articles, context, TAG)
            rvLikedNewsList.adapter = articlesAdapter
        } else {
            likedText.text = getString(R.string.no_liked)
        }
        // Testing
        Log.i("fuck", "Liked: $articles")
    }

    fun updateLiked() {
        val likedArticles = app.dataManager.likedArticles.toList()
        articlesAdapter.change(likedArticles)
        articlesAdapter.updateLikes()
        // update the main list
        app.dataManager.fetchData()
        Log.i("poopy", "fragment likes update")
    }
}