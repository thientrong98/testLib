package com.example.mymap.Helper

import com.example.mymap.utils.Constants.qhpk_crop_url
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.layers.*
import com.mapbox.mapboxsdk.style.sources.CannotAddSourceException
import com.mapbox.mapboxsdk.style.sources.RasterSource
import com.mapbox.mapboxsdk.style.sources.Source
import com.mapbox.mapboxsdk.style.sources.TileSet
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK

class MapAddLayerHelper {
     val id: String = ""
    private val count_id_layer = 0
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
                qhpk_crop_url.toString() + maQHPKRanh + "/{z}/{x}/{y}"
            ), 256
        )
        val layer: Layer = RasterLayer(
            "$maQHPKRanh-qhpk-layer", "$maQHPKRanh-qhpk-source"
        )
        try {
//            mMap.addSource(source)
            mMap.getStyle {
                it.addSource(source)
                it.addLayerBelow(layer, "com.mapbox.annotations.points")

            }
        } catch (e: CannotAddSourceException) {
            e.printStackTrace()
        }
        try {
            //TODO attention here
//            if (mMap.getLayer("sketch-layer") != null!! and mMap.getLayer("qhpksdd-stroke-" + MapAddLayerHelper.count_id_layer.toString()) != null) {
//                mMap.addLayerBelow(
//                    layer,
//                    "qhpksdd-stroke-" + MapAddLayerHelper.count_id_layer.toString()
//                )
//            } else if (mMap.getLayer("sketch-layer") != null) {
//                mMap.addLayerBelow(layer, "sketch-layer")
//            } else if (mMap.getLayer("mapbox-location-layer") != null) {
//                mMap.addLayerBelow(layer, "mapbox-location-layer")
//            } else if (mMap.getLayer("qhpksdd-stroke-" + MapAddLayerHelper.count_id_layer.toString()) != null) {
//                mMap.addLayerBelow(
//                    layer,
//                    "qhpksdd-stroke-" + MapAddLayerHelper.count_id_layer.toString()
//                )
//            } else {
//                mMap.addLayerBelow(layer, "com.mapbox.annotations.points")
//            }
//            mMap.getLayer("$maQHPKRanh-qhpk-layer").setProperties(
//                PropertyFactory.rasterOpacity(GlobalVariables.ratioProgress as Float / 100)
//            )
        } catch (e: CannotAddLayerException) {
            e.printStackTrace()
        }
//        var i = 0
//        for (cucBo in qhpk.getDCCB()) {
//            try {
//                val coors = JSONArray(cucBo.getRanh())
//                MapAddLayerHelper.addDCCBLayerProgramlly(
//                    mMap,
//                    coors,
//                    i,
//                    cucBo.getMaDCCBRanh(),
//                    qhpk,
//                    cucBo
//                )
//            } catch (e: JSONException) {
//                e.printStackTrace()
//            }
//            i++
//        }
    }


}