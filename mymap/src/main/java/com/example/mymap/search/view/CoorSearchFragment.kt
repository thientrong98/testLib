package com.example.mymap.search.view

import AddLayer
import CoordinateAdapter
import CoordinateItem
import CoordinateSeachPresenter
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
import butterknife.Unbinder
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.mymap.Helper.Extension
import com.example.mymap.Helper.MapAddLayerHelper
import com.example.mymap.R
import com.example.mymap.utils.GlobalVariables
import org.json.JSONArray


class CoorSearchFragment : Fragment(), CoordinateAdapter.AddRowCoodinateListener,
    CoordinateSeachPresenter.Callback {
    private var coordinateItems: ArrayList<CoordinateItem>? = null
    private var adapter: CoordinateAdapter? = null
    var unbinder: Unbinder? = null
    private lateinit var recyclerViewCoordinate: RecyclerView
    private lateinit var txtRewrite: TextView
    private lateinit var txtSearch: TextView
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
        var view = inflater.inflate(R.layout.fragment_coor_search, container, false)
        recyclerViewCoordinate = view.findViewById(R.id.recycler_view_coordinate)
        txtRewrite = view.findViewById(R.id.txtRewrite)
        txtSearch = view.findViewById(R.id.txtSearch)

        val layoutManager = LinearLayoutManager(context)
        recyclerViewCoordinate.layoutManager = layoutManager
        recyclerViewCoordinate.setHasFixedSize(true)
        coordinateItems = java.util.ArrayList()
//        for (i in 0..30) {
//            coordinateItems!!.add(CoordinateItem(i, "", ""))
//        }

        coordinateItems!!.add(CoordinateItem(0, "1192173.70035635", "603274.689512241"))
        coordinateItems!!.add(CoordinateItem(1, "1192151.96035533", "603254.799512424"))
//        coordinateItems!!.add(CoordinateItem(2, "1192190.70035625", "603256.379511523"))
//        coordinateItems!!.add(CoordinateItem(3, "1192173.70035635", "603274.689512241"))
//        coordinateItems!!.add(CoordinateItem(4, "", ""))
//        coordinateItems!!.add(CoordinateItem(5, "", ""))
//        coordinateItems!!.add(CoordinateItem(6, "", ""))
//        coordinateItems!!.add(CoordinateItem(7, "", ""))
//        coordinateItems!!.add(CoordinateItem(8, "", ""))
        adapter = CoordinateAdapter(coordinateItems!!, this)
        recyclerViewCoordinate.adapter = adapter
//        recyclerViewCoordinate.isNestedScrollingEnabled = true
        txtRewrite.setOnClickListener { onClickRewrite() }
        txtSearch.setOnClickListener {
            onClickSearch()
        }

        return view
    }

    private fun searchLandCoordinate(edtXs: List<String?>, edtYs: List<String?>) {
        var isEmpty = false
        for (i in edtXs.indices) {
            if (edtXs[i] == "" || edtYs[i] == "") {
                isEmpty = true
                break
            }
        }
        if (isEmpty) {
            ToastUtils.showShort(getString(R.string.coordinate_error))
        } else {
            val presenter = CoordinateSeachPresenter(this)
            if (edtXs.size > 3) {
                val coors: JSONArray = presenter.toJSONArray(edtXs, edtYs)!!
                if (coors != null) {
                    Extension().hideKeyboard(view)

//                    MyApplication.getInstance().trackEvent(
//                        "FIND_COORDS", MyApplication.CLIENT_ID,
//                        MyApplication.CURRENT_LOCATION.toString() + "::" + coors.toString()
//                    )
                    val digitalLandSearchPresenter = DigitalLandSearchPresenter()
                    digitalLandSearchPresenter.searchPlanningInfoByCoordinate(
                        coors.toString(),
                        activity
                    )
                }
            }
            //            else {
//                presenter.searchCoordinate(activity, edtXs, edtYs)
//            }
        }
    }

    private fun onClickSearch() {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showLong(getString(R.string.no_connect_error))
            return
        }
        val x: ArrayList<String?> = java.util.ArrayList()
        val y: ArrayList<String?> = java.util.ArrayList()
        for (a in adapter!!.getCoordinateItems()) {
            if (a.getCoordinateX()!!.isNotEmpty() && a.getCoordinateY()!!.isNotEmpty()) {
                x.add(a.getCoordinateX())
                y.add(a.getCoordinateY())
            }
        }
        if (x.size < 4 || y.size < 4) {
            ToastUtils.showLong(R.string.txt_toado)
            return
        }
        searchLandCoordinate(x, y)
    }

    private fun onClickRewrite() {
        coordinateItems?.clear()
        for (i in 0..3) {
            coordinateItems!!.add(CoordinateItem(i, "", ""))
        }
        adapter = CoordinateAdapter(coordinateItems!!, this)
        recyclerViewCoordinate.adapter = adapter

        Extension().hideKeyboard(view)
    }

    override fun onAddedRowCoor(index: Int) {
//        recyclerViewCoordinate.smoothScrollToPosition(adapter!!.getCoordinateItems().size)
//        adapter!!.notifyDataSetChanged()
    }

    override fun onAddedRowCoorUnder(index: Int) {
        val oldListItemscount: Int = coordinateItems!!.size
        val insertIndex = index + 1
        coordinateItems!!.add(insertIndex, CoordinateItem(insertIndex, "", ""))
        adapter!!.notifyItemInserted(insertIndex);

//        for (i in (insertIndex+1 ) until coordinateItems!!.size) {
//            coordinateItems!![i].setIndex(i + 1)
//            coordinateItems!![i].setCoordinateX(coordinateItems!![i-1].getCoordinateX())
//            coordinateItems!![i].setCoordinateY(coordinateItems!![i-1].getCoordinateX())
////            adapter!!.notifyItemChanged(i)
//        }
        adapter!!.notifyItemChanged(oldListItemscount + 1, coordinateItems)

//        for (i in coordinateItems!!.size - 1 downTo insertIndex + 1) {
//            coordinateItems!![i].setCoordinateX(coordinateItems!![i - 1].getCoordinateX())
//            coordinateItems!![i].setCoordinateY(coordinateItems!![i - 1].getCoordinateY())
//        }
//        adapter!!.notifyItemRangeChanged(index, coordinateItems!!.size);

//        adapter!!.notifyItemRangeChanged(index,  coordinateItems!!.size)
//        recyclerViewCoordinate.smoothScrollToPosition(adapter!!.getCoordinateItems().size)
    }

    @SuppressLint("LogNotTimber")
    override fun onRemoveRowAt(index: Int) {
        adapter!!.notifyItemRemoved(index)
        adapter!!.notifyItemRangeChanged(index, adapter!!.getCoordinateItems().size)
    }

    override fun needDrawSketchLayer(B: DoubleArray?, L: DoubleArray?) {
        Log.d("huhu", "vao ne")
        if (GlobalVariables.mMap == null) {
            ToastUtils.showLong(R.string.txt_loi_thu_lai)
            return
        }
        AddLayer().removeBDGQHPK(GlobalVariables.mMap)
        MapAddLayerHelper().drawSketchLayer(
            GlobalVariables.mMap, B, L
        )
    }

}