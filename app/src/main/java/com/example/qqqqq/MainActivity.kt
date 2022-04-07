package com.example.qqqqq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.fragmentlib.FragmentManager
import com.mapbox.mapboxsdk.Mapbox

class MainActivity : AppCompatActivity() {
    lateinit var btnSwitch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Mapbox.getInstance(applicationContext, getString(R.string.access_token))

//                btnSwitch = findViewById(R.id.btn_switch)
//        btnSwitch.setOnClickListener {
//            FragmentManager.changeMap("TTQH_GIAY")
//        }
//
//        FragmentManager.showFragment(this)
//
    }
}