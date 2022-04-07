package com.example.fragmentlib

import ChangeLayer
import DemoFragment
import androidx.appcompat.app.AppCompatActivity
import com.example.mymap.utils.Constants
import com.example.mymap.utils.GlobalVariables
import com.mapbox.geojson.BoundingBox
import com.mapbox.mapboxsdk.geometry.LatLng

class FragmentManager {
    companion object {
        fun showFragment(activity: AppCompatActivity, center: LatLng?, bb: BoundingBox?, zoom: Double?, maxZoom: Double?, minZoom:Double?, fgMapFirst: String) {
            if (activity.supportFragmentManager.findFragmentById(android.R.id.content) == null) {
                activity.supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, DemoFragment( center, bb,zoom,minZoom,maxZoom,fgMapFirst))
                    .addToBackStack(null)
                    .commit()
            }
        }

        fun changeBGMap(name: String){
          ChangeLayer().changeMapBackground(name,null)
        }

        fun changeFGMap(name: String) {
            ChangeLayer().changeMapForeground(name,null)
            }

        }
}