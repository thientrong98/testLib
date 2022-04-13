import android.util.Log
import androidx.annotation.NonNull
import com.example.mymap.Helper.MapAddLayerHelper
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK

class MapPresenter {
    fun showBanDoGiayQHPK(point: LatLng,mMap:MapboxMap) {
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
                    Log.d("huhu",response.body()!!.size.toString() )
                    if (response.body()!!.size > 1) {
//                        MapPresenter.listener.onShowDialogBanDoGiayQHPKsSucess(response.body())
                    } else {
                        AddLayer().onShowBanDoGiayQHPKSucess(response.body()!![0]!!,mMap )
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
}