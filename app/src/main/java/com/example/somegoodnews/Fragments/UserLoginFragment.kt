package com.example.somegoodnews.Fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.sign_in_fragment.*

class UserLoginFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    var currentUser: FirebaseUser? = null
    companion object {
        var TAG: String = "USERLOGINFRAG"
        fun getInstance(): UserLoginFragment {
            return UserLoginFragment()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth(FirebaseApp.getInstance())
        updateUser(auth.currentUser)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.sign_in_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(currentUser)

        emailSignInButton.setOnClickListener {
            signIn(fieldEmail.text.toString(), fieldPassword.text.toString())
        }

        emailCreateAccountButton.setOnClickListener {
            createAccount(fieldEmail.text.toString(), fieldPassword.text.toString())
        }

        signOutButton.setOnClickListener {
            signOut()
        }
    }

    private fun signIn(email: String, password: String) {
        if (!validateForm()) {
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    updateUser(auth.currentUser)
                    updateUI(currentUser)
                   view?.visibility = GONE
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun createAccount(email: String, password: String) {
        if (!validateForm()) {
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                    signIn(email, password)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun signOut() {
        auth.signOut()
        updateUser(null)
        updateUI(currentUser)
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            fieldEmail.error = "Required."
            valid = false
        } else {
            fieldEmail.error = null
        }

        val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            fieldPassword.error = "Required."
            valid = false
        } else {
            fieldPassword.error = null
        }
        return valid
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            status.text = getString(R.string.emailpassword_status_fmt,
                user.email)
            emailPasswordButtons.visibility = GONE
            emailPasswordFields.visibility = GONE
            signedInButtons.visibility = VISIBLE
        } else {
            status.text = getString(R.string.signed_out)
            emailPasswordButtons.visibility = VISIBLE
            emailPasswordFields.visibility = VISIBLE
            signedInButtons.visibility = GONE
            view?.visibility = VISIBLE
        }
    }

    private fun updateUser(newUser: FirebaseUser?) {
        // updates user in app and here
        currentUser = newUser
        val app = (context?.applicationContext as SGNApp)
        app.refreshData()
        app.currentUser = newUser
        app.dataManager.fetchLikedData(newUser?.email.toString())
    }
}