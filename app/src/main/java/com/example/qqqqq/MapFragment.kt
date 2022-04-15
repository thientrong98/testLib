package com.example.qqqqq

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentlib.FragmentManager
import com.mapbox.mapboxsdk.geometry.LatLng


class MapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        FragmentManager.showFragment(activity as AppCompatActivity, location = LatLng(),center=null,bb=null, zoom = null, maxZoom = null,
            minZoom = null,fgMapFirst ="FG_TTQH_SO",bgMapFirst= "BG_NEN_BAN_DO", tileBaseMap = "", tileSatellite = "")
    }

}