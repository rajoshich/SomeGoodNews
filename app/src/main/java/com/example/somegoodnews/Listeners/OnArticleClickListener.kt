package com.example.somegoodnews.Listeners

import com.example.somegoodnews.Managers.NewsArticle

interface OnArticleClickListener {
    fun onArticleClicked(newsArticle: NewsArticle)
}