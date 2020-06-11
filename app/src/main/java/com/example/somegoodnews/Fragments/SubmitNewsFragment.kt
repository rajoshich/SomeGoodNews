package com.example.somegoodnews.Fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.somegoodnews.Managers.NewsArticle
import com.example.somegoodnews.R
import com.example.somegoodnews.SGNApp
import kotlinx.android.synthetic.main.fragment_submit_news.*
import java.time.LocalDate

class SubmitNewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_submit_news, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSubmitNews.setOnClickListener {
            submitNews()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun submitNews() {
        if (!cbConfirm.isChecked) {
            Toast.makeText(context, "You must confirm this is wholesome news", Toast.LENGTH_SHORT)
                .show()
        } else {
            var imgLink = etImgLink.text.toString()
            if (imgLink.isEmpty()) {
                imgLink =
                    "https://images.pexels.com/photos/3944463/pexels-photo-3944463.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940"
            }
            val app = (context?.applicationContext as SGNApp)
            val user = app.currentUser?.email.toString()
            val newArticle = NewsArticle(
                category.selectedItem.toString(),
                etHeadline.text.toString(),
                etContent.text.toString(),
                imgLink,
                LocalDate.now().toString(),
                user
            )
            app.dataManager.addArticle(newArticle, user)
            Toast.makeText(context, "Good vibes sent!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        val TAG: String = "SUBMITNEWS"
        fun getInstance(): SubmitNewsFragment {
            return SubmitNewsFragment()
        }
    }
}
