
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.example.mymap.R
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.RasterLayer
import com.mapbox.mapboxsdk.style.sources.CannotAddSourceException
import com.mapbox.mapboxsdk.style.sources.RasterSource
import com.mapbox.mapboxsdk.style.sources.Source
import com.mapbox.mapboxsdk.style.sources.TileSet

 class DemoFragment : Fragment(), OnMapReadyCallback, OnMapClickListener {

    private lateinit var btnBack: Button
    var mapView: MapView? = null
    var center = LatLng(10.7994064, 106.7116703)
    private lateinit var mMap: MapboxMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_demo, container, false)

        // enable back button
//        btnBack = view.findViewById(R.id.btn_back)
//        btnBack.setOnClickListener {
//            this.activity?.supportFragmentManager?.popBackStack()
//        }
        mapView = view.findViewById(R.id.mapview)

        mapView?.getMapAsync { mapboxMap ->
            onMapReady(mapboxMap)
        }

        return view
    }

      override fun onMapReady(mapboxMap:MapboxMap) {
         this.mMap = mapboxMap
          mapboxMap.setStyle(Style.DARK)
            mapboxMap.uiSettings.isRotateGesturesEnabled = false
          mapboxMap.cameraPosition = CameraPosition.Builder()
              .target(center)
              .zoom(16.0)
              .build()
          showTitleBanDoSo(mapboxMap)
     }



    private fun showTitleBanDoSo( mapboxMap: MapboxMap) {
        val url1: String = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/bandoso/{z}/{x}/{y}"

        val rasterSource1 = RasterSource("1", TileSet("tile", url1), 256) //256
        val layer = RasterLayer("base-layer", "1")
        try {
            Log.d("huhu", "123")
            mapboxMap.getStyle {
                Log.d("huhu", "123")

                it.removeLayer("base-layer")
                it.addSource(rasterSource1)

                it.addLayerBelow(layer, "com.mapbox.annotations.points")
                Log.d("huhu", it.getLayer("1").toString())

            }

        } catch (e: CannotAddSourceException) {
            Log.d("huhu", e.message.toString())
            e.printStackTrace()
        }

    }

    fun showFullRasterDCCB() {
//        if (GlobalVariables.getCurrentForeground === FG_TTQH_GIAY) {
//            if (!MapFragment.isClickDCCB) {
                addFullRasterQHPKLayer(
                    ""
                )
//            } else if (!MapFragment.dccbCurrent.isEmpty()) {
//                MapAddLayerHelper.addFullRasterDCCBLayer(MapFragment.mMap, MapFragment.dccbCurrent)
//            }
//        } else if (GlobalVariables.getCurrentForeground === FG_CDN_GIAY) {
//            MapAddLayerHelper.addCaoDoNenFullLayer(MapFragment.mMap, MapFragment.currentCdnID)
//        }
    }

    fun addFullRasterQHPKLayer(maQHPKRanh: String) {
        val qhpk_full_url = "https://sqhkt-qlqh.tphcm.gov.vn/api/tiles/qhpk_full/"

        val source: Source = RasterSource(
            "$maQHPKRanh-full-qhpk-source",
            TileSet(
                "tileset",
                "$qhpk_full_url$maQHPKRanh/{z}/{x}/{y}"
            ), 256
        )
        val layer = RasterLayer(
            "$maQHPKRanh-full-qhpk-layer",
            "$maQHPKRanh-full-qhpk-source"
        )

        try {
            mMap?.getStyle {
                it.addSource(source)

            }
        } catch (e: CannotAddSourceException) {
            e.printStackTrace()
        }
    }


    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onMapClick(point: LatLng): Boolean {
        Log.d("huhu", point.toString())

        return false;
    }


}