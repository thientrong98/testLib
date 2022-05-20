package com.example.mymap.search.presenter

import AddLayer
import ApiHelper
import MapPresenter
import PlanningInfo
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.mymap.utils.GlobalVariables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DigitalLandSearchPresenter {
    fun searchPlanningInfoByID(
        ID: String,
        activity: FragmentActivity?,
        btnLoadingbutton: CircularProgressButton
    ) {

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
                    btnLoadingbutton.revertAnimation()
                } else {
                    MapPresenter(null).searchFail(1,activity)
                    btnLoadingbutton.revertAnimation()
                }
            }

            override fun onFailure(@NonNull call: Call<PlanningInfo?>, @NonNull t: Throwable) {
                MapPresenter(null).searchFail(1,activity)
                btnLoadingbutton.revertAnimation()
            }
        })
    }

    fun searchPlanningInfoByCoordinate(
        coordinate: String?,
        activity: FragmentActivity?,
        btnLoadingButtonSearch: CircularProgressButton
    ) {
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
                    btnLoadingButtonSearch.revertAnimation()

                } else {
                    MapPresenter(null).searchFail(2,activity)
                    btnLoadingButtonSearch.revertAnimation()
                }
            }

            override fun onFailure(@NonNull call: Call<PlanningInfo?>, @NonNull t: Throwable) {
                MapPresenter(null).searchFail(2,activity)
                btnLoadingButtonSearch.revertAnimation()

            }
        })
    }
}