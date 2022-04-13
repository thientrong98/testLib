import android.app.Activity
import android.widget.Toast
import com.example.mymap.R
import com.example.mymap.utils.Constants
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.RasterLayer
import com.mapbox.mapboxsdk.style.sources.RasterSource
import com.mapbox.mapboxsdk.style.sources.TileSet


class ChangeLayer {
    fun changeMapBackground(name: String, activity: Activity?) {
        if (GlobalVariables.mMap == null) {
            Toast.makeText(activity, R.string.no_connect_error, Toast.LENGTH_LONG).show()
            return
        }

        when (name) {
            "BG_NEN_BAN_DO" -> {
                GlobalVariables.mMap.setStyle(Style.Builder().fromUri("mapbox://styles/tranthientrong/ckr0po0y67exw17rwcwg3ttrv")){
                }
            }
            "BG_NEN_VE_TINH" -> {
                GlobalVariables.mMap.setStyle(Style.Builder().fromUri("mapbox://styles/tranthientrong/cl1x7xmkv001914ppuw0ne6et")){

                }
            }
        }

        changeMapForeground(GlobalVariables.getCurrentForeground.toString(),null)
    }


    fun changeMapForeground(style: String, activity: Activity?) {
        if (GlobalVariables.mMap == null) {
            Toast.makeText(activity, R.string.no_connect_error, Toast.LENGTH_LONG).show()
            return
        }

        when (style) {

            "FG_CDN_SO" -> {
                if (GlobalVariables.getCurrentForeground != Constants.Style.FG_CDN_SO) {
                    GlobalVariables.getCurrentForeground = Constants.Style.FG_CDN_SO
                    addNewStyle(Constants.URL_CDN_DIGITAL, "1", GlobalVariables.mMap, activity)
                }

            }
            "FG_CDN_GIAY" -> {
                if (GlobalVariables.getCurrentForeground != Constants.Style.FG_CDN_GIAY) {
                    GlobalVariables.getCurrentForeground = Constants.Style.FG_CDN_GIAY
                    addNewStyle(
                        if (GlobalVariables.currentBackgroud === Constants.Style.BG_NEN_BAN_DO) Constants.URL_CDN_RASTER else Constants.URL_CDN_RASTER_SATELLITE,
                        "2", GlobalVariables.mMap, activity
                    )
                }
            }
            "FG_TTQH_SO" -> {
//                if (GlobalVariables.getCurrentForeground != Constants.Style.FG_TTQH_SO) {
                    GlobalVariables.getCurrentForeground = Constants.Style.FG_TTQH_SO
                    addNewStyle(
                        Constants.URL_DIGITAL_LAND_NEW_QQQ,
                        "5",
                        GlobalVariables.mMap,
                        activity
                    )
//                }
            }
            "FG_TTQH_GIAY" -> {
//                if (GlobalVariables.getCurrentForeground != Constants.Style.FG_TTQH_GIAY) {
                    GlobalVariables.getCurrentForeground = Constants.Style.FG_TTQH_GIAY
                    addNewStyle(
                        if (GlobalVariables.currentBackgroud === Constants.Style.BG_NEN_BAN_DO) Constants.URL_RASTER_LAND else Constants.URL_RASTER_LAND_SATELLITE,
                        "4", GlobalVariables.mMap, activity
                    )
//                }
            }
        }


    }

    private fun addNewStyle(url: String?, id: String, mMap: MapboxMap, activity: Activity?) {
        if (mMap == null) {
            Toast.makeText(activity, R.string.txt_loi_thu_lai, Toast.LENGTH_LONG).show()
            return
        }
//      removeAllLayersAndVariables()
        val rasterSource = RasterSource(
            id, TileSet(
                "tile",
                url
            ), 256
        )
        val layer =
            RasterLayer(if (id == "2" || id == "4") "base-layer-giay" else "base-layer-so", id)
        mMap.getStyle {
            it.removeLayer("base-layer-giay")
            it.removeLayer("base-layer-so")
            it.removeSource("1")
            it.removeSource("2")
            it.removeSource("4")
            it.removeSource("5")

            it.addSource(rasterSource)
            it.addLayerBelow(layer, "com.mapbox.annotations.points")
        }
    }

    fun removeAllLayersAndVariables(mMap: MapboxMap) {
//        removeBDGQHPK()
//        removeBDSLayers()
//        removeDCCB()
//        removeCDNLayer()
//        mMap.deselectMarkers()
//        resetVariable()
    }


}