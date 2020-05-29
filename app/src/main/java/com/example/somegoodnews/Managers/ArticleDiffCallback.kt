package com.example.somegoodnews.Managers

import androidx.recyclerview.widget.DiffUtil

class ArticleDiffCallback (
    // Change these to hashmap?
    private val old: List<NewsArticle>,
    private val new: List<NewsArticle>
    ): DiffUtil.Callback() {
        override fun getOldListSize() = old.size

        override fun getNewListSize() = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = old[oldItemPosition].headline == new[newItemPosition].headline

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = old[oldItemPosition]
            val newItem = new[newItemPosition]
            return ((oldItem.headline == newItem.headline) && (oldItem.source == newItem.source))
        }
}