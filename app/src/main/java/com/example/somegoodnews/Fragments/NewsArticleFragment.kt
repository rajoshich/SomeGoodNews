package com.example.somegoodnews.Fragments

import android.content.Intent
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
    lateinit var app: SGNApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = (context?.applicationContext as SGNApp)
    }
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
        btnShare.setOnClickListener {
            clickedArticle = app.lastArticle
            val text = "Checkout this wholesome article on the Some Good News App! \n" +
                        clickedArticle?.headline + "\n" +
                        "Here's the full article: \n" +
                        clickedArticle?.content
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, "Some Good News")
            startActivity(shareIntent)
        }
    }

    fun updateArticle() {
        clickedArticle = app.lastArticle
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