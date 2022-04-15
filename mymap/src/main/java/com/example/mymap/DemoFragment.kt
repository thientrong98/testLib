import android.graphics.PointF
import android.os.Bundle
import android.util.Log
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
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.MapboxMap.OnMapClickListener
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback

class DemoFragment(
    center: LatLng?,
    bb: BoundingBox?,
    zoom: Double?,
    minZoom: Double?,
    maxZoom: Double?,
    fgMapFirst: String,
    bgMapFirst: String,
    tileBaseMap: String,
    tileSatellite: String
) : Fragment(), OnMapReadyCallback, OnMapClickListener {

    private lateinit var btnSo: Button
    private lateinit var btnGiay: Button
    private lateinit var btnNen: Button
    private lateinit var btnBackgroundMap: ImageButton


    var mapView: MapView? = null
    private var centerPoint = center ?: LatLng(10.7994064, 106.7116703)
    private var zoomMap = zoom ?: 16.0
    private var styleFGMapFirst = fgMapFirst
    private var styleBGMapFirst = bgMapFirst
    private var tileBaseMap = tileBaseMap
    private var tileSatellite = tileSatellite

    private lateinit var mMap: MapboxMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Constants.BG_NEN_BAN_DO =
            if (tileBaseMap.isNullOrEmpty()) "mapbox://styles/tranthientrong/ckr0po0y67exw17rwcwg3ttrv" else tileBaseMap
        Constants.BG_NEN_VE_TINH =
            if (tileSatellite.isNullOrEmpty()) "mapbox://styles/tranthientrong/cl1x7xmkv001914ppuw0ne6et" else tileBaseMap
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

    fun onMapClick(point: LatLng, activity: FragmentActivity?): Boolean {
        Log.d("huhu", point.toString())
//        if (mMap == null) {
//            ToastUtils.showLong(getText(R.string.txt_loi_thu_lai));
//            return;
//        }
//        if (!NetworkUtils.isConnected()) {
//            ToastUtils.showLong(getText(R.string.no_connect_error));
//            return;
//        }
        when (GlobalVariables.getCurrentForeground) {
//            case FG_CDN_SO:
//            mapPresenter.showCaoDoNenHienTrang(point, mapView.getWidth(), mapView.getHeight());
//            break;
//            case FG_CDN_GIAY:
//            PointF pixelCDN = mMap.getProjection().toScreenLocation(point);// get map pixel touched
//            List<Feature> featuresCDN = mMap.queryRenderedFeatures(pixelCDN); // get features from that pixel
//            if (featuresCDN.size() > 0) {
//                try {
//                    for (Feature f : featuresCDN) {
//                        if (f.properties() != null && f.getStringProperty("diem_cao_do") != null) {
//                            if (f.getStringProperty("diem_cao_do").equalsIgnoreCase("1")) {
//                                mapPresenter.addMarkerCaoDo(point, f.getStringProperty("qh"), f.getStringProperty("hh"), getContext());
//                                return;
//                            }
//                        }
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//            }
//            mapPresenter.showBanDoGiayCaDoNen(point);
//            break;
            Constants.Style.FG_TTQH_SO -> {
//                mMap.removeAnnotations();
                AddLayer().removeBDSLayers()
                Thread { MapPresenter().getDigitalLandMapinfo(point, activity) }.start()
            }

            Constants.Style.FG_TTQH_GIAY -> {
                val pixel: PointF =
                    mMap.projection
                        .toScreenLocation(point) // get map pixel touched

                val features: List<Feature> =
                    mMap.queryRenderedFeatures(pixel) // get features from that pixel

                if (features.isNotEmpty()) {
                    MapPresenter().showDCCBCrop(features, point, activity)
                } else {
                    GlobalVariables.isClickDCCB = false
                    MapPresenter().showBanDoGiayQHPK(point, mMap, activity);
                }
            }

        }
        return false;
    }

    override fun onMapClick(point: LatLng): Boolean {
        TODO("Not yet implemented")
    }


}