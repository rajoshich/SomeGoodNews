package com.example.somegoodnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.UserManager
import androidx.fragment.app.FragmentTransaction

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, com.example.somegoodnews.Managers.UserManager.getInstance(), "SIGN_IN_PAGE")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
