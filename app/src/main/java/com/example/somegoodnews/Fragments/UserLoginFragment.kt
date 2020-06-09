package com.example.somegoodnews.Fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
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
        Log.i("fuck", currentUser.toString())
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
        Log.i("fuck", "signIn:$email")
        if (!validateForm()) {
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("fuck", "signInWithEmail:success")
                    updateUser(auth.currentUser)
                    updateUI(currentUser)
                    // redirect to newslist
                    val fragmentManager: FragmentManager = activity!!.supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.main_layout, NewsListFragment(), NewsListFragment.TAG)
                    fragmentTransaction.addToBackStack(NewsListFragment.TAG)
                    fragmentTransaction.commit()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("fuck", "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }

                if (!task.isSuccessful) {
                    // Show some error message?
                }
            }
    }

    private fun createAccount(email: String, password: String) {
        Log.i("fuck", "createAccount:$email")
        if (!validateForm()) {
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.i("fuck", "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                    signIn(email, password)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("fuck", "createUserWithEmail:failure", task.exception)
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
            emailPasswordButtons.visibility = View.GONE
            emailPasswordFields.visibility = View.GONE
            signedInButtons.visibility = View.VISIBLE
        } else {
            status.setText(getString(R.string.signed_out))
            emailPasswordButtons.visibility = View.VISIBLE
            emailPasswordFields.visibility = View.VISIBLE
            signedInButtons.visibility = View.GONE
            view?.visibility = View.VISIBLE
        }
    }

    private fun updateUser(newUser: FirebaseUser?) {
        // updates user in app and here
        currentUser = newUser
        val app = (context?.applicationContext as SGNApp)
        app.currentUser = newUser
        Log.i("fuck", "new user: " + currentUser?.email.toString())
        currentUser?.let {
            (context?.applicationContext as SGNApp).dataManager.fetchLikedData(it.email.toString())
        }
    }
}