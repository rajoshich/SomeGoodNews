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
    private var onUpdateLikes: OnUpdateLikes? = null
    private var onUpdateListListener:OnUpdateListListener? = null
    companion object {
        val TAG: String = "LIKEDFRAG"
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
        val articles = app.dataManager.likedArticles.toList()
        Log.i("saashm","Likes fragment created")
        articlesAdapter = ArticlesAdapter(articles, context, TAG)
        rvLikedNewsList.adapter = articlesAdapter
    }

    fun updateLiked() {
        articlesAdapter.change(app.dataManager.likedArticles, null)
        Log.i("saashm", "Update likes fragment called")
        // update the main list
        onUpdateListListener?.onUpdateList()
    }
}