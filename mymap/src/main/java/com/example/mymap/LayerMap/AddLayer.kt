import android.app.AlertDialog
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ToastUtils
import com.example.mymap.Helper.MapAddLayerBDSHelper
import com.example.mymap.Helper.MapAddLayerHelper
import com.example.mymap.R
import com.example.mymap.utils.GlobalVariables
import com.google.gson.GsonBuilder
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.maps.MapboxMap
import org.json.JSONArray
import org.json.JSONException
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK

class AddLayer {

    var gson = GsonBuilder().create()


    fun onShowBanDoGiayQHPKSucess(qhpk: QHPK, mMap: MapboxMap) {
//        if (it == null) {
//            ToastUtils.showLong(R.string.txt_loi_thu_lai)
//            return
//        }
//        MapPresenter.showInfoQHPK(qhpk)
//        if (.qhpkIDCurrent.equals(
//                qhpk.getMaQH(),
//                ignoreCase = true
//            )
//        ) {
//            return
//        }
        removeBDGQHPK(mMap)
        removeDCCB(mMap)
//        savedQHPKRaster = qhpk
        GlobalVariables.qhpkIDCurrent = qhpk.maQHPKRanh.toString()
        if (mMap.cameraPosition.zoom < 15) {
            MapAddLayerHelper().zoomToRaster(
                qhpk.ranh,
                mMap
            )
        }
        if (GlobalVariables.qhpkIDCurrent != null) {
            MapAddLayerHelper().addRasterQHPKLayer(
                mMap,
                GlobalVariables.qhpkIDCurrent,
                qhpk
            )
        }
        GlobalVariables.numberOfDCCB = qhpk.dCCB?.size ?: 0
    }

    fun onShowDialogBanDoGiayQHPKsSucess(qhpk: ArrayList<QHPK?>?, activity: FragmentActivity?) {
        setUpChoosingRasterQHPKDialog(qhpk, activity )
    }

    fun onShowBanDoGiayDCCBSucess(ranh: String?, dccbCurrent: String) {
        if (GlobalVariables.mMap == null) {
//            ToastUtils.showLong(R.string.txt_loi_thu_lai)
            return
        }
        removeBDGQHPK(GlobalVariables.mMap)
        removeDCCB(GlobalVariables.mMap)
        GlobalVariables.dccbCurrent = dccbCurrent
        GlobalVariables.isClickDCCB = true
        //chi zoom toi thua dat khi co do zoom nho, boi vi co the nguoi dan zoom toi de tim nha
        if (GlobalVariables.mMap.cameraPosition.zoom < 15) {
            MapAddLayerHelper().zoomToRaster(ranh, GlobalVariables.mMap)
        }
        MapAddLayerHelper().addRasterDCCBLayer(
            GlobalVariables.mMap,
            dccbCurrent
        )
    }

    private fun setUpChoosingRasterQHPKDialog(qhpk: ArrayList<QHPK?>?, activity: FragmentActivity?) {
//        MapPresenter.hidden()
        val planningNames: MutableList<String> = java.util.ArrayList()
        for (q in qhpk!!) {
            val str = q?.SoQD + " | " + q?.NgayDuyet
            val ss = SpannableString(str)
            val fg = ForegroundColorSpan(Color.GREEN)
            ss.setSpan(fg, 0, str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            planningNames.add(
                """
                $ss
                ${q?.tenDoAn}
                
                """.trimIndent()
            )
        }
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.CustomAlertDialog)
        builder.setTitle(R.string.choose_do_an)
        builder.setSingleChoiceItems(
            planningNames.toTypedArray(),
            -1
        ) { dialogInterface, i ->
//            MapPresenter.expand()
            onShowBanDoGiayQHPKSucess(qhpk!![i]!!, GlobalVariables.mMap)
            dialogInterface.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun onLoadLandInfoSuccess(body: PlanningInfo,activity: FragmentActivity?) {
        if (GlobalVariables.mMap == null) {
            ToastUtils.showLong(R.string.txt_loi_thu_lai)
            return
        }
        try {

            GlobalVariables.mMap.removeAnnotations()
            removeBDSLayers()
                GlobalVariables.savedQHPK = JSONArray(body.qHPK)
            GlobalVariables.savedQHN = JSONArray(body.qHNganh)
            MapAddLayerBDSHelper().addQHPKLayers(
                activity ,
                GlobalVariables.mMap,
                JSONArray(body.qHNganh),
                JSONArray(body.qHPK)
            )
            val ttc: ThongTinChung =
                gson.fromJson(body.thongTinChung, ThongTinChung::class.java)
            onLandZoomed(ttc.ranh)
//            MyApplication.getInstance().trackEvent(
//                "VIEW_LAND", MyApplication.CLIENT_ID,
//                MyApplication.CURRENT_LOCATION.toString() + "::" + ttc.getMathuadat()
//            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

   private fun onLandZoomed(coordinate: String?) {
        try {
            val landCoordinate = JSONArray(coordinate).getJSONArray(0).getJSONArray(0)
            GlobalVariables.mMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    LocationHelper().getCenterBounds(landCoordinate)!!, 110,
                    110, 110, GlobalVariables.bottom_sheet_height
                )
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun removeDCCB(mMap: MapboxMap) {
        //xoa tat ca dieu chinh cuc bo neu co
        for (i in 0..GlobalVariables.numberOfDCCB) {

            mMap.getStyle {
                it.removeLayer("$i-sketch-layer")
                it.removeSource("$i-sketch-source")
            }
        }
    }

    private fun removeBDGQHPK(mMap: MapboxMap) {
//        if (it == null) {
//            ToastUtils.showLong(R.string.txt_loi_thu_lai)
//            return
//        }
        val qhpkIDCurrent = GlobalVariables.qhpkIDCurrent
        val dccbCurrent = GlobalVariables.dccbCurrent
        //xoa het cac layer ban do giay
        mMap.getStyle {
            if (qhpkIDCurrent.isNotEmpty()) {
                it.removeLayer("$qhpkIDCurrent-qhpk-layer")
                it.removeSource("$qhpkIDCurrent-qhpk-source")

                it.removeLayer("$qhpkIDCurrent-full-qhpk-layer")
                it.removeSource("$qhpkIDCurrent-full-qhpk-source")
            }

            if (dccbCurrent.isNotEmpty()) {
                it.removeLayer("$dccbCurrent-dccb-layer")
                it.removeSource("$dccbCurrent-dccb-source")

                it.removeLayer("$dccbCurrent-full-dccb-layer")
                it.removeSource("$dccbCurrent-full-dccb-source")
            }
        }
//        deselectMarkers()
        for (i in 0..9) {
            mMap.getStyle {
                if (it.getLayer("qhpksdd-stroke-" + (i + 1).toString()) != null) {
                    it.removeLayer("qhpksdd-stroke-" + (i + 1).toString())
                }
            }

        }
    }

     fun removeBDSLayers() {
//        if (it == null) {
//            ToastUtils.showLong(R.string.txt_loi_thu_lai)
//            return
//        }
        //xoa het layer da ve o ban do so
        if (GlobalVariables.savedQHPK != null && GlobalVariables.savedQHN != null) {
            for (i in 0 until GlobalVariables.savedQHPK.length() + GlobalVariables.savedQHN.length()) {
                GlobalVariables.mMap.getStyle {
                    if (it.getLayer("qhpksdd-stroke-" + (i + 1).toString()) != null) {
                        it.removeLayer("qhpksdd-stroke-" + (i + 1).toString())
                    }
                    if (it.getLayer("qhpksdd-layer-" + (i + 1).toString()) != null) {
                        it.removeLayer("qhpksdd-layer-" + (i + 1).toString())
                    }
                    if (it.getSource("qhpksdd-source-" + (i + 1).toString()) != null) {
                        it.removeSource("qhpksdd-source-" + (i + 1).toString())
                    }
                    if (it.getLayer("QHN-layer-" + (i + 1).toString()) != null) {
                        it.removeLayer("QHN-layer-" + (i + 1).toString())
                    }
                    if (it.getSource("QHN-source-" + (i + 1).toString()) != null) {
                        it.removeSource("QHN-source-" + (i + 1).toString())
                    }
                }
               
            }
        }
//        .removeOChucNangLayer()

    }

    private fun resetVariable() {
        GlobalVariables.numberOfDCCB = 0
        GlobalVariables.qhpkIDCurrent = ""
        GlobalVariables.dccbCurrent = ""
//        GlobalVariables.currentCdnID = ""
        GlobalVariables.isClickDCCB = false
    }

    fun removeAllLayersAndVariables() {
        removeBDGQHPK(GlobalVariables.mMap)
        removeBDSLayers()
        removeDCCB(GlobalVariables.mMap)
//        removeCDNLayer()
//        mMap.deselectMarkers()
        GlobalVariables.mMap.removeAnnotations()
        resetVariable()
    }


}