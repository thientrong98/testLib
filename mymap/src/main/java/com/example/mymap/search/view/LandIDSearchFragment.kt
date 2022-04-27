package com.example.mymap.search.view

import AddLayer
import DistrictWard
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.example.mymap.R
import com.example.mymap.utils.GlobalVariables
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
    private lateinit var txtSearch: TextView
    private lateinit var edtMaLoDat: EditText
    private lateinit var edtSoTo: EditText
    private lateinit var edtSoThua: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_land_id_search, container, false)
        txtChooseDistrict = view.findViewById(R.id.txtChooseDistrict)
        txtChooseWard = view.findViewById(R.id.txtChooseWard)
        txtChooseProvince = view.findViewById(R.id.txtChooseProvince)
        formPhuongXa = view.findViewById(R.id.formPhuongXa)
        formMaOPho = view.findViewById(R.id.formMaOPho)
        formSoThua = view.findViewById(R.id.formSoThua)
        formSoTo = view.findViewById(R.id.formSoTo)
        txtSearch = view.findViewById(R.id.txtSearch)
        edtMaLoDat = view.findViewById(R.id.edtMaLoDat)
        edtSoTo = view.findViewById(R.id.edtSoTo)
        edtSoThua = view.findViewById(R.id.edtSoThua)


        txtChooseProvince.setOnClickListener {
            onClick(view, "txtChooseProvince")
        }
        txtChooseDistrict.setOnClickListener {
            onClick(view, "txtChooseDistrict")
        }

        txtChooseWard.setOnClickListener {
            onClick(view, "txtChooseWard")
        }

        txtSearch.setOnClickListener {
            onClick(view, "search")
        }


        return view
    }

    private fun onClick(view: View, type: String) {
        when (type) {
            "txtChooseProvince" -> {
                showPopUpProvince(view)
            }

            "txtChooseDistrict" -> {
                Log.d("haha", "1233")
                showPopUp(view, false, false)
            }

            "txtChooseWard" -> {
                showPopUp(view, true, false)
            }
            "search" -> {
                if (!NetworkUtils.isConnected()) {
                    ToastUtils.showLong(getString(R.string.no_connect_error))
                    return
                }

                if (formMaOPho.visibility == View.VISIBLE) {
                    val maLoDat: String = edtMaLoDat.text.toString()
                    val landID = wardID + "_" + maLoDat
                    if (maLoDat.isEmpty()) {
                        ToastUtils.showLong(R.string.txt_ma_lo)
                        return
                    }
//                    DigitalLandSearchPresenter(this).searchPlanningInfoByID(landID)
                } else {
//                    MapPresenter.hiddenBottomSheet()
                    val soTo: String = edtSoTo.text.toString()
                    val soThua: String = edtSoThua.text.toString()

                    if (soTo.isEmpty() || soThua.isEmpty() || wardID.isEmpty()) {
                        ToastUtils.showLong(R.string.txt_fill_all_input)
                        return
                    }
                    val parcel = String.format(Locale.getDefault(), "%03d", soTo.toInt())
                    val lot = String.format(Locale.getDefault(), "%04d", soThua.toInt())
                    val landID = wardID + parcel + lot
                    val v = getView()
                    if (v != null) {
                        val imm =
                            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(v.windowToken, 0)
                    }

                    DigitalLandSearchPresenter().searchPlanningInfoByID(landID,activity)
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

                DistrictWard().getDistrictWardById(GlobalVariables.provinceID[index])

//                if(GlobalVariables.districtName.isNotEmpty()){
//                    txtChooseDistrict.text = GlobalVariables.districtName[0].toString()
//                }
            }

            false
        }
        menu.show()
    }


    private fun showPopUp(view: View, isWard: Boolean, isProvince: Boolean) {
        val menu = PopupMenu(context, view)
        menu.gravity = Gravity.CENTER
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
                ToastUtils.showShort("Vui lòng chọn quận trước")
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



