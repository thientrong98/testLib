package com.example.qqqqq

import DemoFragment
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mapbox.mapboxsdk.Mapbox

class MainActivity : AppCompatActivity() {
    lateinit var btnSwitch: Button
    private var currentFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        currentFragment = FragmentManager.newInstance("FG_TTQH_SO", "BG_NEN_BAN_DO", null, null)
//        currentFragment = DemoFragment.newInstance("FG_TTQH_SO", "BG_NEN_BAN_DO", null, null)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment, currentFragment as DemoFragment, "")
            .commit();
        setContentView(R.layout.activity_main)
    }
}