package com.example.mymap.search.view

import AddLayer
import CoordinateItem
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.mymap.Helper.Extension
import com.example.mymap.Helper.MapAddLayerHelper
import com.example.mymap.R
import com.example.mymap.search.adapter.CoordinateAdapter
import com.example.mymap.search.presenter.CoordinateSeachPresenter
import com.example.mymap.search.presenter.DigitalLandSearchPresenter
import com.example.mymap.utils.GlobalVariables
import org.json.JSONArray

class CoorSearchFragment : Fragment(), CoordinateAdapter.AddRowCoodinateListener,
    CoordinateSeachPresenter.Callback {
    private var coordinateItems: ArrayList<CoordinateItem>? = null
    private var adapter: CoordinateAdapter? = null
    private lateinit var recyclerViewCoordinate: RecyclerView
    private lateinit var btnLoadingButtonSearch : CircularProgressButton
    private lateinit var btnLoadingButtonClear: CircularProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_coor_search, container, false)
        recyclerViewCoordinate = view.findViewById(R.id.recycler_view_coordinate)
        btnLoadingButtonSearch = view.findViewById(R.id.btn_loadingButtonSearch)
        btnLoadingButtonClear = view.findViewById(R.id.btn_loadingButtonClear)

        val layoutManager = LinearLayoutManager(context)
        recyclerViewCoordinate.layoutManager = layoutManager
        (recyclerViewCoordinate.layoutManager as LinearLayoutManager).isAutoMeasureEnabled
        recyclerViewCoordinate.setHasFixedSize(false)
        coordinateItems = java.util.ArrayList()
        for (i in 0..3) {
            coordinateItems!!.add(CoordinateItem(i, "", ""))
        }

//        coordinateItems!!.add(CoordinateItem(0, "1192173.70035635", "603274.689512241"))
//        coordinateItems!!.add(CoordinateItem(1, "1192151.96035533", "603254.799512424"))
//        coordinateItems!!.add(CoordinateItem(2, "1192190.70035625", "603256.379511523"))
//        coordinateItems!!.add(CoordinateItem(3, "1192173.70035635", "603274.689512241"))
        adapter = CoordinateAdapter(coordinateItems!!, this)
        recyclerViewCoordinate.adapter = adapter
        recyclerViewCoordinate.isNestedScrollingEnabled = true
        btnLoadingButtonClear.setOnClickListener { onClickRewrite() }
        btnLoadingButtonSearch.setOnClickListener {
            onClickSearch()
        }

//        recyclerViewCoordinate.setOnTouchListener { view, event ->
//            val action = MotionEventCompat.getActionMasked(event)
//            when (action) {
//                MotionEvent.ACTION_DOWN -> false
//                else -> true
//            }
//        }
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
            Extension().showToast(
                R.string.coordinate_error,
                GlobalVariables.activity.applicationContext
            )
            btnLoadingButtonSearch.revertAnimation()

        } else {
            val presenter = CoordinateSeachPresenter(this)
            if (edtXs.size > 3) {
                val coors: JSONArray = presenter.toJSONArray(edtXs, edtYs)!!
                if (coors != null) {
                    Extension().hideKeyboard(view)
                    val digitalLandSearchPresenter = DigitalLandSearchPresenter()
                    digitalLandSearchPresenter.searchPlanningInfoByCoordinate(
                        coors.toString(),
                        activity,btnLoadingButtonSearch
                    )
                }
            }
        }
    }

    private fun onClickSearch() {
        btnLoadingButtonSearch.startAnimation()
        if (!Extension().isNetworkAvailable(GlobalVariables.activity)) {
            Extension().showToast(
                R.string.coordinate_error,
                GlobalVariables.activity.applicationContext
            )
            btnLoadingButtonSearch.revertAnimation()
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
            Extension().showToast(
                R.string.txt_toado,
                GlobalVariables.activity.applicationContext
            )
            btnLoadingButtonSearch.revertAnimation()
            return
        }
        searchLandCoordinate(x, y)
    }

    private fun onClickRewrite() {
        btnLoadingButtonClear.startAnimation {
            coordinateItems?.clear()
            for (i in 0..3) {
                coordinateItems!!.add(CoordinateItem(i, "", ""))
            }
            adapter = CoordinateAdapter(coordinateItems!!, this)
            recyclerViewCoordinate.adapter = adapter

            Extension().hideKeyboard(view)
        }
        btnLoadingButtonClear.revertAnimation()
    }

    override fun onAddedRowCoor(index: Int) {
        recyclerViewCoordinate.smoothScrollToPosition(index)
    }

    override fun onRemoveRowAt(index: Int) {
        recyclerViewCoordinate.smoothScrollToPosition(index)
    }

    override fun needDrawSketchLayer(B: DoubleArray?, L: DoubleArray?) {
        if (GlobalVariables.mMap == null) {
            Extension().showToast(
                R.string.txt_loi_thu_lai,
                GlobalVariables.activity.applicationContext
            )
            return
        }
        AddLayer().removeBDGQHPK(GlobalVariables.mMap)
        MapAddLayerHelper().drawSketchLayer(
            GlobalVariables.mMap, B, L
        )
    }

}