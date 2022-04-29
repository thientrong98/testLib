package com.example.mymap.search.view

import CoordinateAdapter
import CoordinateItem
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.mymap.R

class CoorSearchFragment : Fragment(), CoordinateAdapter.AddRowCoodinateListener {
    private var coordinateItems: ArrayList<CoordinateItem>? = null
    private var adapter: CoordinateAdapter? = null
    var unbinder: Unbinder? = null
    private lateinit var recyclerViewCoordinate: RecyclerView
    private lateinit var txtRewrite:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_coor_search, container, false)
        recyclerViewCoordinate = view.findViewById(R.id.recycler_view_coordinate)
        txtRewrite= view.findViewById(R.id.txtRewrite)

        unbinder = ButterKnife.bind(this, view)
        val layoutManager = LinearLayoutManager(context)
        recyclerViewCoordinate.layoutManager = layoutManager
        recyclerViewCoordinate.setHasFixedSize(true)
        coordinateItems = java.util.ArrayList()
        coordinateItems!!.add(CoordinateItem(0, "", ""))
        coordinateItems!!.add(CoordinateItem(1, "", ""))
        coordinateItems!!.add(CoordinateItem(2, "", ""))
        coordinateItems!!.add(CoordinateItem(3, "", ""))
        adapter = CoordinateAdapter(coordinateItems!!, this)
        recyclerViewCoordinate.adapter = adapter
        txtRewrite.setOnClickListener { onClickRewrite() }

        return view
    }

    private fun onClickRewrite() {
        coordinateItems?.clear()
        coordinateItems!!.add(CoordinateItem(0, "", ""))
        adapter = CoordinateAdapter(coordinateItems!!, this)
        recyclerViewCoordinate.adapter = adapter
    }

    override fun onAddedRowCoor() {
        Log.d("huhuhu","dang vao")
        Log.d("huhuhu",adapter!!.itemCount.toString())
        recyclerViewCoordinate.smoothScrollToPosition(adapter!!.getCoordinateItems().size)
    }

    @SuppressLint("LogNotTimber")
    override fun onRemoveRowAt(index: Int) {
        adapter!!.notifyItemRemoved(index)
        adapter!!.notifyItemRangeChanged(index, adapter!!.getCoordinateItems().size)
    }

}