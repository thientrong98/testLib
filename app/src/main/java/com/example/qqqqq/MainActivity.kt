package com.example.qqqqq

import DemoFragment
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng

class MainActivity : AppCompatActivity(), DemoFragment.CreatePostListener {
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(applicationContext, getString(R.string.access_token))
        currentFragment = DemoFragment.newInstance(
            LatLng(10.7994064, 106.7116703),
            "FG_TTQH_SO",
            "BG_NEN_BAN_DO",
            "mapbox://styles/tranthientrong/ckr0po0y67exw17rwcwg3ttrv",
            "",
            "vi",this,
        )
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment, currentFragment as DemoFragment, "")
            .commit();

        setContentView(R.layout.activity_main)
    }

    override fun sendDataSuccess(info: String?) {
       Log.d("info",info.toString())
    }
}