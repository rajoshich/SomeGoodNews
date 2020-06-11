package com.example.somegoodnews.Fragments

import TabsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.somegoodnews.R
import kotlinx.android.synthetic.main.fragment_category.*


class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category1.setOnClickListener {
            updateFrag("Humans being bros")
        }
        category2.setOnClickListener {
            updateFrag("Aww")
        }
        category3.setOnClickListener {
            updateFrag("Covid-19")
        }
        category4.setOnClickListener {
            updateFrag("Other")
        }
    }

    private fun updateFrag(category: String) {
        val fragment = CategoryListFragment()
        val args = Bundle()
        args.putString("CATEGORY", category)
        fragment.arguments = args
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_category, fragment, CategoryListFragment.TAG)
        fragmentTransaction.addToBackStack(CategoryListFragment.TAG)
        fragmentTransaction.commit()
    }
}
