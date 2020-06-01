import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CustomPageAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    var fragmentItems:ArrayList<Fragment> = ArrayList()
    var fragmentTitles:ArrayList<String> = ArrayList()

    fun addFragments(fragmentItem:Fragment, fragmentTitle: String) {
        fragmentItems.add(fragmentItem)
        fragmentTitles.add(fragmentTitle)
    }
    override fun getItem(position: Int): Fragment {
        return fragmentItems[position]
    }

    override fun getCount(): Int {
        return fragmentItems.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitles[position]
    }
}