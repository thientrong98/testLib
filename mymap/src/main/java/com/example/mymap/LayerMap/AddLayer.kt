import com.example.mymap.Helper.MapAddLayerHelper
import com.mapbox.mapboxsdk.maps.MapboxMap
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK

class AddLayer {
    private var numberOfDCCB = 0
    private var qhpkIDCurrent = ""
    private var dccbCurrent = ""


    fun onShowBanDoGiayQHPKSucess(qhpk: QHPK, mMap:MapboxMap) {
//        if (tech.vlab.ttqhhcm.new_ui.map.view.MapFragment.mMap == null) {
//            ToastUtils.showLong(R.string.txt_loi_thu_lai)
//            return
//        }
//        MapPresenter.showInfoQHPK(qhpk)
//        if (tech.vlab.ttqhhcm.new_ui.map.view.MapFragment.qhpkIDCurrent.equals(
//                qhpk.getMaQH(),
//                ignoreCase = true
//            )
//        ) {
//            return
//        }
        removeBDGQHPK(mMap)
       removeDCCB(mMap)
//        savedQHPKRaster = qhpk
        qhpkIDCurrent = qhpk.maQHPKRanh.toString()
//        if (tech.vlab.ttqhhcm.new_ui.map.view.MapFragment.mMap.getCameraPosition().zoom < 15) {
//            MapAddLayerHelper.zoomToRaster(
//                qhpk.getRanh(),
//                tech.vlab.ttqhhcm.new_ui.map.view.MapFragment.mMap
//            )
//        }
        if (qhpkIDCurrent != null) {
            MapAddLayerHelper().addRasterQHPKLayer(
                mMap ,
                qhpkIDCurrent,
                qhpk
            )
        }
        numberOfDCCB = qhpk.dCCB?.size ?: 0
    }

    private fun removeDCCB(mMap: MapboxMap) {
        //xoa tat ca dieu chinh cuc bo neu co
        for (i in 0..numberOfDCCB) {

            mMap.getStyle {
                it.removeLayer("$i-sketch-layer")
                it.removeSource("$i-sketch-source")
            }
        }
    }

    private fun removeBDGQHPK(mMap: MapboxMap) {
//        if (tech.vlab.ttqhhcm.new_ui.map.view.MapFragment.mMap == null) {
//            ToastUtils.showLong(R.string.txt_loi_thu_lai)
//            return
//        }
        //xoa het cac layer ban do giay
        mMap.getStyle {
            it.removeLayer("$qhpkIDCurrent-qhpk-layer")
            it.removeSource("$qhpkIDCurrent-qhpk-source")

            it.removeLayer("$qhpkIDCurrent-full-qhpk-layer")
            it.removeSource("$qhpkIDCurrent-full-qhpk-source")

            it.removeLayer("$dccbCurrent-dccb-layer")
            it.removeSource("$dccbCurrent-dccb-source")

            it.removeLayer("$dccbCurrent-full-dccb-layer")
            it.removeSource("$dccbCurrent-full-dccb-source")
        }
//        deselectMarkers()
//        for (i in 0..9) {
//            if (tech.vlab.ttqhhcm.new_ui.map.view.MapFragment.mMap.getLayer("qhpksdd-stroke-" + (i + 1).toString()) != null) {
//                tech.vlab.ttqhhcm.new_ui.map.view.MapFragment.mMap.removeLayer("qhpksdd-stroke-" + (i + 1).toString())
//            } else {
//                break
//            }
//        }
    }
}