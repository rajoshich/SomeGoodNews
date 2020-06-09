package com.example.somegoodnews.Listeners

import com.example.somegoodnews.Managers.NewsArticle

interface OnArticleLongClickListener {
    fun likeArticle(newsArticle: NewsArticle, pos:Int)
}