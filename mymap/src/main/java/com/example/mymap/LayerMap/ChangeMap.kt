package com.example.mymap.LayerMap

import ApiHelper
import android.app.AlertDialog
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.core.view.marginLeft
import androidx.fragment.app.FragmentActivity
import com.example.mymap.R
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.geometry.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK


class ChangeMap {
     fun showInfoNoData(activity: FragmentActivity) {
//        runOnUiThread { onPopup(0) }
         onPopup(0, activity )
    }

    private fun onPopup(str: Int,activity: FragmentActivity) {
        val inflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_window, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(activity.window.decorView.rootView, Gravity.TOP, 0, 0)
        val tvError = popupView.findViewById<View>(R.id.tv_error_popup) as TextView
        val ivClose = popupView.findViewById<View>(R.id.im_close_popup) as ImageView
        if (str == 0) {
            tvError.text = "Thửa đất thuộc khu vực chưa được lập Quy hoạch Phân khu."
        } else if (str == 1) {
            tvError.text = "Không tìm thấy số tờ, số thửa bạn nhập. Vui lòng thử tìm kiếm bằng tọa độ góc ranh. Xin cảm ơn."
        }
        ivClose.setOnClickListener { popupWindow.dismiss() }
    }



}