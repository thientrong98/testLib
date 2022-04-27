import android.util.Log
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.mymap.LayerMap.ChangeMap
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.geometry.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapPresenter()  {
    private var listener: MapPresenterListener? = null
    fun getDigitalLandMapinfo(
        point: LatLng,
        activity: FragmentActivity?,
    ) {
        getDigitalLandMapinfoByLink(point, activity)
    }

    fun MapPresenter(fragment: Fragment) {
        // set null or default listener or accept as argument to constructor
        listener = fragment as MapPresenterListener
    }

    fun setCustomObjectListener(listener: MapPresenterListener?) {
        this.listener = listener
    }


    private fun getDigitalLandMapinfoByLink(
        point: LatLng,
        activity: FragmentActivity?,
    ) {
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
                        AddLayer().onLoadLandInfoSuccess(response.body()!!, activity)
                        if (GlobalVariables.landInfoBDSListener != null){
                            GlobalVariables.landInfoBDSListener?.onLoadLandInfoSuccess(landInfo)
                        }
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

    fun showInfoOChucNang(maQHPK: String?) {
        GlobalVariables.landInfoBDSListener?.onLoadOChucNangInfoSucces(maQHPK)
    }

//    override fun onLoadLandInfoSuccess(planningInfo: PlanningInfo) {
//        Log.d("haha345","445")
//    }


//    override fun onLoadLandInfoPresenterSuccess(planningInfo: PlanningInfo) {
//        Log.d("haha345", "4456")
//        DemoFragment().getInfo(planningInfo)
//    }

    fun searchIDSuccess(body: PlanningInfo?) {
//        MapPresenter.listenerForActivity.onClickMap()
        Log.d("huhu","123")
        GlobalVariables.landInfoBDSListener.onClickMap()
        GlobalVariables.landInfoBDSListener.onLoadLandInfoSuccess(body)
        listener?.onSearchSuccess()
//        MapPresenter.listener.onLoadLandInfoSuccess(body)
//        MapPresenter.collapse()
    }

    fun searchIDFail(s: String, activity: FragmentActivity?) {
        ChangeMap().showInfoNoData(activity!!)
    }

    interface MapPresenterListener {
        fun onSearchSuccess()
    }
}

