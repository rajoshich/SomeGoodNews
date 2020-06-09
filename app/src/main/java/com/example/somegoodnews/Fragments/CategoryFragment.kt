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
    lateinit var pageAdapter: TabsAdapter

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
            val fragment = CategoryListFragment()
            val args = Bundle()
            args.putString("CATEGORY", "Humans being bros")
            fragment.arguments = args

            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()        }

        category2.setOnClickListener {
            val fragment = CategoryListFragment()
            val args = Bundle()
            args.putString("CATEGORY", "Aww")
            fragment.arguments = args

            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()        }

        category3.setOnClickListener {
            val fragment = CategoryListFragment()
            val args = Bundle()
            args.putString("CATEGORY", "COVID-19")
            fragment.arguments = args

            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        category4.setOnClickListener {
            val fragment = CategoryListFragment()
            val args = Bundle()
            args.putString("CATEGORY", "Other")
            fragment.arguments = args

            val fragmentManager: FragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
//            pageAdapter = TabsAdapter(fragmentManager)
//            pageAdapter.addFragments(CategoryListFragment(), "Category list news")
//            viewPager.adapter = pageAdapter
//            tabLayout.setupWithViewPager(viewPager)
//            pageAdapter.instantiateItem(viewPager, viewPager.currentItem) as NewsListFragment
    }
}
