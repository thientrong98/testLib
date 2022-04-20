
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymap.R
import com.example.mymap.listener.LandInfoBDSListener

class LandInfoFragment : Fragment(), LandInfoBDSListener {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_land_info, container, false)
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
        Log.d("haha","123")
        fillPlanningInfo(body)
    }

    private fun fillPlanningInfo(body: PlanningInfo?) {
        Log.d("123",body!!.thongTinChung.length.toString())

    }
}