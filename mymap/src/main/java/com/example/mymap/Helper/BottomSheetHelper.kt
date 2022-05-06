package com.example.mymap.Helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.mymap.R
import com.example.mymap.utils.GlobalVariables
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetHelper : BottomSheetDialogFragment() {
    private val listener: CreatePostListener = GlobalVariables.bottomSheetlistener

    private var unbinder: Unbinder? = null

    private lateinit var btnCreatePost: Button
    private lateinit var btnCancelDownload:Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        unbinder = ButterKnife.bind(this, view)
        btnCreatePost= view.findViewById(R.id.btnCreatePost)
        btnCancelDownload = view.findViewById(R.id.btn_cancel_download)

        btnCreatePost.setOnClickListener{
            clickCreatePost()
        }

        btnCancelDownload.setOnClickListener{
            dismiss()
        }

        return  view
    }

    private fun clickCreatePost() {
        listener.sendDataSuccess()
        dismiss()
    }

    interface CreatePostListener{
       fun sendDataSuccess()
    }
}