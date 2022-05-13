package com.example.mymap.search.presenter
import LocationHelper
import XY_BL_UTM
import android.app.Activity
import android.widget.Toast
import com.example.mymap.Converter.XYVN_2_BL84
import com.example.mymap.R
import com.example.mymap.search.view.CoorSearchFragment
import org.json.JSONArray
import org.json.JSONException

class CoordinateSeachPresenter(coorSearchFragment: CoorSearchFragment) {
    private val callback: Callback = coorSearchFragment
    private fun XLL0_2(pdo: String, pph: String): Double {
        return (pdo.toInt() * 10000 + pph.toInt() * 100).toDouble()
    }

    fun searchCoordinate(activity: Activity, edtsX: List<String>, edtsY: List<String>) {
        var isEmpty = false
        for (i in edtsX.indices) {
            if (edtsX[i] == "" || edtsY[i] == "") {
                isEmpty = true
                break
            }
        }
        if (isEmpty) {
            Toast.makeText(
                activity,
                activity.getString(R.string.coordinate_error),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            searchVN2000Coordinate(activity, edtsX, edtsY)
        }
    }

    fun toJSONArray(edtsX: List<String?>, edtsY: List<String?>): JSONArray? {
        return try {
            val polygon = JSONArray()
            var i = 0
            var j = 0
            while (i < edtsX.size) {
                if (edtsX[i] != "" && edtsY[i] != "") {
                    val coord = JSONArray()
                    coord.put(edtsX[i]!!.toFloat().toDouble())
                    coord.put(edtsY[i]!!.toFloat().toDouble())
                    polygon.put(coord)
                }
                i++
                j++
            }
            if (polygon.getJSONArray(0).getDouble(0) != polygon.getJSONArray(polygon.length() - 1)
                    .getDouble(0)
            ) {
                val coord = JSONArray()
                coord.put(polygon.getJSONArray(polygon.length() - 1).getDouble(0))
                coord.put(polygon.getJSONArray(polygon.length() - 1).getDouble(1))
                polygon.put(coord)
            }
            polygon
        } catch (e: JSONException) {
            null
        }
    }

    private fun searchVN2000Coordinate(
        activity: Activity,
        edtsX: List<String>,
        edtsY: List<String>
    ) {
        val XY = java.lang.reflect.Array.newInstance(
            String::class.java,
            *intArrayOf(edtsX.size, 2)
        ) as Array<Array<String>>
        run {
            var i = 0
            var j = 0
            while (i < edtsX.size) {
                if (edtsX[i] != "" && edtsY[i] != "") {
                    val _XY =
                        arrayOf(edtsX[i], edtsY[i])
                    XY[i] = _XY
                }
                i++
                j++
            }
        }
        val L0 = XLL0_2(105.toString(), 45.toString())
        val BLH84 = java.lang.reflect.Array.newInstance(
            java.lang.Double.TYPE,
            *intArrayOf(XY.size, 2)
        ) as Array<DoubleArray>
        for (i in XY.indices) {
            BLH84[i] = XYVN_2_BL84().xyvn_to_bl84(XY[i][0].toDouble(), XY[i][1].toDouble(), L0, 3.0)!!
        }
        val B = DoubleArray(BLH84.size)
        val L = DoubleArray(BLH84.size)
        for (i in BLH84.indices) {
            B[i] = BLH84[i][0]
            L[i] = BLH84[i][1]
        }
        for (i in B.indices) {
            B[i] = XY_BL_UTM.Degree_2_Rad(B[i]) * 180.0 / 3.141592653589793
            L[i] = XY_BL_UTM.Degree_2_Rad(L[i]) * 180.0 / 3.141592653589793
        }
        if (LocationHelper().isLatArrayValid(B) && LocationHelper().isLngArrayValid(L)) {
            callback.needDrawSketchLayer(B, L)
        } else {
            Toast.makeText(
                activity,
                activity.getString(R.string.coordinate_error),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    interface Callback {
        fun needDrawSketchLayer(B: DoubleArray?, L: DoubleArray?)
    }


}