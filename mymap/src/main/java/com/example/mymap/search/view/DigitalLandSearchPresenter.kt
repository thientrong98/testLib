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

        val searchPlanningInfo: Call<PlanningInfo?>? = ApiHelper().getPlanningInfoService()?.getPlanningInfoByLandId(ID)
        searchPlanningInfo?.enqueue(object : Callback<PlanningInfo?> {
            override fun onResponse(
                @NonNull call: Call<PlanningInfo?>,
                @NonNull response: Response<PlanningInfo?>
            ) {
                if (response.code() == 200 && response.body() != null && response.body()?.thongTinChung !=("{}")
                ) {
                    MapPresenter(null).searchIDSuccess(response.body())
                    AddLayer().onLoadLandInfoSuccess(response.body()!!, activity)
                } else {
                    MapPresenter(null).searchIDFail("",activity)
                }
            }

            override fun onFailure(@NonNull call: Call<PlanningInfo?>, @NonNull t: Throwable) {
                MapPresenter(null).searchIDFail("",activity)
            }
        })
    }
}