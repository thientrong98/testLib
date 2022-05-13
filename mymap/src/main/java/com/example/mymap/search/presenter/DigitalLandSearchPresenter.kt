package com.example.mymap.search.presenter

import AddLayer
import ApiHelper
import MapPresenter
import PlanningInfo
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.example.mymap.utils.GlobalVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DigitalLandSearchPresenter {
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

    fun searchPlanningInfoByCoordinate(coordinate: String?,activity: FragmentActivity?) {
        val searchPlanninginfo: Call<PlanningInfo?>? =
            if (GlobalVariables.getCurrentLanguage.equals("vi")) {
                ApiHelper().getPlanningInfoService()!!.getPlanningInfoByCoordinate(coordinate)
            } else {
                ApiHelper().getPlanningInfoService()!!.getPlanningInfoByCoordinateEnglish(coordinate)
            }
        searchPlanninginfo?.enqueue(object : Callback<PlanningInfo?> {
            override fun onResponse(
                @NonNull call: Call<PlanningInfo?>,
                @NonNull response: Response<PlanningInfo?>
            ) {
                if (response.code() == 200 && response.body() != null && response.body()!!.thongTinChung != "{}"
                ) {
                    MapPresenter(null).searchIDSuccess(response.body())
                    AddLayer().onLoadLandInfoSuccess(response.body()!!, activity)
                } else {
                    MapPresenter(null).searchIDFail("",null)
                }
            }

            override fun onFailure(@NonNull call: Call<PlanningInfo?>, @NonNull t: Throwable) {
                MapPresenter(null).searchIDFail("",null)
            }
        })
    }
}