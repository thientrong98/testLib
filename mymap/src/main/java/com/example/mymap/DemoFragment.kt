import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.mymap.R
import com.example.mymap.utils.Constants
import com.example.mymap.utils.GlobalVariables
import com.mapbox.geojson.BoundingBox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback

class DemoFragment() : Fragment(), OnMapReadyCallback, OnMapClickListener {

    private lateinit var btnSo: Button
    private lateinit var btnGiay: Button
    private lateinit var btnNen: Button
    private lateinit var btnBackgroundMap: ImageButton


    var mapView: MapView? = null
    private var centerPoint: LatLng = LatLng(10.7994064, 106.7116703)
    private var zoomMap: Double = 16.0

    private  var styleFGMapFirst: String = "FG_TTQH_SO"
    private  var styleBGMapFirst: String = "BG_NEN_BAN_DO"
    private lateinit var mMap: MapboxMap


    companion object {
        fun newInstance(
//            center: LatLng?,
//            bb: BoundingBox?,
//            zoom: Double?,
//            minZoom: Double?,
//            maxZoom: Double?,
            fgMapFirst: String,
            bgMapFirst: String,
            tileBaseMap: String?,
            tileSatellite: String?
        ): DemoFragment {
            val data = Bundle()
            data.putString("fgMapFirst", fgMapFirst)
            data.putString("bgMapFirst", bgMapFirst)
            data.putString("tileBaseMap", tileBaseMap)
            data.putString("tileSatellite", tileSatellite)

            return DemoFragment().apply {
                arguments = data
            }
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            styleFGMapFirst = it.getString("fgMapFirst").toString()
            styleBGMapFirst = it.getString("bgMapFirst").toString()
            Constants.BG_NEN_BAN_DO =
                if (it.getString("tileBaseMap")
                        .isNullOrEmpty()
                ) "mapbox://styles/tranthientrong/ckr0po0y67exw17rwcwg3ttrv" else it.getString(
                    "tileBaseMap"
                )
            Constants.BG_NEN_VE_TINH =
                if (it.getString("tileSatellite")
                        .isNullOrEmpty()
                ) "mapbox://styles/tranthientrong/cl1x7xmkv001914ppuw0ne6et" else it.getString(
                    "tileSatellite"
                )

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var view = inflater.inflate(R.layout.fragment_demo, container, false)


        // enable back button
        btnSo = view.findViewById(R.id.btn_so)
        btnSo.setOnClickListener {
            ChangeLayer().changeMapForeground("FG_TTQH_SO", null)
        }
        btnGiay = view.findViewById(R.id.btn_giay)
        btnGiay.setOnClickListener {

            ChangeLayer().changeMapForeground("FG_TTQH_GIAY", null)
        }

        btnBackgroundMap = view.findViewById(R.id.btn_background_map)
        btnBackgroundMap.setOnClickListener {
            if (GlobalVariables.currentBackgroud == Constants.Style.BG_NEN_VE_TINH) {
                GlobalVariables.currentBackgroud = Constants.Style.BG_NEN_BAN_DO
                ChangeLayer().changeMapBackground(
                    "BG_NEN_BAN_DO",
                    null
                )
            } else {
                GlobalVariables.currentBackgroud = Constants.Style.BG_NEN_VE_TINH
                ChangeLayer().changeMapBackground("BG_NEN_VE_TINH", null)
            }
        }

        mapView = view.findViewById(R.id.mapview)
        mapView?.getMapAsync { mapboxMap ->
            onMapReady(mapboxMap)
        }

        return view
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mMap = mapboxMap
        GlobalVariables.mMap = mapboxMap

        mapboxMap.uiSettings.isRotateGesturesEnabled = false
        mapboxMap.uiSettings.isAttributionEnabled = false;
        mapboxMap.uiSettings.isLogoEnabled = false;
        mapboxMap.cameraPosition = CameraPosition.Builder()
            .target(centerPoint)
            .zoom(zoomMap)
            .build()
        ChangeLayer().changeMapBackground(styleBGMapFirst, null)
        ChangeLayer().changeMapForeground(styleFGMapFirst, null)

        mapboxMap.addOnMapClickListener { point ->
            onMapClick(point, activity)
            true
        }


    }

    private fun onMapClick(point: LatLng, activity: FragmentActivity?): Boolean {
        AddLayer().removeBDSLayers()
        Thread { MapPresenter().getDigitalLandMapinfo(point, activity) }.start()
        return false;
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
        TODO("Not yet implemented")
    }


}