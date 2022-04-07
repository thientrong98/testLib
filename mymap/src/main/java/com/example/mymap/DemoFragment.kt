import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
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
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.RasterLayer
import com.mapbox.mapboxsdk.style.sources.CannotAddSourceException
import com.mapbox.mapboxsdk.style.sources.RasterSource
import com.mapbox.mapboxsdk.style.sources.Source
import com.mapbox.mapboxsdk.style.sources.TileSet

class DemoFragment(
    center: LatLng?,
    bb: BoundingBox?,
    zoom: Double?,
    minZoom: Double?,
    maxZoom: Double?,
    fgMapFirst: String
) : Fragment(), OnMapReadyCallback, OnMapClickListener {

    private lateinit var btnSo: Button
    private lateinit var btnGiay: Button


    var mapView: MapView? = null
    private var centerPoint = center ?: LatLng(10.7994064, 106.7116703)
    private var zoomMap = zoom ?: 16.0
    private var styleFGMapFirst = fgMapFirst

    private lateinit var mMap: MapboxMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_demo, container, false)

        // enable back button
        btnSo = view.findViewById(R.id.btn_so)
        btnSo.setOnClickListener {
            if (GlobalVariables.getCurrentForeground != Constants.Style.FG_TTQH_SO) {
                ChangeLayer().changeMapForeground("FG_TTQH_SO", null)
            }
        }
        btnGiay = view.findViewById(R.id.btn_giay)
        btnGiay.setOnClickListener {
            if (GlobalVariables.getCurrentForeground != Constants.Style.FG_TTQH_GIAY) {
                ChangeLayer().changeMapForeground("FG_TTQH_GIAY", null)
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
        mapboxMap.setStyle(Style.DARK)
        mapboxMap.uiSettings.isRotateGesturesEnabled = false
        mapboxMap.cameraPosition = CameraPosition.Builder()
            .target(centerPoint)
            .zoom(zoomMap)
            .build()
//        ChangeLayer().changeMapBackground( "BG_NEN_BAN_DO", null)
        ChangeLayer().changeMapForeground( styleFGMapFirst, null)
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