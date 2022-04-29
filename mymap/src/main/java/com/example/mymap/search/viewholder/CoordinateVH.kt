import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.example.mymap.R

class CoordinateVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    var edtXCoordinate: EditText = itemView!!.findViewById(R.id.edtXCoordinate)
    var edtYCoordinate: EditText = itemView!!.findViewById(R.id.edtYCoordinate)
    var icDelete: ImageView = itemView!!.findViewById(R.id.ic_delete)
    private var icExpand: ImageView = itemView!!.findViewById(R.id.ic_expand)
    private var groupBtnCoor: LinearLayout = itemView!!.findViewById(R.id.group_btn_coor)
    var icInsertAbove: ImageView = itemView!!.findViewById(R.id.ic_insert_above)
    var icInsertUnder: ImageView = itemView!!.findViewById(R.id.ic_insert_under)
    var txtRowNumber: TextView = itemView!!.findViewById(R.id.txtRowNumber)

    fun deactiveView() {
        // itemView.setEnabled(false);
        icDelete!!.visibility = View.INVISIBLE
        txtRowNumber!!.setTextColor(Color.LTGRAY)
        edtXCoordinate!!.hint = ""
        edtYCoordinate!!.hint = ""
        edtXCoordinate!!.isEnabled = false
        edtYCoordinate!!.isEnabled = false
    }

    fun addEvents(position: Int) {
        edtYCoordinate!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun onBtnExpandClick() {
        if (groupBtnCoor!!.visibility == View.VISIBLE) {
            groupBtnCoor!!.visibility = View.GONE
        } else {
            groupBtnCoor!!.visibility = View.VISIBLE
        }
    }

    init {
        icExpand.setOnClickListener { onBtnExpandClick() }
        ButterKnife.bind(this, itemView!!)
    }


}