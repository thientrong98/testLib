package com.example.mymap.search.view

import DistrictWard
import Province
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.mymap.Helper.Extension
import com.example.mymap.R
import com.example.mymap.search.presenter.DigitalLandSearchPresenter
import com.example.mymap.utils.GlobalVariables
import kotlinx.android.synthetic.main.fragment_land_id_search.*
import kotlinx.coroutines.*
import java.util.*


class LandIDSearchFragment : Fragment() {
    private lateinit var txtChooseDistrict: TextView
    private lateinit var txtChooseWard: TextView
    private lateinit var txtChooseProvince: TextView
    private lateinit var formSoThua: LinearLayout
    private lateinit var formSoTo: LinearLayout
    private lateinit var formPhuongXa: LinearLayout
    private lateinit var formMaOPho: LinearLayout
    private var wardID = ""
    private lateinit var edtMaLoDat: EditText
    private lateinit var edtSoTo: EditText
    private lateinit var edtSoThua: EditText
    private lateinit var btnLoadingButtonSearch : CircularProgressButton
    private lateinit var btnLoadingButtonClear: CircularProgressButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        btnLoadingButtonSearch.dispose()
        btnLoadingButtonClear.dispose()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        var view = inflater.inflate(R.layout.fragment_land_id_search, container, false)
        txtChooseDistrict = view.findViewById(R.id.txtChooseDistrict)
        txtChooseWard = view.findViewById(R.id.txtChooseWard)
        txtChooseProvince = view.findViewById(R.id.txtChooseProvince)
        formPhuongXa = view.findViewById(R.id.formPhuongXa)
        formMaOPho = view.findViewById(R.id.formMaOPho)
        formSoThua = view.findViewById(R.id.formSoThua)
        formSoTo = view.findViewById(R.id.formSoTo)
        edtMaLoDat = view.findViewById(R.id.edtMaLoDat)
        edtSoTo = view.findViewById(R.id.edtSoTo)
        edtSoThua = view.findViewById(R.id.edtSoThua)
        btnLoadingButtonSearch = view.findViewById(R.id.btn_loadingButtonSearch)
        btnLoadingButtonClear = view.findViewById(R.id.btn_loadingButtonClear)

        txtChooseProvince.setOnClickListener {
            onClick(view, "txtChooseProvince")
        }
        txtChooseDistrict.setOnClickListener {
            if (txtChooseProvince.text.equals("")){
                Extension().showToast(R.string.txt_choose_province_before, GlobalVariables.activity.applicationContext)
            }else{
                onClick(view, "txtChooseDistrict")
            }
        }

        txtChooseWard.setOnClickListener {
            if (!txtChooseProvince.text.equals("") && !txtChooseDistrict.text.equals(""))
            onClick(view, "txtChooseWard") else
            Extension().showToast(R.string.txt_choose_province_and_district_before, GlobalVariables.activity.applicationContext)
        }

        btnLoadingButtonClear.setOnClickListener{
            onRewrite()
        }

        btnLoadingButtonSearch.setOnClickListener {
            onClick(view, "search")
        }
        return view
    }

    private fun onRewrite() {
        btnLoadingButtonClear.startAnimation{
            wardID = ""
            edtSoTo.setText("")
            edtSoThua.setText("")
            txtChooseDistrict.text = ""
            txtChooseWard.text = ""
            txtChooseProvince.text = ""
        }
        btnLoadingButtonClear.revertAnimation()

    }

    private fun onClick(view: View, type: String) {
        when (type) {
            "txtChooseProvince" -> {
                showPopUpProvince(view)
            }

            "txtChooseDistrict" -> {
                showPopUp(view, false, false)
            }

            "txtChooseWard" -> {
                showPopUp(view, true, false)
            }
            "search" -> {
                btnLoadingButtonSearch.startAnimation()

                if (!Extension().isNetworkAvailable(GlobalVariables.activity)) {
                    Extension().showToast(R.string.no_connect_error, GlobalVariables.activity.applicationContext)
                    btnLoadingButtonSearch.revertAnimation {
                        btnLoadingButtonSearch.text = getText(R.string.tim_kiem)
                        btnLoadingButtonSearch.background = resources.getDrawable(R.drawable.bg_animation_search)
                    }
                    return
                }

                if (formMaOPho.visibility == View.VISIBLE) {
                    val maLoDat: String = edtMaLoDat.text.toString()
                    val landID = wardID + "_" + maLoDat
                    if (maLoDat.isEmpty()) {
                        Extension().showToast(R.string.txt_ma_lo, GlobalVariables.activity.applicationContext)
                        return
                    }
                    DigitalLandSearchPresenter().searchPlanningInfoByID(
                        landID,
                        activity,
                        btnLoadingButtonSearch
                    )
                } else {
//                    MapPresenter.hiddenBottomSheet()
                    val soTo: String = edtSoTo.text.toString()
                    val soThua: String = edtSoThua.text.toString()

                    if (soTo.isEmpty() || soThua.isEmpty() || wardID.isEmpty()) {
                        Extension().showToast(R.string.txt_fill_all_input, GlobalVariables.activity.applicationContext)
                        btnLoadingButtonSearch.revertAnimation {
                            btnLoadingButtonSearch.text = getText(R.string.tim_kiem)
                        }
                        return
                    }
                    val parcel = String.format(Locale.getDefault(), "%03d", soTo.toInt())
                    val lot = String.format(Locale.getDefault(), "%04d", soThua.toInt())
                    val landID = wardID + parcel + lot

                    Extension().hideKeyboard(view)
                    DigitalLandSearchPresenter().searchPlanningInfoByID(landID,activity, btnLoadingButtonSearch)

                }

            }


        }
    }

    private fun showPopUpProvince(view: View) {
        val contextThemeWrapper = ContextThemeWrapper(context, R.style.PopupMenuOverlapAnchor)
//        val menu = PopupMenu(contextThemeWrapper, view)

        val menu = PopupMenu(context, view, Gravity.NO_GRAVITY, R.attr.actionOverflowMenuStyle, 0)
//        menu.gravity = Gravity.CENTER_VERTICAL
        var list: Array<String?>
        val getProvinceName: Array<String>? = GlobalVariables.provinceName
        val getProvinceId: Array<String> = GlobalVariables.provinceID

        if (GlobalVariables.provinceName != null) {
            for (i in getProvinceName!!.indices) {
                menu.menu.add(getProvinceName[i]).titleCondensed = i.toString() + ""
            }
        }

        menu.setOnMenuItemClickListener { menuItem ->
            txtChooseDistrict.text = ""
            txtChooseWard.text = ""
            txtChooseProvince.text = menuItem.title

            var index: Int = GlobalVariables.provinceName!!.indexOf(menuItem.title)
            if (index != null) {
                GlobalScope.launch(Dispatchers.IO) {
                    suspend {
                        val resultGetDistrict: Deferred<List<Province.Quanhuyen>?> = async {  DistrictWard().getDistrictWardById(GlobalVariables.provinceID[index])}
                        var list   = resultGetDistrict.await()
                        delay(1000)
                        Log.d("haha", list?.size.toString())
                        if(GlobalVariables.districtName != null){
                            txtChooseDistrict.text = GlobalVariables.districtName[0]
                        }

                        if (GlobalVariables.wardName != null) {
                            txtChooseWard.text = GlobalVariables.wardName.first().first()
                            wardID = GlobalVariables.wardId.first().first()
                        }
                    }.invoke()

//                    Log.d("haha", (GlobalVariables.districtName != null).toString())
                }



//                if(GlobalVariables.districtName.isNotEmpty()){
//                    txtChooseDistrict.text = GlobalVariables.districtName[0].toString()
//                }
            }

            false
        }
        menu.show()
    }


    private fun showPopUp(view: View, isWard: Boolean, isProvince: Boolean) {
        val menu = PopupMenu(context, view,Gravity.NO_GRAVITY, R.attr.actionOverflowMenuStyle, R.drawable.new_bg_land_info)
//        menu.gravity = Gravity.CENTER
        var list: Array<String?>
        val getDistrictName: Array<String?> = GlobalVariables.districtName
        val getDictrictId: Array<String> = GlobalVariables.districtId
        val getWardname: Array<Array<String>> = GlobalVariables.wardName
        val getWardId: Array<Array<String>> = GlobalVariables.wardId

        list = getDistrictName

        if (isWard) {
            var checkDistrict = 0
            for (i in getDistrictName.indices) {
                if (txtChooseDistrict.text.toString() == getDistrictName[i]) {
                    checkDistrict = 1
                    list = arrayOfNulls(getWardname[i].size)
                    for (j in 0 until getWardname[i].size) {
                        list[j] = getWardname[i][j]
                    }
                    break
                }
            }
            if (checkDistrict == 0) {
                Extension().showToast(R.string.txt_choose_district_before, GlobalVariables.activity.applicationContext)
                return
            }
        }
        for (i in list.indices) {
            menu.menu.add(list[i]).titleCondensed = i.toString() + ""
        }


        menu.setOnMenuItemClickListener { menuItem ->
            if (isWard) {
                txtChooseWard.text = menuItem.title
                for (i in getDistrictName.indices) {
                    if (txtChooseDistrict.text.toString() == getDistrictName[i]) {
                        for (j in 0 until getWardname[i].size) {
                            if (txtChooseWard.text.toString() == getWardname[i][j]) {
                                wardID = getWardId[i][j]
                                break
                            }
                        }
                        break
                    }
                }
            } else {
                txtChooseDistrict.text = menuItem.title
                for (i in getDistrictName.indices) {
                    if (txtChooseDistrict.text.toString() == getDistrictName[i]) {
                        val listWard =
                            arrayOfNulls<String>(getWardname[i].size)
                        if (listWard.isEmpty()) {
                            wardID = getDictrictId[i].substring(0, 3)
                            formPhuongXa.visibility = View.GONE
                            formSoThua.visibility = View.GONE
                            formSoTo.visibility = View.GONE
                            formMaOPho.visibility = View.VISIBLE
                        } else {
                            txtChooseWard.text = getWardname[i][0]
                            wardID = getWardId[i][0]
                            formPhuongXa.visibility = View.VISIBLE
                            formSoThua.visibility = View.VISIBLE
                            formSoTo.visibility = View.VISIBLE
                            formMaOPho.visibility = View.GONE
                        }
                        break
                    }
                }
            }
            false
        }
        menu.show()
    }
}



