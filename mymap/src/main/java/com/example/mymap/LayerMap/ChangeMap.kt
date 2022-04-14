package com.example.mymap.LayerMap

import ApiHelper
import ChangeLayer
import MapPresenter
import android.app.AlertDialog
import android.content.DialogInterface.OnShowListener
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.mymap.R
import com.example.mymap.utils.Constants
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.geometry.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK


class ChangeMap {
     fun showInfoChangeMap(point: LatLng,activity: FragmentActivity) {
//        val point: LatLng = MapPresenter.point()
        val map: MutableMap<String, Double> = HashMap()
        map["Lat"] = point.latitude
        map["Lon"] = point.longitude
         val call: Call<java.util.ArrayList<QHPK?>?>? = if (GlobalVariables.getCurrentLanguage.equals("vi"))
             ApiHelper().getRasterPlanningInfoService()?.getQHPK(map)
         else
             ApiHelper().getRasterPlanningInfoService()?.getQHPKEnglish(map)

         call?.enqueue(object : Callback<ArrayList<QHPK?>?> {
            override fun onResponse(
                call: Call<ArrayList<QHPK?>?>,
                response: Response<ArrayList<QHPK?>?>
            ) {
                val qhpks = response.body()!!
                Log.d("hmm123", response.code().toString())
                if (response.code() == 200 && qhpks.size > 0) {
                    showInfoHaveData(point, activity )
                } else {
                    showInfoNoData()
                }
            }

            override fun onFailure(call: Call<ArrayList<QHPK?>?>, t: Throwable) {
                showInfoNoData()
            }
        })
    }

    private fun showInfoNoData() {
//        runOnUiThread { onPopup(0) }
    }

    private fun showInfoHaveData(point: LatLng,activity:FragmentActivity) {

//        runOnUiThread {
            val builder: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.CustomAlertDialog)
            builder.setTitle(R.string.title_data_updating)
            builder.setMessage(R.string.txt_change)
            builder.setCancelable(false)
            builder.setPositiveButton(R.string.txt_chuyen) { dialog, which ->
                // Dismiss dialog and quit
                GlobalVariables.getCurrentForeground =Constants.Style.FG_TTQH_GIAY
                ChangeLayer().changeMapForeground("FG_TTQH_GIAY",activity)
                MapPresenter().showBanDoGiayQHPK(point,GlobalVariables.mMap,activity)
            }

            builder.setNegativeButton(R.string.text_cancel) { dialog, which ->
                // Dismiss dialog and quit
                dialog.dismiss()
            }


        builder.show()
//        }
    }
}