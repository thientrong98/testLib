package com.example.fragmentlib

import DemoFragment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class FragmentManager {
    companion object {
        /**
         * displays the fragment
         */
        fun showFragment(activity: AppCompatActivity) {
            Log.d("FragmentManager", "showAd")
            if (activity.supportFragmentManager.findFragmentById(android.R.id.content) == null) {
                activity.supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, DemoFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        fun changeMap(function: String) {

        }
    }
}