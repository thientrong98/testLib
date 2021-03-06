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
                GlobalVariables.mMap.setStyle(Style.Builder().fromUri(Constants.BG_NEN_BAN_DO)){
                }
            }
            "BG_NEN_VE_TINH" -> {
                GlobalVariables.mMap.setStyle(Style.Builder().fromUri(Constants.BG_NEN_VE_TINH)){

                }
            }
        }

//        changeMapForeground(GlobalVariables.getCurrentForeground.toString(),null)
    }


    fun changeMapForeground(style: String, activity: Activity?) {
        if (GlobalVariables.mMap == null) {
            Toast.makeText(activity, R.string.no_connect_error, Toast.LENGTH_LONG).show()
            return
        }
        GlobalVariables.bottom_sheet_height = 110
//        seek_bar_layer_opacity.setProgress(100)
        when (style) {

            "FG_TTQH_SO" -> {
                AddLayer().removeAllLayersAndVariables()
                    GlobalVariables.getCurrentForeground = Constants.Style.FG_TTQH_SO
                    addNewStyle(
                        Constants.URL_DIGITAL_LAND_NEW_QQQ,
                        "5",
                        GlobalVariables.mMap,
                        activity
                    )
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