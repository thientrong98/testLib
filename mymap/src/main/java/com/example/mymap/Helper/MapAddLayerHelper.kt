package com.example.mymap.Helper

import DCCucBo
import LocationHelper
import MapRemoveLayerHelper
import android.app.Activity
import android.graphics.Color
import android.util.Log
import com.example.mymap.utils.Constants
import com.example.mymap.utils.Constants.qhpk_crop_url
import com.example.mymap.utils.GlobalVariables
import com.example.mymap.utils.GlobalVariables.count_id_layer
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.exceptions.InvalidLatLngBoundsException
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.layers.*
import com.mapbox.mapboxsdk.style.sources.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK


class MapAddLayerHelper {
    val id: String = ""
    private val temp = 0
    private val strokee: LineLayer? = null


    fun addRasterQHPKLayer(mMap: MapboxMap, maQHPKRanh: String, qhpk: QHPK) {
//        id = maQHPKRanh
//        MyApplication.getInstance().trackEvent(
//            "VIEW_PROJECT",
//            MyApplication.CLIENT_ID,
//            MyApplication.CURRENT_LOCATION.toString() + "::" + maQHPKRanh
//        )

        val source: Source = RasterSource(
            "$maQHPKRanh-qhpk-source",
            TileSet(
                "tileset",
                "$qhpk_crop_url$maQHPKRanh/{z}/{x}/{y}"
            ), 256
        )

        val layer: Layer = RasterLayer(
            "$maQHPKRanh-qhpk-layer", "$maQHPKRanh-qhpk-source"
        )

        try {
            mMap.getStyle {
                it.addSource(source)
            }
        } catch (e: CannotAddSourceException) {
            e.printStackTrace()
        }

        try {
            mMap.getStyle {
                if (it.getLayer("sketch-layer") != null && it.getLayer("qhpksdd-stroke-$count_id_layer") != null) {
                    it.addLayerBelow(layer, "qhpksdd-stroke-$count_id_layer")

                } else if (it.getLayer("sketch-layer") != null) {
                    it.addLayerBelow(layer, "sketch-layer")
                } else if (it.getLayer("mapbox-location-layer") != null) {
                    it.addLayerBelow(layer, "mapbox-location-layer")

                } else if (it.getLayer("qhpksdd-stroke-$count_id_layer") != null) {
                    it.addLayerBelow(layer, "qhpksdd-stroke-$count_id_layer")

                } else {
                    it.addLayerBelow(layer, "com.mapbox.annotations.points")
                }
            }

//            mMap.getLayer("$maQHPKRanh-qhpk-layer").setProperties(
//                PropertyFactory.rasterOpacity(GlobalVariables.ratioProgress / 100)
//            )
        } catch (e: CannotAddLayerException) {
            e.printStackTrace()
        }
        var i = 0
        for (cucBo in qhpk.dCCB!!) {
            try {
                val coors = JSONArray(cucBo.ranh)
                addDCCBLayerProgramlly(
                    mMap,
                    coors,
                    i,
                    cucBo.maDCCBRanh.toString(),
                    qhpk,
                    cucBo
                )
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            i++
        }
    }

    fun addQHPKLayers1(arrayQHN: JSONArray, jsonArray: JSONArray): String? {
        var lat = 0.0
        var lon = 0.0
        try {
            // Custom markers with numbers
            var index = 1
            var count = 0.0
            for (i in 0 until jsonArray.length()) {
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
                index++
            }
            lon /= count
            lat /= count
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val la = lat.toString()
        val lo = lon.toString()
        return "$la,$lo"
    }


    fun addRasterDCCBLayer(mMap: MapboxMap, maDCCB: String) {
        GlobalVariables.id = maDCCB
//        MyApplication.getInstance().trackEvent(
//            "F8",
//            MyApplication.CLIENT_ID,
//            MyApplication.CURRENT_LOCATION.toString() + "::" + maDCCB
//        )
        val source: Source = RasterSource(
            "$maDCCB-dccb-source",
            TileSet(
                "tileset",
                Constants.dccb_crop_url.toString() + maDCCB + "/{z}/{x}/{y}"
            ),
            256
        )
        val layer: Layer = RasterLayer(
            "$maDCCB-dccb-layer", "$maDCCB-dccb-source"
        )
        try {
            mMap.getStyle {
                it.addSource(source)
                it.addLayerBelow(layer, "mapbox-location-layer")
//                it.getLayer("$maDCCB-dccb-layer").setProperties(
//                    PropertyFactory.rasterOpacity(GlobalVariables.ratioProgress / 100)
//                )
            }
        } catch (e: CannotAddSourceException) {
            e.printStackTrace()
        }
    }

    private fun addDCCBLayerProgramlly(
        map: MapboxMap,
        polygonArray: JSONArray,
        index: Int,
        maDCCB: String,
        qhpk: QHPK,
        dcCucBo: DCCucBo
    ) {
        try {
            val feature = JSONObject()
            feature.put("type", "Feature")
            val geometry = JSONObject()
            val pro = JSONObject()
            geometry.put("type", "MultiPolygon")
            geometry.put("coordinates", polygonArray)
            pro.put("maDCCB", maDCCB)
            pro.put("ranhDCCB", dcCucBo.ranh)
            pro.put("tenQHPK", qhpk.tenDoAn)
            pro.put("tenDCCB", dcCucBo.tenDoAn)
            pro.put("qh", dcCucBo.tenQH)
            pro.put("soqd", dcCucBo.soQD)
            pro.put("cqpd", dcCucBo.coQuanPD)
            pro.put("ngayduyet", dcCucBo.ngayDuyet)
            feature.put("geometry", geometry)
            feature.put("properties", pro)
            val source: Source = GeoJsonSource(
                "$index-sketch-source", feature.toString()
            )
            val layer = FillLayer(
                "$index-sketch-layer", "$index-sketch-source"
            )
            layer.setProperties(
                PropertyFactory.lineColor(Color.rgb(255, 0, 0)),
                PropertyFactory.lineWidth(3f),
                PropertyFactory.fillColor(Color.rgb(255, 0, 0)),
                PropertyFactory.fillOpacity(0.40f),
                PropertyFactory.visibility(Property.VISIBLE)
            )
            try {
                map.getStyle { it.addSource(source) }

            } catch (e: CannotAddSourceException) {
                e.printStackTrace()
            }
            try {
                map.getStyle {
                    if (it.getLayer("sketch-layer") != null) {
                        it.addLayerBelow(layer, "sketch-layer")
                    } else {
                        it.addLayerBelow(layer, "mapbox-location-layer")
                    }
                }

            } catch (e: CannotAddLayerException) {
                e.printStackTrace()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun zoomToRaster(ranh: String?, mMap: MapboxMap) {
        try {
            //height = DeviceSizeHelper.getHeightScreen(this);
                Log.d("ranh","123")
                Log.d("ranh", ranh.toString())
            val coordinates = JSONArray(ranh)
            try {
                if (coordinates.getJSONArray(0).length() > 1) {
                    //this is polygon
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            LocationHelper().getCenterBounds(coordinates.getJSONArray(0))!!,
                            110,
                            110,
                            110,
                            GlobalVariables.bottom_sheet_height
                        )
                    )
                } else {
                    //this is multi polygon
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            LocationHelper().getCenterBounds(
                                coordinates.getJSONArray(0).getJSONArray(0)
                            )!!, 110, 110, 110, GlobalVariables.bottom_sheet_height
                        )
                    )
                }
            } catch (e: InvalidLatLngBoundsException) {
                e.printStackTrace()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun addOChucNangLayer(mMap: MapboxMap, ranh: String?) {
        try {
            val source: Source = GeoJsonSource("ochucnang-source", ranh)
            try {
                mMap.getStyle {
                    it.addSource(source)
                }
            } catch (e: CannotAddSourceException) {
                e.printStackTrace()
            }
            val stroke: Layer = LineLayer("ochucnang-stroke", "ochucnang-source")
            stroke.setProperties(
                PropertyFactory.lineWidth(2f),
                PropertyFactory.lineColor(Color.rgb(255, 0, 0))
            )
            val layer: Layer = FillLayer("ochucnang-layer", "ochucnang-source")
            layer.setProperties(
                PropertyFactory.fillColor(Color.RED),
                PropertyFactory.fillOpacity("0.2".toFloat())
            )
            try {
                mMap.getStyle {
                    it.addLayer(stroke)
                }

            } catch (e: CannotAddLayerException) {
                e.printStackTrace()
            }
            try {
                mMap.getStyle {
                    it.addLayer(layer)
                }
            } catch (e: CannotAddLayerException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun drawSketchLayer( map: MapboxMap, B: DoubleArray?, L: DoubleArray?) {
        MapRemoveLayerHelper().removeSketchLayers(GlobalVariables.mMap)
        if (B!!.isNotEmpty()) {
            if (B.size == 1) {
//                MapAddLayerHelper.addSymbolLayer(activity, map, B, L)
            }
            if (B.size == 2) {
//                MapAddLayerHelper.addLineLayer(map, B, L)
            }
            if (B.size > 2) {
                addFillLayer(map, B, L!!)
            }
//            MapPresenter.collapse()
        }
    }

    private fun addFillLayer(map: MapboxMap, B: DoubleArray, L: DoubleArray) {
        Log.d("huuh","vao ne nha")
        try {
            val polygonArray = JSONArray()
            for (i in B.indices) {
                val coord = JSONArray()
                coord.put(L[i])
                coord.put(B[i])
                polygonArray.put(coord)
            }
            val feature = JSONObject()
            feature.put("type", "Feature")
            val geometry = JSONObject()
            geometry.put("type", "MultiPolygon")
            geometry.put("coordinates", JSONArray().put(JSONArray().put(polygonArray)))
            feature.put("geometry", geometry)
            feature.put("properties", JSONObject())
            val source: Source = GeoJsonSource("sketch-source", feature.toString())
            val layer = FillLayer("sketch-layer", "sketch-source")
            layer.setProperties(
                PropertyFactory.fillColor(Color.parseColor("#1666A8"))
            )
            map.getStyle {
                try {
                    it.addSource(source)
                } catch (e: CannotAddSourceException) {
                    e.printStackTrace()
                }
                if (it.getLayer("base-layer-so") != null) {
                    it.addLayerBelow(layer, "base-layer-so")
                }
            }

            map.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(B[B.size - 1], L[L.size - 1]), 19.0
                )
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}