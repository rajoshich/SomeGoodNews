package com.example.somegoodnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "fuck"
    }
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Firebase.database.reference

        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("articles")
        // newsarticle.heading

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            var articles: HashMap<String, NewsArticle> = hashMapOf()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<HashMap<String, NewsArticle>>()
                value?.let{
                    articles = value
                }
                Log.i(TAG, "Value is: $articles")
                val test = articles["headline2"];
                Log.i(TAG, "Whatever $test");
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.i(TAG, "Failed to read value.", error.toException())
            }
        })

    }
}
