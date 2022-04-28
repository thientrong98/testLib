import MapPresenter
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import butterknife.BindView
import butterknife.ButterKnife
import com.example.mymap.R
import tech.vlab.ttqhhcm.new_ui.land_info.model.QHNganh
import tech.vlab.ttqhhcm.new_ui.map.models.QHPK
import kotlin.math.roundToInt

class QHPKAdapter(context: Context?, quyHoachList: List<QHPK>, QHNs: List<QHNganh>) :
    ArrayAdapter<Any?>(context!!, 0, quyHoachList) {
    private val QHPKs: List<QHPK>
    private val QHNs: List<QHNganh>

    class ViewHolder(view: View?) {

        var tvSTT = view!!.findViewById(R.id.tvSTT) as TextView
        var tvOPho = view!!.findViewById(R.id.tvOPho) as TextView
        var tvChucNangSD = view!!.findViewById(R.id.tvChucNangSD) as TextView
        var tvChucNangSD1 = view!!.findViewById(R.id.tvChucNangSD1) as TextView
        var tvOPho1 = view!!.findViewById(R.id.tvOPho1) as TextView
        var tvDienTich1 = view!!.findViewById(R.id.tvDienTich1) as TextView
        var tvDienTich = view!!.findViewById(R.id.tvDienTich) as TextView
        var layoutQHItem = view!!.findViewById(R.id.layoutItemQH) as LinearLayout
        var fl_stt = view!!.findViewById(R.id.fl_stt) as FrameLayout

        fun setTextFor(tv: TextView?, s: String?) {
            if (s != null) {
                tv!!.text = s
            } else {
                tv!!.text = "Đang cập nhật"
            }
        }

        init {
            ButterKnife.bind(this, view!!)
        }
    }

    override fun getCount(): Int {
        return QHPKs.size + QHNs.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @NonNull
    override fun getView(position: Int, convertView: View?, @NonNull parent: ViewGroup): View {
        var convertView = convertView
        return if (position < QHPKs.size) {
            val QHPK: QHPK = QHPKs[position]
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.item_qhpk, parent, false)
            val viewHolder: ViewHolder = ViewHolder(convertView)
            viewHolder.tvSTT!!.text = (position + 1).toString()
            if (QHPK.getProperties() != null && QHPK.getProperties()!!.maopho != null) {
                if (!QHPK.getProperties()!!.maopho.contains("GT")) {
                    viewHolder.tvOPho!!.visibility = View.VISIBLE
                    viewHolder.tvOPho1!!.visibility = View.VISIBLE
                    viewHolder.setTextFor(viewHolder.tvOPho, QHPK.getProperties()!!.maopho)
                } else {
                    viewHolder.tvOPho!!.visibility = View.GONE
                    viewHolder.tvOPho1!!.visibility = View.GONE
                }
            } else {
                viewHolder.tvOPho!!.visibility = View.GONE
                viewHolder.tvOPho1!!.visibility = View.GONE
            }
            viewHolder.setTextFor(viewHolder.tvChucNangSD, QHPK.getProperties()!!.chucnang)
            viewHolder.tvDienTich!!.text = QHPK.getProperties()!!.getDientich().toDouble()
                .toString() + " m²"
            viewHolder.tvChucNangSD1!!.setPadding(
                viewHolder.tvChucNangSD!!.paddingLeft,
                viewHolder.tvChucNangSD!!.paddingTop, viewHolder.tvChucNangSD!!.paddingRight,
                viewHolder.tvChucNangSD!!.paddingBottom
            )
            val RGBColor: String = if (QHPK.getProperties()!!.rgbcolor != null) {
                QHPK.getProperties()!!.rgbcolor
            } else {
                "130,130,130"
            }
            val colors = RGBColor.split(",").toTypedArray()
            val gradientDrawable = GradientDrawable() // set radius cho linearlayout
            gradientDrawable.cornerRadii = floatArrayOf(20f, 20f, 20f, 20f, 20f, 20f, 20f, 20f)
            gradientDrawable.setColor(
                Color.rgb(
                    Integer.valueOf(colors[0]), Integer.valueOf(
                        colors[1]
                    ),
                    Integer.valueOf(colors[2])
                )
            )
            viewHolder.layoutQHItem!!.background = gradientDrawable
            viewHolder.fl_stt!!.setBackgroundColor(getColorWithAlpha(Color.WHITE, 0.5f))
            changeTextColor(viewHolder, Color.WHITE)
            convertView!!.setOnClickListener {
                MapPresenter(null).showInfoOChucNang(
                    QHPK.getProperties()!!.gid
                )
            }
            convertView
        } else {
            //TODO PHAM QUANG SON
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.item_qhpk, parent, false)
            val viewHolder: ViewHolder = ViewHolder(convertView)
            viewHolder.setTextFor(viewHolder.tvSTT, (position + 1).toString())
            viewHolder.setTextFor(
                viewHolder.tvChucNangSD,
                QHNs[position - QHPKs.size].properties!!.chucnang
            )
            viewHolder.setTextFor(
                viewHolder.tvDienTich,
                QHNs[position - QHPKs.size].properties!!.dientich.toDouble().toString()

            )
            viewHolder.tvChucNangSD1!!.setPadding(
                viewHolder.tvChucNangSD!!.paddingLeft,
                viewHolder.tvChucNangSD!!.paddingTop, viewHolder.tvChucNangSD!!.paddingRight,
                viewHolder.tvChucNangSD!!.paddingBottom
            )
            convertView!!
        }
    }

    private fun changeTextColor(viewHolder: ViewHolder, color: Int) {
        viewHolder.tvSTT!!.setTextColor(color)
        viewHolder.tvOPho!!.setTextColor(color)
        viewHolder.tvChucNangSD!!.setTextColor(color)
        viewHolder.tvDienTich!!.setTextColor(color)
        viewHolder.tvOPho1!!.setTextColor(color)
        viewHolder.tvChucNangSD1!!.setTextColor(color)
        viewHolder.tvDienTich1!!.setTextColor(color)
    }

    companion object {
        private fun getColorWithAlpha(
            color: Int,
            ratio: Float
        ): Int { //TODO TRONG: đè màu trắng lên màu nền với % để làm mờ, % càng nhỏ càng mờ
            var newColor = 0
            val alpha = (Color.alpha(color) * ratio).roundToInt()
            val r = Color.red(color)
            val g = Color.green(color)
            val b = Color.blue(color)
            newColor = Color.argb(alpha, r, g, b)
            return newColor
        }
    }

    init {
        QHPKs = quyHoachList
        this.QHNs = QHNs
    }
}