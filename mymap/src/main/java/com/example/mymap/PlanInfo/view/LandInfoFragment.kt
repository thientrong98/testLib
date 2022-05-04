import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.mymap.Helper.MapAddLayerHelper
import com.example.mymap.R
import com.example.mymap.listener.LandInfoBDSListener
import com.example.mymap.utils.GlobalVariables
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_info.*
import org.json.JSONException
import org.json.JSONObject
import tech.vlab.ttqhhcm.new_ui.land_info.model.QHNganh
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK
import java.util.*

class LandInfoFragment : Fragment(), LandInfoBDSListener, OChucNangPresenter.LoadOChucNangListener {
    private var gson = GsonBuilder().create()!!
    private var landRanh = ""

    private var loGioiAdapter: LoGioiAdapter? = null
    private var loGiois = ArrayList<LoGioi>()
    private var QHPKAdapter: QHPKAdapter? = null
    private var QHPKs = ArrayList<QHPK>()
    private var QHNs = ArrayList<QHNganh>()

    private lateinit var tvTinh: TextView
    private lateinit var tvQuan: TextView
    private lateinit var tvPhuong: TextView
    private lateinit var trSoTo: TableRow
    private lateinit var trSoThua: TableRow
    private lateinit var tvDienTich: TextView
    private lateinit var tvSoTo: TextView
    private lateinit var tvSoThua: TextView
    private lateinit var tvLoGioi: TableLayout
    private lateinit var trQHPK: TableRow
    private lateinit var tvQHPK: TextView
    private lateinit var indicator: ProgressBar
    private lateinit var btnBackToLandInfo: ImageButton
    private lateinit var btnDownloadPDF: ImageButton
    private lateinit var iv_legend: ImageView
    private lateinit var iv_legend_cn: ImageView
    private lateinit var iv_legend_chitieu:ImageView
    private lateinit var includeLayoutLandInfo: FrameLayout
    private lateinit var includeLayoutQHPKInfo: FrameLayout
    private lateinit var includeLayoutOCNInfo: FrameLayout
    private lateinit var tv_ten_do_an: TextView
    private lateinit var tv_quan_huyen: TextView
    private lateinit var tv_so_quyet_dinh: TextView
    private lateinit var tv_cqpd: TextView
    private lateinit var tv_ngay_duyet: TextView
    private lateinit var lvQHPK: ExpandableHeightListView
    private lateinit var lvLoGioi: ExpandableHeightListView
    private lateinit var txtTouchToSeeDetail: TextView

    //o chuc nang
    private lateinit var tvMaOChucNang: TextView
    private lateinit var tvChucNang: TextView
    private lateinit var tvDanSo: TextView
    private lateinit var tvHeSoSdd: TextView
    private lateinit var tvDienTichOCN: TextView
    private lateinit var tvMatDo: TextView
    private lateinit var tvChieuCao: TextView
    private lateinit var tvTangCao: TextView
    private lateinit var trTangCao: TableRow
    private lateinit var trChieuCao: TableRow
    private lateinit var scrollViewOChucNang: NestedScrollView
    private lateinit var trDanSo: TextView
    private lateinit var trDienTichOCN: TextView
    private lateinit var trHeSoSdd: TextView
    private lateinit var trMatDo: TextView

    //chi tieu quy hoach
    private lateinit var tlChitieu:LinearLayout
    private lateinit var txtTypeChitieu:TextView
    private lateinit var llChitieu:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DemoFragment().setActivityListener(this)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_land_info, container, false)

        tvTinh = view.findViewById(R.id.tvTinh)
        tvQuan = view.findViewById(R.id.tvQuan)
        tvPhuong = view.findViewById(R.id.tvPhuong)
        trSoTo = view.findViewById(R.id.trSoTo)
        tvSoTo = view.findViewById(R.id.tvSoTo)
        trSoThua = view.findViewById(R.id.trSoThua)
        tvSoThua = view.findViewById(R.id.tvSoThua)
        tvDienTich = view.findViewById(R.id.tvDienTich)
        tvLoGioi = view.findViewById(R.id.tv_lo_gioi)
        lvLoGioi = view.findViewById(R.id.lv_lo_gioi)
        trQHPK = view.findViewById(R.id.trQHPK)
        tvQHPK = view.findViewById(R.id.tvQHPK)
        indicator = view.findViewById(R.id.indicator)
        btnBackToLandInfo = view.findViewById(R.id.btnBackToLandInfo)
        btnDownloadPDF = view.findViewById(R.id.btnDownloadPDF)
        iv_legend = view.findViewById(R.id.iv_legend)
        iv_legend_cn = view.findViewById(R.id.iv_legend_cn)
        iv_legend_chitieu = view.findViewById(R.id.iv_legend_chitieu)
        includeLayoutLandInfo = view.findViewById(R.id.include_layout_land_info)
        includeLayoutQHPKInfo = view.findViewById(R.id.include_layout_OQHPK_info)
        includeLayoutOCNInfo = view.findViewById(R.id.include_layout_OCN_info)
        tv_ten_do_an = view.findViewById(R.id.tv_ten_do_an)
        tv_quan_huyen = view.findViewById(R.id.tv_quan_huyen)
        tv_cqpd = view.findViewById(R.id.tv_cqpd)
        tv_so_quyet_dinh = view.findViewById(R.id.tv_so_quyet_dinh)
        tv_ngay_duyet = view.findViewById(R.id.tv_ngay_duyet)
        lvQHPK = view.findViewById(R.id.lv_QHPK)
        txtTouchToSeeDetail = view.findViewById(R.id.txt_touch_to_see_detail)

        tvMaOChucNang = view.findViewById(R.id.tv_ma_o)
        tvChucNang = view.findViewById(R.id.tv_chuc_nang)
        tvDanSo = view.findViewById(R.id.tv_dan_so)
        tvDienTichOCN = view.findViewById(R.id.tv_dien_tichOCN)
        tvHeSoSdd = view.findViewById(R.id.tv_he_so_sdd)
        tvMatDo = view.findViewById(R.id.tv_mat_do)
        tvTangCao = view.findViewById(R.id.tv_tang_cao)
        tvChieuCao = view.findViewById(R.id.tv_chieu_cao)
        scrollViewOChucNang = view.findViewById(R.id.scroll_viewOCN)
        trDanSo = view.findViewById(R.id.tr_dan_so)
        trDienTichOCN = view.findViewById(R.id.tr_dien_tichOCN)
        trHeSoSdd = view.findViewById(R.id.tr_he_so_sdd)
        trMatDo = view.findViewById(R.id.tr_mat_do)
        trTangCao = view.findViewById(R.id.tr_tang_cao)
        trChieuCao = view.findViewById(R.id.tr_chieu_cao)

        tlChitieu= view.findViewById(R.id.tl_chitieu)
        txtTypeChitieu= view.findViewById(R.id.txt_type_chitieu)
        llChitieu= view.findViewById(R.id.ll_chitieu)


        iv_legend.setOnClickListener {
            showCustomeDialog(
                this.layoutInflater.inflate(R.layout.fragment_legend_note, null),
                0.7f
            )
        }

        iv_legend_cn.setOnClickListener {
            showCustomeDialog(
                this.layoutInflater.inflate(R.layout.fragment_legend_note, null),
                0.7f
            )
        }

        iv_legend_chitieu.setOnClickListener {
            showCustomeDialog(
                this.layoutInflater.inflate(R.layout.fragment_legent_chitieu, null),
                0.7f
            )
        }

        btnBackToLandInfo.setOnClickListener {
            includeLayoutLandInfo.visibility = View.VISIBLE
            includeLayoutQHPKInfo.visibility = View.GONE
            includeLayoutOCNInfo.visibility = View.GONE
//            includeLayoutQHNInfo.setVisibility(View.GONE)
            btnBackToLandInfo.visibility = View.GONE
            btnDownloadPDF.visibility = View.VISIBLE
//            checkInfo = 0
            onClickBackLandInfo(landRanh)
        }

        setUpListView()
        return view
    }

    private fun onClickBackLandInfo(ranh: String?) {
        MapAddLayerHelper().zoomToRaster(ranh, GlobalVariables.mMap)
        MapRemoveLayerHelper().removeOChucNangLayer()
    }


    interface OnDraggerView {
        fun onDraggerViewClick()
//        fun onClickOChucNang(ranh: String?)
//        fun onClickBackLandInfo(ranh: String?)
//        fun onClickQHN(ranh: String?, color: String?)
    }

    override fun onLoadLandInfoSuccess(body: PlanningInfo?) {
        indicator.visibility = View.GONE
        btnBackToLandInfo.visibility = View.GONE
        btnDownloadPDF.visibility = View.VISIBLE
        fillPlanningInfo(body)
    }

    override fun onClickMap() {
        indicator.visibility = View.VISIBLE
        btnDownloadPDF.visibility = View.GONE
        btnBackToLandInfo.visibility = View.GONE
        includeLayoutLandInfo.visibility = View.VISIBLE
        includeLayoutQHPKInfo.visibility = View.GONE
        includeLayoutOCNInfo.visibility = View.GONE
        resetLandInfo()
    }

    override fun onLoadOChucNangInfoSucces(maQHPK: String?) {
        maQHPK?.let { showOChucNangInfo(it) }
    }

    private fun fillPlanningInfo(planningInfo: PlanningInfo?) {
//        LandInfoFragment.landID = ttc.getMathuadat()
        var ttc =
            gson.fromJson(planningInfo!!.thongTinChung, ThongTinChung::class.java)
        landRanh = ttc.ranh
        if (planningInfo.qHPK != "[]") {
            fillQHPK(gson.fromJson(planningInfo.qHPK, Array<QHPK>::class.java).toList())
        }
        fillLandInfo(
            gson.fromJson(planningInfo.thongTinChung, ThongTinChung::class.java),
            gson.fromJson(
                planningInfo.qHPK,
                Array<QHPK>::class.java
            ).toList()
        )

        //        if(!planningInfo.getQHNganh().equals("[]")){
//            fillQHN(Arrays.asList(gson.fromJson(planningInfo.getQHNganh(), QHNganh[].class)));
//        }

        fillLoGioi(
            gson.fromJson(
                planningInfo.loGioi,
                Array<LoGioi>::class.java

            ).toList()
        )
    }

    private fun fillLandInfo(ttc: ThongTinChung, toList: List<QHPK>) {
        tvTinh.text = getText(R.string.txt_hcm)
        tvQuan.text = ttc.tenquanhuyen
        tvPhuong.text = ttc.tenphuongxa

        if (ttc.soto != "") {
            trSoTo.visibility = View.VISIBLE
            tvSoTo.visibility = (View.VISIBLE)
            tvSoTo.text = (ttc.soto)
        } else {
            tvSoTo.visibility = (View.GONE)
            trSoTo.visibility = View.GONE
        }

        if (ttc.sothua != "") {
            trSoThua.visibility = (View.VISIBLE)
            tvSoThua.visibility = (View.VISIBLE)
            tvSoThua.text = (ttc.sothua)
        } else {
            tvSoThua.visibility = (View.GONE)
            trSoThua.visibility = (View.GONE)
        }
        tvDienTich.text =
            ttc.dientich.toDouble().toString() + " m²"

        if (ttc.dsdoan != null) {
            if (ttc.dsdoan.size === 0) {
                trQHPK.visibility = View.GONE
            } else if (ttc.dsdoan.size === 1) {
                trQHPK.visibility = View.VISIBLE
                tvQHPK.text = ttc.dsdoan[0] + getText(R.string.txt_chitiet)
                tvQHPK.setTextColor(Color.parseColor("#186BCC"))
                tvQHPK.setOnClickListener(View.OnClickListener {
                    showOQHPKInfo(
                        ttc,
                        0
                    )
                })
            } else {
                trQHPK.visibility = View.VISIBLE
                var QHPKhu = StringBuilder()
                for (i in 0 until ttc.dsdoan.size) {
                    QHPKhu.append("> ").append(ttc.dsdoan[i]).append("\n")
                }
                if (QHPKhu.toString().endsWith("\n")) {
                    QHPKhu = StringBuilder(QHPKhu.substring(0, QHPKhu.lastIndexOf("\n")))
                }
                val ss = SpannableString(QHPKhu.toString())
                for (i in 0 until ttc.dsttdoan.size) {
                    val clickableSpan: ClickableSpan = object : ClickableSpan() {
                        override fun onClick(view: View) {
                            showOQHPKInfo(ttc, i)
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            ds.color = Color.parseColor("#186BCC")
                            ds.isUnderlineText = false
                        }
                    }
                    val firstIndex = QHPKhu.indexOf(ttc.dsttdoan[i].tendoan)
                    val lastIndex: Int = firstIndex + ttc.dsttdoan[i].tendoan.length
                    ss.setSpan(
                        clickableSpan,
                        firstIndex,
                        lastIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                tvQHPK.isClickable = true
                tvQHPK.movementMethod = LinkMovementMethod.getInstance()
                tvQHPK.setText(ss, TextView.BufferType.SPANNABLE)
                if (ss.toString().isEmpty()) {
                    tvQHPK.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpListView() {
        indicator.visibility = View.VISIBLE
        btnBackToLandInfo.visibility = View.GONE
        includeLayoutLandInfo.visibility = View.VISIBLE
        includeLayoutQHPKInfo.visibility = View.GONE
        includeLayoutOCNInfo.visibility = View.GONE
        QHPKs = ArrayList<QHPK>()
        QHNs = ArrayList<QHNganh>()
        QHPKAdapter = QHPKAdapter(this.activity, QHPKs, QHNs)
        lvQHPK.adapter = QHPKAdapter
        lvQHPK.isExpanded = true
        loGiois = ArrayList<LoGioi>()
        loGioiAdapter = LoGioiAdapter(this.requireActivity(), loGiois)
        lvLoGioi.adapter = loGioiAdapter
        lvLoGioi.isExpanded = true
    }

    private fun fillLoGioi(loGiois: List<LoGioi>) {
        loGioiAdapter?.clear()
        if (loGiois.isNotEmpty()) {
            tvLoGioi.visibility = (View.VISIBLE)
            loGioiAdapter?.addAll(loGiois)
            loGioiAdapter?.notifyDataSetChanged()
        } else {
            tvLoGioi.visibility = (View.GONE)
        }
    }

    private fun showOQHPKInfo(ttc: ThongTinChung, i: Int) {
        includeLayoutLandInfo.visibility = View.GONE
//        includeLayoutQHNInfo.setVisibility(View.GONE)
        includeLayoutOCNInfo.visibility = View.GONE
        includeLayoutQHPKInfo.visibility = View.VISIBLE
        btnBackToLandInfo.visibility = View.VISIBLE
        btnDownloadPDF.visibility = View.VISIBLE
        resetOQHPK()
//        checkInfo = 1
        onClickOChucNang(ttc.dsttdoan[i].qhpkranh_geom)
        fillOQHPKInfo(ttc, i)
    }

    private fun resetLandInfo() {
        tvTinh.text = "-"
        tvQuan.text = "-"
        tvPhuong.text = "-"
        tvSoThua.text = "-"
        tvSoTo.text = "-"
        tvDienTich.text = "-"
        tvQHPK.text = "-"
//        tvQHN.setText("-")
//        QHNs.clear()
        QHPKs.clear()
        loGiois.clear()
        QHPKAdapter!!.notifyDataSetChanged()
        loGioiAdapter!!.notifyDataSetChanged()
    }

    private fun resetOQHPK() {
        tv_ten_do_an.text = "-"
        tv_quan_huyen.text = "-"
        tv_so_quyet_dinh.text = "-"
        tv_cqpd.text = "-"
        tv_ngay_duyet.text = "-"
    }

    private fun showCustomeDialog(v: View, f: Float) { //TODO: TRONG
        val displayRectangle = Rect()
        val builder: AlertDialog.Builder =
            AlertDialog.Builder(context, R.style.CustomAlertDialog)
        builder.setView(v)
        val dialog: AlertDialog = builder.create()
        dialog.show()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val displayWidth = displayMetrics.widthPixels
        val displayHeight = LinearLayout.LayoutParams.WRAP_CONTENT
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        val dialogWindowWidth = (displayWidth * f).toInt()
        layoutParams.width = dialogWindowWidth
        layoutParams.height = displayHeight
        dialog.window?.attributes = layoutParams
    }

    private fun onClickOChucNang(ranh: String?) {
        MapAddLayerHelper().addOChucNangLayer(
            GlobalVariables.mMap,
            ranh
        )
        try {
            MapAddLayerHelper().zoomToRaster(
                JSONObject(ranh).getString("coordinates"),
                GlobalVariables.mMap
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun fillOQHPKInfo(ttc: ThongTinChung, i: Int) {
        tv_ten_do_an.text = ttc.dsttdoan[i].tendoan
        tv_quan_huyen.text = java.lang.String.valueOf(ttc.tenquanhuyen)
        tv_so_quyet_dinh.text = ttc.dsttdoan[i].soqd
        tv_cqpd.text = ttc.dsttdoan[i].coquanpd
        tv_ngay_duyet.text = ttc.dsttdoan[i].ngayduyet
    }


    private fun fillQHPK(QHPKs: List<QHPK>) {
        if (QHPKs.isNotEmpty()) {
            tvThongTinThuaDat.visibility = View.VISIBLE
            txtTouchToSeeDetail.visibility = View.VISIBLE
            this.QHPKs.addAll(QHPKs)
            QHPKAdapter!!.notifyDataSetInvalidated()
        } else {
            tvThongTinThuaDat.visibility = View.GONE
            txtTouchToSeeDetail.visibility = View.GONE
        }
    }

    private fun showOChucNangInfo(maQHPKSDD: String) {
        includeLayoutLandInfo.visibility = View.GONE
//        includeLayoutQHNInfo.setVisibility(View.GONE)
        includeLayoutQHPKInfo.visibility = View.GONE
        includeLayoutOCNInfo.visibility = View.VISIBLE
        btnBackToLandInfo.visibility = View.VISIBLE
        btnDownloadPDF.visibility = View.GONE
        resetOCNInfo()
//        checkInfo = 0
        OChucNangPresenter(this).getOChucNang930(maQHPKSDD)
        OChucNangPresenter(this).getChiTieuHonHop(maQHPKSDD)
    }

    private fun resetOCNInfo() {
        tvMaOChucNang.text = "-"
        tvChucNang.text = "-"
        tvDanSo.text = "-"
        tvDienTichOCN.text = "-"
        tvHeSoSdd.text = "-"
        tvMatDo.text = "-"
        tvTangCao.text = "-"
        tvChieuCao.text = "-"
    }
    private fun setVisbilityoChucNang() {
        tvDanSo.visibility = View.VISIBLE
        trDanSo.visibility = View.VISIBLE
        tvDienTichOCN.visibility = View.VISIBLE
        trDienTichOCN.visibility = View.VISIBLE
        tvHeSoSdd.visibility = View.VISIBLE
        trHeSoSdd.visibility = View.VISIBLE
        tvMatDo.visibility = View.VISIBLE
        trMatDo.visibility = View.VISIBLE
        tvTangCao.visibility = View.VISIBLE
        trTangCao.visibility = View.VISIBLE
        tvChieuCao.visibility = View.VISIBLE
        trChieuCao.visibility = View.VISIBLE
    }


    @SuppressLint("SetTextI18n")
    private fun setUpTextView(textView: TextView, content: String?) {
        if (content != null) {
            textView.text = content
        } else {
            textView.text = "Đang cập nhật"
        }
    }

    @SuppressLint("SetTextI18n")
    fun fillOChucNangInfo(oChucNang: OChucNang) {
        setVisbilityoChucNang()
        setUpTextView(tvMaOChucNang, oChucNang.maOPho)
        setUpTextView(tvChucNang, oChucNang.chucnangsdd)
        if (java.lang.String.valueOf(oChucNang.danso) != "0") {
            setUpTextView(tvDanSo, java.lang.String.valueOf(oChucNang.danso))
        } else {
            tvDanSo.visibility = View.GONE
            trDanSo.visibility = View.GONE
        }
        setUpTextView(tvHeSoSdd, java.lang.String.valueOf(oChucNang.hesosdd))
        if (oChucNang.hesosdd != null) {
            setUpTextView(tvHeSoSdd, oChucNang.hesosdd)
        } else {
            tvHeSoSdd.visibility = View.GONE
            trHeSoSdd.visibility = View.GONE
        }
        if (oChucNang.dientich != null) {
            tvDienTichOCN.text = oChucNang.dientich + " m²"
        } else {
            tvDienTichOCN.visibility = View.GONE
            trDienTichOCN.visibility = View.GONE
        }
        if (oChucNang.matdo != null) {
            setUpTextView(tvMatDo, oChucNang.matdo + "%")
        } else {
            tvMatDo.visibility = View.GONE
            trMatDo.visibility = View.GONE
        }

        if (oChucNang.chieucao != null) {
            setUpTextView(tvChieuCao, oChucNang.chieucao + " m")
        } else {
            trChieuCao.visibility = View.GONE
        }
        if (oChucNang.tangcao != null) {
            setUpTextView(
                tvTangCao,
                oChucNang.tangcao + getString(R.string.txt_tang)
            )
        } else {
            trTangCao.visibility = View.GONE
        }
        Handler().postDelayed({
            scrollViewOChucNang.fullScroll(ScrollView.FOCUS_UP)

        }, 200)
    }

    private  fun addEachCellTableChiTieu(
        value: String?,
        linearLayout: LinearLayout,
        i: Int,
        weight: Int
    ) { // TODO TRONG
        val frameLayout = FrameLayout(requireContext())
        val paramsFrame = LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.MATCH_PARENT,
            weight.toFloat()
        )
        paramsFrame.setMargins(0, 0, 0, 0)
        frameLayout.layoutParams = paramsFrame
        if (i % 2 == 0) {
            frameLayout.background =  resources.getDrawable(R.drawable.boder_cell)
        } else {
            frameLayout.background = resources.getDrawable(R.drawable.boder_cell_color)
        }
        val paramsTextView = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,
            weight.toFloat()
        )
        val t1v = TextView(context)
        t1v.text = value
        t1v.layoutParams = paramsTextView
        t1v.gravity = Gravity.CENTER_HORIZONTAL
        val color = Color.parseColor("#303030")
        t1v.setTextColor(color)
        frameLayout.addView(t1v)
        linearLayout.addView(frameLayout)
    }

   private fun addEachRowTableChiTieu(chiTieuHonHops: List<ChiTieuHonHop?>, i: Int) { // TODO TRONG
        val linearLayout = LinearLayout(context)
        val paramsFrame = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 6.0f)
        linearLayout.layoutParams = paramsFrame
        addEachCellTableChiTieu(chiTieuHonHops[i]?.chucnangct, linearLayout, i, 2)
        addEachCellTableChiTieu(
            chiTieuHonHops[i]?.dientich, linearLayout, i, 1
        )
        addEachCellTableChiTieu(
            if (chiTieuHonHops[i]?.danso.equals("0")) "" else chiTieuHonHops[i]?.danso,
            linearLayout,
            i,
            1
        )
        addEachCellTableChiTieu(chiTieuHonHops[i]?.tangcao, linearLayout, i, 1)
        addEachCellTableChiTieu(chiTieuHonHops[i]?.matdo, linearLayout, i, 1)
        addEachCellTableChiTieu(chiTieuHonHops[i]?.hesosdd, linearLayout, i, 1)
        tlChitieu.addView(linearLayout)
    }

   private fun fillTableChiTieuHonHon(chiTieuHonHops: List<ChiTieuHonHop?>) {
        llChitieu.visibility = View.GONE
       tlChitieu.removeAllViews()
        if (chiTieuHonHops.size == 1) {
            llChitieu.visibility = View.VISIBLE
            txtTypeChitieu.setText(R.string.ctqhkt)
            addEachRowTableChiTieu(chiTieuHonHops, 0)
        } else if (chiTieuHonHops.size > 1) {
            llChitieu.visibility = View.VISIBLE
            txtTypeChitieu.setText(R.string.cthh)
            for (i in chiTieuHonHops.indices) {
                addEachRowTableChiTieu(chiTieuHonHops, i)
            }
        }
    }

    override fun onLoadOChucNangSucess(oChucNang: OChucNang?) {
        fillOChucNangInfo(oChucNang!!)
        onClickOChucNang(oChucNang.ranh)
    }

    override fun onLoadOChucNangFailure(string: String?) {
//        if (!LocationHelper.isNetworkConnected(LandInfoFragment.context)) {
//            Toast.makeText(this.activity, getString(R.string.no_connect_error), Toast.LENGTH_SHORT)
//                .show()
//        } else {
//            Toast.makeText(this.activity, message, Toast.LENGTH_SHORT).show()
//        }
        indicator.visibility = View.GONE
    }

    override fun onLoadChiTieuHonHopSucess(chiTieuHonHops: List<ChiTieuHonHop?>?) {
        fillTableChiTieuHonHon(chiTieuHonHops!!)
    }

    override fun onLoadChiTieuHonHopFailure(string: String?) {
    }

    override fun onPopup() {
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window, null)

        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // lets taps outside the popup also dismiss it

        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 50)
        val ivClose = popupView.findViewById<View>(R.id.im_close_popup) as ImageView
        val tvError = popupView.findViewById<View>(R.id.tv_error_popup) as TextView
        tvError.text = getText(R.string.txt_error_get_o_chuc_nang)
        ivClose.setOnClickListener { popupWindow.dismiss() }

    }
}