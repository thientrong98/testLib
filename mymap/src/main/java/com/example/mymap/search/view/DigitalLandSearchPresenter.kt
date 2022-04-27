package com.example.mymap.search.view

import AddLayer
import ApiHelper
import MapPresenter
import PlanningInfo
import PlanningInfoService
import android.util.Log
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.example.mymap.Helper.MapAddLayerHelper
import com.example.mymap.utils.GlobalVariables
import org.json.JSONArray
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DigitalLandSearchPresenter {
//    private var listener: LoadPlanningInfoListener? = null
//    private var service: PlanningInfoService? = null
//    fun DigitalLandSearchPresenter(listener: LoadPlanningInfoListener?) {
//        this.listener = listener
//        service = ApiHelper().getPlanningInfoService()
//    }

    fun searchPlanningInfoByID(ID: String, activity: FragmentActivity?) {
//        MyApplication.getInstance().trackEvent(
//            "FIND_LAND", MyApplication.CLIENT_ID,
//            MyApplication.CURRENT_LOCATION.toString() + "::" + ID
//        )
        val searchPlanningInfo: Call<PlanningInfo?>? = ApiHelper().getPlanningInfoService()?.getPlanningInfoByLandId(ID)
        searchPlanningInfo?.enqueue(object : Callback<PlanningInfo?> {
            override fun onResponse(
                @NonNull call: Call<PlanningInfo?>,
                @NonNull response: Response<PlanningInfo?>
            ) {
                Log.d("haha", searchPlanningInfo.request().toString())
                Log.d("haha",response.code().toString())
                if (response.code() == 200 && response.body() != null && response.body()?.thongTinChung !=("{}")
                ) {
                    AddLayer().onLoadLandInfoSuccess(response.body()!!, activity)
                    MapPresenter().searchIDSuccess(response.body())
                } else {
                    MapPresenter().searchIDFail("",activity)
                }
            }

            override fun onFailure(@NonNull call: Call<PlanningInfo?>, @NonNull t: Throwable) {
                MapPresenter().searchIDFail("",activity)
            }
        })
    }
}