package com.example.mymap.LayerMap

import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Handler
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.mymap.R


class ChangeMap {
     fun showInfoNoData(activity: FragmentActivity) {
//        runOnUiThread { onPopup(0) }
         onPopup(0, activity )

    }

    private fun onPopup(str: Int,activity: FragmentActivity) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels -40

        val inflater = activity.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_window, null)
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width , height, focusable)
        popupWindow.showAtLocation(activity.window.decorView.rootView, Gravity.TOP, 0, 0)
        val tvError = popupView.findViewById<View>(R.id.tv_error_popup) as TextView
        val ivClose = popupView.findViewById<View>(R.id.im_close_popup) as ImageView
        if (str == 0) {
            tvError.text = "Thửa đất thuộc khu vực chưa được lập Quy hoạch Phân khu."
        } else if (str == 1) {
            tvError.text = "Không tìm thấy số tờ, số thửa bạn nhập. Vui lòng thử tìm kiếm bằng tọa độ góc ranh. Xin cảm ơn."
        }
        ivClose.setOnClickListener { popupWindow.dismiss() }

        val handler = Handler()
        handler.postDelayed({
            popupWindow.dismiss()
        }, 5000)
    }



}