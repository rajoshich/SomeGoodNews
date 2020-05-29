package com.example.somegoodnews.Managers

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DataManager {
    companion object {
        private const val TAG = "fuck"
    }
    private lateinit var database:FirebaseDatabase
    var articles: HashMap<String, NewsArticle> = hashMapOf()

    fun fetchData() {
        database = Firebase.database
        val myRef = database.getReference("articles")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<HashMap<String, NewsArticle>>()
                value?.let{
                    articles = value
                }
                Log.i(TAG, "Value is: $articles")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.i(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}