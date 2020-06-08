package com.example.somegoodnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.somegoodnews.Fragments.UserLoginFragment

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, UserLoginFragment.getInstance(), "SIGN_IN_PAGE")
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
