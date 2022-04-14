package com.example.mymap.Helper

import BitmapHelper
import LocationHelper
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.mymap.R
import com.example.mymap.utils.Constants
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.layers.*
import com.mapbox.mapboxsdk.style.sources.CannotAddSourceException
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.style.sources.Source
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MapAddLayerBDSHelper {
    //ve len map co 2 loai:
    //QHPK -->
    //QHN -->
    fun addQHPKLayers(
        activity: FragmentActivity?,
        mMap: MapboxMap,
        arrayQHN: JSONArray,
        jsonArray: JSONArray
    ) {
        try {
            // Custom markers with numbers
            var index = 1
            var count = 0.0
            var lat = 0.0
            var lon = 0.0
            GlobalVariables.count_id_layer = jsonArray.length() + arrayQHN.length()
            for (i in 0 until jsonArray.length() + arrayQHN.length()) {
                var jsonObj: JSONObject = if (i < jsonArray.length()) {
                    jsonArray.getJSONObject(i)
                } else {
                    arrayQHN.getJSONObject(i - jsonArray.length())
                }
                if (jsonObj.isNull("geometry")) {
                    continue
                }
                val feature = JSONObject()
                feature.put("type", "Feature")
                feature.put("geometry", jsonObj.getJSONObject("geometry"))
                feature.put("properties", JSONObject())
                val source: Source =
                    GeoJsonSource("qhpksdd-source-" + (i + 1).toString(), feature.toString())
                val stroke = LineLayer(
                    "qhpksdd-stroke-" + (i + 1).toString(),
                    "qhpksdd-source-" + (i + 1).toString()
                ) // Draw stroke around polygon
                if (GlobalVariables.getCurrentForeground == Constants.Style.FG_TTQH_GIAY) {
                    stroke.setProperties(
                        PropertyFactory.lineWidth(2f),
                        PropertyFactory.lineColor(Color.RED)
                    )
                } else {
                    stroke.setProperties(
                        PropertyFactory.lineWidth(2f),
                        PropertyFactory.lineColor(Color.BLACK)
                    )
                }
                var RGBColor: String
                if (i < jsonArray.length()) {
                    //quy hoach phan khu
                    RGBColor = if (!jsonObj.getJSONObject("properties").isNull("rgbcolor")) {
                        jsonObj.getJSONObject("properties").getString("rgbcolor")
                    } else {
                        "130,130,130"
                    }
                } else {
                    //Quy hoach nganh
                    RGBColor = when (jsonObj.getJSONObject("properties").getString("phannhom")) {
                        "1" -> "130,130,130"
                        "2" -> "169,0,230"
                        else -> "78,78,78"
                    }
                    addQHNRanhLayer(
                        mMap,
                        jsonObj.getJSONObject("properties").getString("ranhlayer"),
                        RGBColor,
                        index
                    )
                }
                val colors = RGBColor.split(",").toTypedArray()
                val layer = FillLayer(
                    "qhpksdd-layer-" + (i + 1).toString(),
                    "qhpksdd-source-" + (i + 1).toString()
                )
                layer.setProperties(
                    PropertyFactory.fillColor(
                        Color.argb(
                            255, Integer.valueOf(colors[0]),
                            Integer.valueOf(colors[1]),
                            Integer.valueOf(colors[2])
                        )
                    )
                )
                try {
                    mMap.getStyle {
                        it.addSource(source)
                    }
                } catch (e: CannotAddSourceException) {
                    e.printStackTrace()
                }
                if (GlobalVariables.getCurrentForeground != Constants.Style.FG_TTQH_GIAY) {
                    try {
                        mMap.getStyle {
                            if (it.getLayer("base-layer-so") != null) {
                                it.addLayerBelow(layer, "base-layer-so")
                            } else {
                                it.addLayerBelow(layer, "base-layer-giay")
                            }
                            it.getLayer("qhpksdd-layer-" + (i + 1).toString())?.setProperties(
                                PropertyFactory.fillOpacity(GlobalVariables.ratioProgress as Float / 100)
                            )
                        }


                    } catch (e: CannotAddLayerException) {
                        e.printStackTrace()
                    }
                }
                try {
                    mMap.getStyle {
                        if (GlobalVariables.getCurrentForeground == Constants.Style.FG_TTQH_GIAY)

                                it.addLayerAbove(
                                    stroke,
                                    GlobalVariables.id + "-qhpk-layer")

                        else it.addLayerBelow(stroke, GlobalVariables.id + "-qhpk-layer")
                    }

                } catch (e: CannotAddLayerException) {
                    e.printStackTrace()
                }
                val geometry = jsonObj.getJSONObject("geometry")
                var coordinates = geometry.getJSONArray("coordinates")
                coordinates = coordinates.getJSONArray(0).getJSONArray(0)
                for (j in 0 until coordinates.length()) {
                    lon += coordinates[j].toString().split(",").toTypedArray()[0].replace("[", "")
                        .toDouble()
                    lat += coordinates[j].toString().split(",").toTypedArray()[1].replace("]", "")
                        .toDouble()
                }
                count += coordinates.length().toDouble()
                val marker: View =
                    (activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                        R.layout.custom_marker,
                        null
                    )
                val edtNumber = marker.findViewById<TextView>(R.id.edtNumber)
                edtNumber.text = index.toString()
                val iconFactory = IconFactory.getInstance(activity)
                val icon =
                    iconFactory.fromBitmap(BitmapHelper().createDrawableFromView(activity, marker)!!)
                if (index >= 10) { // Move number in marker to left when that number > 9
                    val paddingLeft = 10
                    val paddingTop = 2
                    val density = activity.resources.displayMetrics.density
                    val paddingLeftDp = (paddingLeft * density).toInt()
                    val paddingTopDp = (paddingTop * density).toInt()
                    edtNumber.setPadding(paddingLeftDp, paddingTopDp, 0, 0)
                }
                // Add marker to Polygon
                mMap.addMarker(
                    MarkerOptions()
                        .icon(icon)
                        .position(LocationHelper().getMarkerPosition(coordinates))
                )
                index++
            }
            lon = lon / count
            lat = lat / count
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }


    private fun addQHNRanhLayer(mMap: MapboxMap, ranh: String, color: String, index: Int) {
       GlobalVariables.temp = index
        try {
            val source: Source = GeoJsonSource(
                "QHN-source-$index", ranh
            )
            val layer: Layer = FillLayer(
                "QHN-layer-$index", "QHN-source-$index"
            )
            val colors = color.split(",").toTypedArray()
            layer.setProperties(
                PropertyFactory.fillColor(
                    Color.rgb(
                        Integer.valueOf(
                            colors[0]
                        ),
                        Integer.valueOf(colors[1]),
                        Integer.valueOf(colors[2])
                    )
                ),
                PropertyFactory.fillOpacity("0.3".toFloat())
            )
            try {
                mMap.getStyle {
                    it.addSource(source)
                    it.addLayer(layer)

                }
            } catch (e: CannotAddSourceException) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}