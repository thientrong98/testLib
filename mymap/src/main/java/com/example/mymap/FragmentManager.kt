package com.example.fragmentlib

import ChangeLayer
import DemoFragment
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.mymap.R
import com.example.mymap.utils.Constants
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.Mapbox.getApplicationContext
import com.mapbox.mapboxsdk.maps.MapboxMap

class FragmentManager {
    companion object {
        /**
         * displays the fragment
         */
        fun showFragment(activity: AppCompatActivity) {
            if (activity.supportFragmentManager.findFragmentById(android.R.id.content) == null) {
                activity.supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, DemoFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        fun changeMap(name: String) {
            when (name) {
                "FG-TTQH_SO" ->       {
                    if (GlobalVariables.getCurrentForeground != Constants.Style.FG_TTQH_SO)
                    ChangeLayer().changeMapForeground(Constants.Style.FG_TTQH_SO,null)
                }

                "FG-TTQH_GIAY"->  {
                    if (GlobalVariables.getCurrentForeground != Constants.Style.FG_TTQH_GIAY){
                        ChangeLayer().changeMapForeground(Constants.Style.FG_TTQH_GIAY ,null)

                    }
                }

            }

        }
    }
}