import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mymap.Helper.Extension
import com.example.mymap.R
import com.example.mymap.search.view.CoorSearchFragment
import com.example.mymap.search.view.LandIDSearchFragment
import com.google.android.material.tabs.TabLayout


class LandSearchFragment : Fragment() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_land_search, container, false)
        viewPager = view.findViewById(R.id.viewpager)
        tabLayout = view.findViewById(R.id.tabs)


//        viewPager.adapter = PagerAdapter(getSupportFragmentManager())
//        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//        tabLayout.addOnTabSelectedListener(this)
//        tabLayout.setupWithViewPager(viewPager)
//        viewPager.offscreenPageLimit = 3
//        tabLayout.get.setText(getString(R.string.tim_toa_do))
        tabLayout!!.getTabAt(0)!!.text = getString(R.string.tim_toa_do)
        tabLayout!!.getTabAt(1)!!.text = getString(R.string.tim_land_id)

        val adapter = PagerAdapter(requireActivity().supportFragmentManager)
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Extension().hideKeyboard(view)
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        tabLayout!!.setBackgroundResource(R.drawable.new_bg_menu)
        return view
    }


        class PagerAdapter ( fm: FragmentManager) :
        FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 2
        override fun getItem(position: Int): Fragment = when (position) {
            0 -> CoorSearchFragment()
            else -> LandIDSearchFragment()
        }
    }
}