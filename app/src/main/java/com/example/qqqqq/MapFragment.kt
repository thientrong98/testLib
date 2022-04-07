package com.example.qqqqq

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.fragmentlib.FragmentManager


class MapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("hiih", "onCreate")

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("hiih", "onCreateView")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        Log.d("hiih", "onstart")
        FragmentManager.showFragment(activity as AppCompatActivity, null,null,null,null,null,fgMapFirst ="FG_TTQH_GIAY")
    }

}