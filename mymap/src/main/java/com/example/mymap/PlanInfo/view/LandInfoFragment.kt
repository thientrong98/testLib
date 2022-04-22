import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.mymap.R
import com.example.mymap.listener.LandInfoBDSListener
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView
import com.google.gson.GsonBuilder
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK
import java.util.*

class LandInfoFragment : Fragment(), LandInfoBDSListener {
    var gson = GsonBuilder().create()!!
    private var loGioiAdapter: LoGioiAdapter? = null
    private var loGiois = ArrayList<LoGioi>()

    private lateinit var tvTinh: TextView
    private lateinit var tvQuan: TextView
    private lateinit var tvPhuong: TextView
    private lateinit var trSoTo: TableRow
    private lateinit var trSoThua: TableRow
    private lateinit var tvDienTich: TextView
    private lateinit var tvSoTo: TextView
    private lateinit var tvSoThua: TextView
    private lateinit var tvLoGioi: TableLayout
    private lateinit var lvLoGioi: ExpandableHeightListView
    private lateinit var  trQHPK:TableRow
    private lateinit var tvQHPK:TextView
    private lateinit var  indicator:ProgressBar
    private lateinit var btnBackToLandInfo: ImageButton
    private lateinit var btnDownloadPDF:ImageButton


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
        trQHPK =view.findViewById(R.id.trQHPK)
        tvQHPK= view.findViewById(R.id.tvQHPK)
        indicator = view.findViewById(R.id.indicator)
        btnBackToLandInfo = view.findViewById(R.id.btnBackToLandInfo)
        btnDownloadPDF = view.findViewById(R.id.btnDownloadPDF)
        setUpListView()
        return view
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
        btnDownloadPDF.visibility =View.VISIBLE
        fillPlanningInfo(body)
    }

    override fun onClickMap() {
        indicator.visibility = View.VISIBLE
        btnDownloadPDF.visibility =View.GONE
    }

    private fun fillPlanningInfo(planningInfo: PlanningInfo?) {
//        LandInfoFragment.landID = ttc.getMathuadat()
//        LandInfoFragment.landRanh = ttc.getRanh()
        var ttc =
            gson.fromJson(planningInfo!!.thongTinChung, ThongTinChung::class.java)
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
                            btnDownloadPDF.visibility = View.GONE
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

//        QHPKs = ArrayList<QHPK>()
//        QHNs = ArrayList<QHNganh>()
//        QHPKAdapter = QHPKAdapter(this.activity, QHPKs, QHNs)
//        lvQHPK.setAdapter(QHPKAdapter)
//        lvQHPK.setExpanded(true)
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
//        includeLayoutLandInfo.setVisibility(View.GONE)
//        includeLayoutQHNInfo.setVisibility(View.GONE)
//        includeLayoutOCNInfo.setVisibility(View.GONE)
//        includeLayoutQHPKInfo.setVisibility(View.VISIBLE)
//        btnBackToLandInfo.setVisibility(View.VISIBLE)
        btnDownloadPDF.visibility = View.VISIBLE
//        resetOQHPK()
//        checkInfo = 1
//        listener.onClickOChucNang(ttc.getDsttdoan().get(i).getQhpkranh_geom())
//        fillOQHPKInfo(ttc, i)
    }

}