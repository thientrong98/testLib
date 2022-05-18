package com.example.mymap.Helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.mymap.utils.GlobalVariables
import com.mapbox.mapboxsdk.geometry.LatLng

class Extension {
    fun hideKeyboard(view: View?) {
        if (view != null) {
            val inputManager =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun checkProvinceLocation(location: LatLng): LatLng {
        if (GlobalVariables.ws_hcm.latitude < location.latitude && location.latitude < GlobalVariables.ne_hcm.latitude
            && GlobalVariables.ws_hcm.longitude < location.longitude && location.longitude < GlobalVariables.ne_hcm.longitude
        ) {
            return GlobalVariables.center_hcm
        }
        if (GlobalVariables.ws_hue.latitude < location.latitude && location.latitude < GlobalVariables.ne_hue.latitude
            && GlobalVariables.ws_hue.longitude < location.longitude && location.longitude < GlobalVariables.ne_hue.longitude
        ) {
            return GlobalVariables.center_hue
        }

        return GlobalVariables.center_hcm

    }
}