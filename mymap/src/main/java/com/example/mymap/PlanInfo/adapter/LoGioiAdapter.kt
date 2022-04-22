import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.mymap.R

class LoGioiAdapter(context: Context, loGiois: ArrayList<LoGioi>) :
    ArrayAdapter<LoGioi?>(context, 0, loGiois as List<LoGioi?>) {
    private val loGiois: ArrayList<LoGioi> = loGiois
    private var loGioi: LoGioi? = null

    class ViewHolder(view: View?) {
        val tvTenDuong = view?.findViewById(R.id.tvTenDuong) as TextView
        val tvLoGioi = view?.findViewById(R.id.tvTenDuong) as TextView
        val ll_logioi = view?.findViewById(R.id.ll_logioi) as LinearLayout
        val view_left = view?.findViewById(R.id.view_left) as View
        val view_right = view?.findViewById(R.id.view_right) as View

        init {
            ButterKnife.bind(this, view!!)
        }
    }

    override fun getCount(): Int {
        return loGiois.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        loGioi = loGiois[position]
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.item_logioi, parent, false)
            viewHolder = ViewHolder(convertView)
            convertView!!.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        if (loGioi!!.properties!!.tenduong != null) {
            viewHolder.tvTenDuong.text = loGioi!!.properties!!.tenduong
        } else {
            viewHolder.tvTenDuong.text = loGioi!!.properties!!.tenhem
        }
        viewHolder.tvLoGioi.text =
            loGioi!!.properties!!.logioi.toDouble().toString() + "m"
        if (position % 2 == 0) {
            viewHolder.ll_logioi.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            viewHolder.ll_logioi.setBackgroundColor(Color.parseColor("#F3F3F3"))
        }
        if (position == count - 1) {
            viewHolder.view_left.visibility = View.GONE
            viewHolder.view_right.visibility = View.GONE
            if (position % 2 == 0) {
                viewHolder.ll_logioi.background =
                    context.resources.getDrawable(R.drawable.new_bg_logioi_under_white)
            } else {
                viewHolder.ll_logioi.background =
                    context.resources.getDrawable(R.drawable.new_bg_logioi_under)
            }
        } else {
            viewHolder.view_left.visibility = View.VISIBLE
            viewHolder.view_right.visibility = View.VISIBLE
        }
        return convertView
    }
}