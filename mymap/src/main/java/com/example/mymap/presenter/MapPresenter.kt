import android.util.Log
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.example.mymap.LayerMap.ChangeMap
import com.example.mymap.utils.GlobalVariables
import com.mapbox.geojson.Feature
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK
import java.util.*

class MapPresenter {
    fun showBanDoGiayQHPK(point: LatLng, mMap:MapboxMap, activity: FragmentActivity?) {
        val map: MutableMap<String, Double> = HashMap()
        map["Lat"] = point.latitude
        map["Lon"] = point.longitude
        val call: Call<java.util.ArrayList<QHPK?>?>? = if (GlobalVariables.getCurrentLanguage.equals("vi"))
            ApiHelper().getRasterPlanningInfoService()?.getQHPK(map)
         else
            ApiHelper().getRasterPlanningInfoService()?.getQHPKEnglish(map)

        call?.enqueue(object : Callback<ArrayList<QHPK?>?> {
            override fun onResponse(
                @NonNull call: Call<ArrayList<QHPK?>?>,
                @NonNull response: Response<ArrayList<QHPK?>?>
            ) {
                if (response.code() == 200 && response.body() != null && response.body()!!.size > 0) {
                    if (response.body()!!.size > 1) {
                        AddLayer().onShowDialogBanDoGiayQHPKsSucess(response.body(), activity )
                    } else {
                        AddLayer().onShowBanDoGiayQHPKSucess(response.body()!![0]!!, mMap)
                    }
                } else {
//                    MapPresenter.listenerForActivity.onHiddenMap()
//                    MapPresenter.listener.onShowBanDoGiayQHPKFail()
                }
            }

            override fun onFailure(@NonNull call: Call<ArrayList<QHPK?>?>, @NonNull t: Throwable) {
//                MapPresenter.listenerForActivity.onHiddenMap()
//                MapPresenter.listener.onShowBanDoGiayQHPKFail()
            }
        })
    }

    fun showDCCBCrop(features: List<Feature>, point: LatLng?,activity: FragmentActivity?) {
//        MapPresenter.listenerForActivity.onClickMap()
        var flag = false
        for (f in features) {
            if (f.getProperty("maDCCB") != null) {
                flag = true
                var g: JSONObject? = null
                var ranh = ""
                val dccbCurrent = f.getProperty("maDCCB").asString
                try {
                    g = JSONObject(Objects.requireNonNull(f.geometry())!!.toJson())
                    ranh = g.getJSONArray("coordinates").toString()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                //                JSONObject jo = new JSONObject(Objects.requireNonNull(f.getProperty("qhpk")).toJson());
                val qhpk = QHPK()
                qhpk.tenDoAn = f.getProperty("tenDCCB").asString
                //                JsonObject qhpk = f.getProperty("qhpk").getAsJsonObject();
//                Log.d("haha qhpk", String.valueOf(qhpk.toString()));
//                MapPresenter.listenerForActivity.fillPlanningInfoDCCBex(
//                    f.getProperty("tenQHPK").asString,
//                    f.getProperty("qh").asString,
//                    f.getProperty("tenDCCB").asString,
//                    f.getProperty("soqd").asString,
//                    f.getProperty("cqpd").asString,
//                    f.getProperty("ngayduyet").asString,
//                    f.getProperty("maDCCB").asString,
//                    f.getProperty("ranhDCCB").asString
//                )
                AddLayer().onShowBanDoGiayDCCBSucess(ranh, dccbCurrent)
                break
            }
        }
        if (!flag) {
            showBanDoGiayQHPK(point!!, GlobalVariables.mMap, activity )
        }
    }

    fun getDigitalLandMapinfo(point: LatLng, activity: FragmentActivity?) {
//        MapPresenter.listenerForActivity.onClickMap()
        getDigitalLandMapinfoByLink(point, activity )
    }


    private fun getDigitalLandMapinfoByLink(point: LatLng,activity: FragmentActivity?) {
        val searchPlanningInfo: Call<PlanningInfo?>? =
            if (GlobalVariables.getCurrentLanguage.equals("vi")) {
                ApiHelper().getPlanningInfoService()
                    ?.getPlanningInfoByLatLng(point.latitude, point.longitude)
            } else {
                ApiHelper().getPlanningInfoService()
                    ?.getPlanningInfoByLatLngEnglish(point.latitude, point.longitude)
            }
        searchPlanningInfo?.enqueue(object : Callback<PlanningInfo?> {
            override fun onResponse(
                @NonNull call: Call<PlanningInfo?>,
                @NonNull response: Response<PlanningInfo?>
            ) {
                if (response.code() == 200 && response.body() != null) {
                    val landInfo = response.body()
                    if (!landInfo?.thongTinChung.equals("{}")) {
                        AddLayer().onLoadLandInfoSuccess(response.body()!!, activity )
//                        MapPresenter.listener.onLoadLandInfoSuccess(response.body())
                    } else {
                        ChangeMap().showInfoNoData(activity!!)
                    }
                } else {
                    ChangeMap().showInfoNoData(activity!!)
                }
            }

            override fun onFailure(@NonNull call: Call<PlanningInfo?>, @NonNull t: Throwable) {
                ChangeMap().showInfoNoData(activity!!)


            }
        })
    }
}