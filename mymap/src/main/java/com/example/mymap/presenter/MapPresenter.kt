import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.mymap.LayerMap.ChangeMap
import com.example.mymap.listener.LandInfoBDSListener
import com.example.mymap.listener.MapPresenterListener
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.geometry.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapPresenter : MapPresenterListener{
//    private  var activity: FragmentActivity? = fragmentActivity

    fun getDigitalLandMapinfo(
        point: LatLng,
        activity: FragmentActivity?,
        landInfoBDSListener: LandInfoBDSListener?
    ) {
//        MapPresenter.listenerForActivity.onClickMap()
        getDigitalLandMapinfoByLink(point, activity,landInfoBDSListener )
    }


    private fun getDigitalLandMapinfoByLink(
        point: LatLng,
        activity: FragmentActivity?,
        landInfoBDSListener: LandInfoBDSListener?
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
                        AddLayer().onLoadLandInfoSuccess(response.body()!!, activity )
                        onLoadLandInfoPresenterSuccess(response.body()!!)
//                        MapPresenterListener.onLoadLandInfoSuccess()
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

//    override fun onLoadLandInfoSuccess(planningInfo: PlanningInfo) {
//        Log.d("haha345","445")
//    }



    override fun onLoadLandInfoPresenterSuccess(planningInfo: PlanningInfo) {
        Log.d("haha345","4456")
        DemoFragment().getInfo(planningInfo)
    }

}

