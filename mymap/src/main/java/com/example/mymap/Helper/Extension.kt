package com.example.mymap.Helper

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Extension {
    fun hideKeyboard(view: View?) {
        if (view!= null){
            val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }
}