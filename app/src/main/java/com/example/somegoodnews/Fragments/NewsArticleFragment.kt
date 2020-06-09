package com.example.somegoodnews.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.newsarticle_fragment.*

class NewsArticleFragment: Fragment() {
    companion object {
        val TAG:String = "FULLNEWSARTICLE"
    }
    var clickedArticle: NewsArticle? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.newsarticle_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateArticle()

    }

    fun updateArticle() {
        clickedArticle = (context?.applicationContext as SGNApp).lastArticle
        clickedArticle?.let{
            tvFullArticleHeadline.text = it.headline
            tvAuthor.text = it.source
            tvArticleContent.text = it.content
            if(!it.img.isNullOrEmpty()) {
                Picasso.get().load(it.img).into(imgArticlePicture)
            }
        }

    }
}