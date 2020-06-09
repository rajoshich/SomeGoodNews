package com.example.somegoodnews.Listeners

import com.example.somegoodnews.Managers.NewsArticle

interface OnArticleLongClickListener {
    fun onArticleClicked(newsArticle: NewsArticle, pos:Int)
}