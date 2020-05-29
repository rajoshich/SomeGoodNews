package com.example.somegoodnews

data class NewsArticle (
    val category: String = "",
    val headline: String = "",
    val content : String = "",
    val img: String? = "",
    val date: String = "",
    val source: String = ""
)