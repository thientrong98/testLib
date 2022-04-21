import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mymap.R
import com.example.mymap.listener.LandInfoBDSListener
import com.google.gson.GsonBuilder
import java.util.*

class LandInfoFragment : Fragment(), LandInfoBDSListener {
    var gson = GsonBuilder().create()!!
    private val loGioiAdapter: LoGioiAdapter? = null

    private lateinit var tvTinh: TextView
    private lateinit var tvQuan: TextView
    private lateinit var tvPhuong: TextView
    private lateinit var trSoTo: TableRow
    private lateinit var trSoThua: TableRow
    private lateinit var tvDienTich: TextView
    private lateinit var tvSoTo: TextView
    private lateinit var tvSoThua: TextView
    private lateinit var tvLoGioi: TableLayout

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

        return view
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            LandInfoFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    interface OnDraggerView {
        fun onDraggerViewClick()
//        fun onClickOChucNang(ranh: String?)
//        fun onClickBackLandInfo(ranh: String?)
//        fun onClickQHN(ranh: String?, color: String?)
    }

    override fun onLoadLandInfoSuccess(body: PlanningInfo?) {
        Log.d("haha", "123")
        fillPlanningInfo(body)
    }

    private fun fillPlanningInfo(planningInfo: PlanningInfo?) {
//        LandInfoFragment.landID = ttc.getMathuadat()
//        LandInfoFragment.landRanh = ttc.getRanh()
        var ttc =
            gson.fromJson(planningInfo!!.thongTinChung, ThongTinChung::class.java)
        fillLandInfo(
            gson.fromJson(planningInfo!!.thongTinChung, ThongTinChung::class.java)
//            Arrays.asList(
//                gson.fromJson(
//                    planningInfo.qHPK,
//                    List()<QHPK>::class.java
//                )
//            )
        )

        //        if(!planningInfo.getQHNganh().equals("[]")){
//            fillQHN(Arrays.asList(gson.fromJson(planningInfo.getQHNganh(), QHNganh[].class)));
//        }
        fillLoGioi(
            listOf(
                gson.fromJson(
                    planningInfo.loGioi,
                    Array<LoGioi>::class.java
                )
            )
        )


    }

    private fun fillLandInfo(ttc: ThongTinChung) {
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


    }

  private  fun fillLoGioi(loGiois: List<Array<LoGioi>>) {
//      loGioiAdapter.clear()
      if (loGiois.isNotEmpty()) {
          tvLoGioi.visibility = (View.VISIBLE)
//          loGioiAdapter.addAll(loGiois)
//          loGioiAdapter.notifyDataSetChanged()
      } else {
          tvLoGioi.visibility = (View.GONE)
      }
    }

}