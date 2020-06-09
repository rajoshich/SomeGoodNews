package com.example.somegoodnews.Managers

import android.util.Log
import com.example.somegoodnews.Listeners.OnUpdateListListener
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DataManager {
    companion object {
        private const val TAG = "fuck"
    }
    private lateinit var database:FirebaseDatabase
    var articles: MutableList<NewsArticle> = mutableListOf()
    var likedArticles: MutableList<NewsArticle> = mutableListOf()
    var onUpdateListListener: OnUpdateListListener? = null

    fun fetchData() {
        database = Firebase.database
        val myRef = database.getReference("articles")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<ArrayList<NewsArticle>>()
                value?.let {
                    // Drop 1st cause it's always null for some reason
                    articles = value.drop(1).toMutableList()
                    Log.i("fuck", articles.toString())
                }
                onUpdateListListener?.onUpdateList()
                // Testing
                val numArticles = articles.size
                Log.i(TAG, "Fetched $numArticles articles")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.i(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun fetchLikedData(user: String?) {
        if(user == null) {
            Log.i("fuck", "no user")
        } else {
            database = Firebase.database
            val myRef = database.getReference("users")
                .child(user.replace(".", ""))
                .child("likedArticles")

            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = dataSnapshot.getValue<ArrayList<String>>()
                    Log.i("fuck", "Liked articles index" + value.toString())
                    value?.let { pos ->
                        pos.forEach {
                            likedArticles.add(articles[it.toInt() - 1])
                        }
                    }
                    Log.i("fuck", "Liked articles wohoo" + likedArticles.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                    Log.i(TAG, "Failed to get liked articles.", error.toException())
                }
            })
        }
    }
}