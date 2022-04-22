import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
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

class LandInfoFragment : Fragment(), LandInfoBDSListener {
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
    private lateinit var includeLayoutLandInfo: FrameLayout
    private lateinit var includeLayoutQHPKInfo: FrameLayout
    private lateinit var includeLayoutOCNInfo : FrameLayout
    private lateinit var tv_ten_do_an:TextView
    private lateinit var tv_quan_huyen:TextView
    private lateinit var tv_so_quyet_dinh:TextView
    private lateinit var tv_cqpd:TextView
    private lateinit var tv_ngay_duyet:TextView
    private lateinit var lvQHPK: ExpandableHeightListView
    private lateinit var lvLoGioi: ExpandableHeightListView
    private lateinit var txtTouchToSeeDetail: TextView

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
        includeLayoutLandInfo = view.findViewById(R.id.include_layout_land_info)
        includeLayoutQHPKInfo = view.findViewById(R.id.include_layout_OQHPK_info)
        includeLayoutOCNInfo = view.findViewById(R.id.include_layout_OCN_info)
        tv_ten_do_an= view.findViewById(R.id.tv_ten_do_an)
        tv_quan_huyen= view.findViewById(R.id.tv_quan_huyen)
        tv_cqpd= view.findViewById(R.id.tv_cqpd)
        tv_so_quyet_dinh= view.findViewById(R.id.tv_so_quyet_dinh)
        tv_ngay_duyet= view.findViewById(R.id.tv_ngay_duyet)
        lvQHPK =view.findViewById(R.id.lv_QHPK)
        txtTouchToSeeDetail = view.findViewById(R.id.txt_touch_to_see_detail)

        iv_legend.setOnClickListener {
            showCustomeDialog(
                this.layoutInflater.inflate(R.layout.fragment_legend_note, null),
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
        includeLayoutOCNInfo.visibility =View.GONE
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
            gson.fromJson(planningInfo!!.thongTinChung, ThongTinChung::class.java),
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
        includeLayoutOCNInfo.visibility =View.GONE
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
//        QHPKs.clear()
        loGiois.clear()
//        QHPKAdapter.notifyDataSetChanged()
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

  private  fun onClickOChucNang(ranh: String?) {
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
//        OChucNangPresenter(this@LandInfoFragment).getOChucNang930(maQHPKSDD)
//        OChucNangPresenter(this@LandInfoFragment).getChiTieuHonHop(maQHPKSDD)
    }

    private fun resetOCNInfo() {
//        tvMaOChucNang.setText("-")
//        tvChucNang.setText("-")
//        tvDanSo.setText("-")
//        tvDienTichOCN.setText("-")
//        tvHeSoSdd.setText("-")
//        tvMatDo.setText("-")
//        tvTangCao.setText("-")
//        tvChieuCao.setText("-")
    }
}