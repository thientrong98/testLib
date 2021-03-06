import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.example.mymap.Helper.Extension
import com.example.mymap.R
import com.example.mymap.listener.SearchListener
import com.example.mymap.utils.Constants
import com.example.mymap.utils.GlobalVariables
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.rasterOpacity

class DemoFragment : Fragment(), OnMapReadyCallback, SearchListener {

    private lateinit var btnSearch: ImageButton
    private lateinit var btnTransparent: ImageButton
    private lateinit var btnLocation: ImageButton
    private lateinit var llFrameSearch: FragmentContainerView
    private lateinit var llFrameInfo: FragmentContainerView
    private lateinit var containerBottomSheet: FrameLayout
    private lateinit var llSeekbar: LinearLayout
    private lateinit var seekBarLayerOpacity: SeekBar

    private lateinit var location: LatLng
    private var zoomMap: Double = 16.0
    private var styleFGMapFirst: String = "FG_TTQH_SO"
    private var styleBGMapFirst: String = "BG_NEN_BAN_DO"
    private lateinit var mMap: MapboxMap
    private var isTransparent: Boolean = false
    private var isClickLocation: Boolean = false
    private var mBottomSheetBehavior: BottomSheetBehavior<FrameLayout>? = null
    private var mapPresenter: MapPresenter? = null
    var mapView: MapView? = null

    companion object {
        fun newInstance(
//            center: LatLng?,
//            bb: BoundingBox?,
//            zoom: Double?,
//            minZoom: Double?,
//            maxZoom: Double?,
            location: LatLng,
            fgMapFirst: String,
            bgMapFirst: String,
            tileBaseMap: String,
            tileSatellite: String,
            language: String?,
            activity: Activity
        ): DemoFragment {
            val data = Bundle()
            data.putString("fgMapFirst", fgMapFirst)
            data.putString("bgMapFirst", bgMapFirst)
            data.putString("tileBaseMap", tileBaseMap)
            data.putString("tileSatellite", tileSatellite)
            data.putParcelable("location", location)
            data.putString("language", language)

            return DemoFragment().apply {
                GlobalVariables.activity = activity
                arguments = data
            }
        }
    }

    fun setActivityListener(activityListener: LandInfoFragment?) {
        GlobalVariables.landInfoBDSListener = activityListener
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            styleFGMapFirst = it.getString("fgMapFirst").toString()
            styleBGMapFirst = it.getString("bgMapFirst").toString()
            Constants.BG_NEN_BAN_DO =
                it.getString("tileBaseMap")

            Constants.BG_NEN_VE_TINH =
                it.getString(
                    "tileSatellite"
                )

            location = it.getParcelable("" + "location")!!
            GlobalVariables.getCurrentLanguage =
                if (it.getString("language").isNullOrEmpty()) it.getString("language")
                else "vi"
        }

//        DistrictWard().getProvince()
        DistrictWard().getProvinceOffline()

//        mapPresenter = MapPresenter(null)
//        mapPresenter?.setCustomObjectListener(object : MapPresenter.MapPresenterListener {
//            override fun onSearchSuccess() {
//                Log.d("huhu", "345")
////                AddLayer().removeBDSLayers()
////        mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
////                llFrameInfo.visibility = View.VISIBLE
////                llFrameSearch.visibility = View.GONE
////                if (GlobalVariables.bottom_sheet_height < 130) {
////                    GlobalVariables.bottom_sheet_height = containerBottomSheet.height
////                }
//
////        GlobalVariables.landInfoBDSListener.onClickMap()
//            }
//        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_demo, container, false)
        configView(view)

        mapView = view.findViewById(R.id.mapview)
        containerBottomSheet = view.findViewById(R.id.containerBottomSheet)
        btnSearch = view.findViewById(R.id.btn_search)
        llFrameSearch = view.findViewById(R.id.ll_frameSearch)
        llFrameInfo = view.findViewById(R.id.ll_frameInfo)
        btnTransparent = view.findViewById(R.id.btn_transparent)
        llSeekbar = view.findViewById(R.id.ll_seekbar)
        seekBarLayerOpacity = view.findViewById(R.id.seek_bar_layer_opacity)
        btnLocation = view.findViewById(R.id.btn_location)


        btnSearch.setOnClickListener {

            onSearchClick()
        }

        btnTransparent.setOnClickListener {
            if (isTransparent) {
                btnTransparent.setImageResource(R.drawable.transparent)
                llSeekbar.visibility = View.GONE
            } else {
                btnTransparent.setImageResource(R.drawable.ic_transparent_bold)
                llSeekbar.visibility = View.VISIBLE
            }
            isTransparent = !isTransparent

        }

        btnLocation.setOnClickListener {
            if (isClickLocation) {
                btnLocation.setImageResource(R.drawable.gps)
                GlobalVariables.mMap.removeAnnotations()
            } else {
                btnLocation.setImageResource(R.drawable.new_bg_location_color)
                Extension().showToast(
                    R.string.txt_noti_gps,
                    GlobalVariables.activity.applicationContext
                )
                if (!location.latitude.equals(null) && !location.longitude.equals(null)) {
                    if (mBottomSheetBehavior?.state == BottomSheetBehavior.STATE_EXPANDED) {
                        GlobalVariables.bottom_sheet_height = GlobalVariables.height / 3 * 2
//                        mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                    } else if (mBottomSheetBehavior?.state == BottomSheetBehavior.STATE_COLLAPSED) {
                        GlobalVariables.bottom_sheet_height = GlobalVariables.height / 3
                    } else {
                        GlobalVariables.bottom_sheet_height = 0
                    }

                    GlobalVariables.mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            LatLngBounds.from(
                                location.latitude + 0.001f,
                                location.longitude + 0.001f,
                                location.latitude - 0.001f,
                                location.longitude - 0.001f
                            ),
                            110,
                            110,
                            110,
                            GlobalVariables.bottom_sheet_height
                        ), 300
                    )


                    GlobalVariables.mMap.addMarker(
                        MarkerOptions()
//                        .icon(icon)
                            .position(location)
                    )
                }

            }
            isClickLocation = !isClickLocation
        }

        seekBarLayerOpacity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                if (progress > 20) {
                    GlobalVariables.mMap.getStyle {
                        if (it.getLayer("base-layer-so") != null) {
                            it.getLayer("base-layer-so")?.setProperties(
                                rasterOpacity(progress.toFloat() / 100)
                            )
                        }

                        if (GlobalVariables.savedQHPK != null) {
                            for (i in 0 until GlobalVariables.savedQHPK.length() + GlobalVariables.savedQHN.length()) {
                                if (it.getLayer("qhpksdd-layer-" + (i + 1)) != null) {
                                    it.getLayer("qhpksdd-layer-" + (i + 1))!!
                                    rasterOpacity(progress.toFloat() / 100)
                                }
                            }
                        }
                    }

                }
                GlobalVariables.ratioProgress = seekBarLayerOpacity.progress.toFloat()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        mapView?.getMapAsync { mapboxMap ->
            onMapReady(mapboxMap)
        }
        Extension().hideKeyboard(view)
        return view
    }


    override fun onMapReady(mapboxMap: MapboxMap) {
        mapPresenter = MapPresenter(this)
        this.mMap = mapboxMap
        GlobalVariables.mMap = mapboxMap
        mapboxMap.uiSettings.isRotateGesturesEnabled = false
        mapboxMap.uiSettings.isAttributionEnabled = false
        mapboxMap.uiSettings.isLogoEnabled = false
        mapboxMap.cameraPosition = CameraPosition.Builder()
            .target(Extension().checkProvinceLocation(location))
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
        GlobalVariables.mMap.removeAnnotations()
        btnSearch.setImageResource(R.drawable.ic_search_new)
        mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        llFrameInfo.visibility = View.VISIBLE
        llFrameSearch.visibility = View.GONE
        if (GlobalVariables.bottom_sheet_height < 130) {
            GlobalVariables.bottom_sheet_height = containerBottomSheet.height
        }

        GlobalVariables.landInfoBDSListener.onClickMap()
        Thread { MapPresenter(null).getDigitalLandMapinfo(point, activity) }.start()
        return false
    }

    private fun onSearchClick(): Boolean {
        AddLayer().removeBDSLayers()
        GlobalVariables.mMap.removeAnnotations()
        btnSearch.setImageResource(R.drawable.ic_search_new)
        mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        llFrameInfo.visibility = View.GONE
        llFrameSearch.visibility = View.VISIBLE
        if (GlobalVariables.bottom_sheet_height < 130) {
            GlobalVariables.bottom_sheet_height = containerBottomSheet.height
        }

        return false
    }


    private fun configView(view: View) {
        val v: FrameLayout = view.findViewById(R.id.containerBottomSheet)
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        GlobalVariables.height = displayMetrics.heightPixels
        mBottomSheetBehavior = BottomSheetBehavior.from(v)
        if (mBottomSheetBehavior != null) {
            mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            mBottomSheetBehavior?.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                        mBottomSheetBehavior?.peekHeight = GlobalVariables.height / 3 * 2
                    }
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        mBottomSheetBehavior?.peekHeight = GlobalVariables.height / 3
                    }
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        btnSearch.setImageResource(R.drawable.ic_search_new)
                        Extension().hideKeyboard(view)
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (slideOffset == 0f && mBottomSheetBehavior!!.state == BottomSheetBehavior.STATE_DRAGGING) {
                        mBottomSheetBehavior!!.peekHeight = 0
                        Extension().hideKeyboard(view)
                    }
                }
            })
        }
    }

    override fun onSearchSuccess() {
        AddLayer().removeBDSLayers()
        mBottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        llFrameInfo.visibility = View.VISIBLE
        llFrameSearch.visibility = View.GONE
        if (GlobalVariables.bottom_sheet_height < 130) {
            GlobalVariables.bottom_sheet_height = containerBottomSheet.height
        }

    }

    interface CreatePostListener {
        fun sendDataSuccess(info: String?)
    }
}







