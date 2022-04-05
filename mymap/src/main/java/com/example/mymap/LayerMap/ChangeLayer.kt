package com.example.mymap.LayerMap

import androidx.core.content.res.TypedArrayUtils.getText
import com.example.mymap.R
import com.example.mymap.utils.Constants
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.style.layers.RasterLayer
import com.mapbox.mapboxsdk.style.sources.RasterSource
import com.mapbox.mapboxsdk.style.sources.TileSet

class ChangeLayer {
    fun changeMapForeground(style: Constants.Style, mMap: MapboxMap) {
        if (mMap == null) {
//            ToastUtils.showLong(R.string.no_connect_error)
            return
        }

        when (style) {
//            FG_CDN_SO -> addNewStyle(URL_CDN_DIGITAL, "1")
//            FG_CDN_GIAY -> {
//                MapFragment.removeAllLayersAndVariables()
//                addNewStyle(
//                    if (GlobalVariables.currentBackgroud === BG_NEN_BAN_DO) URL_CDN_RASTER else URL_CDN_RASTER_SATELLITE,
//                    "2"
//                )
//            }
            Constants.Style.FG_TTQH_SO -> {
                addNewStyle(Constants.URL_DIGITAL_LAND_NEW_QQQ,"5",mMap)

            }
//            FG_TTQH_GIAY -> {
//                MapFragment.removeAllLayersAndVariables()
//                addNewStyle(
//                    if (GlobalVariables.currentBackgroud === BG_NEN_BAN_DO) URL_RASTER_LAND else URL_RASTER_LAND_SATELLITE,
//                    "4"
//                )
//            }
        }


    }

    fun addNewStyle(url: String?, id: String, mMap: MapboxMap) {
        if (mMap == null) {
//                ToastUtils.showLong(getText(R.string.txt_loi_thu_lai))
            return
        }
//        MapFragment.removeAllLayersAndVariables()
        removeLayer("base-layer",mMap)
        val rasterSource = RasterSource(
            id, TileSet(
                "tile",
                url
            ), 256
        ) //256
       addSource(mMap,rasterSource)
        addLayout(id,mMap )
    }

    fun addSource(mMap: MapboxMap,rasterSource:RasterSource ){
        mMap.getStyle {
            it.addSource(rasterSource)
        }
    }

    fun addLayout(id:String,mMap: MapboxMap){
        val layer = RasterLayer("base-layer", id)
        mMap.getStyle {
            it.addLayerBelow(layer,"com.mapbox.annotations.points")
        }
    }

    fun removeLayer(id: String, mMap: MapboxMap){
        mMap.getStyle {
            it.removeLayer(id)
        }
    }



}