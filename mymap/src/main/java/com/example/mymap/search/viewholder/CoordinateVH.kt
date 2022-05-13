import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymap.R
import com.google.gson.GsonBuilder

class CoordinateVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val gson = GsonBuilder().create()!!
    var edtXCoordinate: EditText = itemView.findViewById(R.id.edtXCoordinate)
    var edtYCoordinate: EditText = itemView.findViewById(R.id.edtYCoordinate)
    var icDelete: ImageView = itemView.findViewById(R.id.ic_delete)
    var icExpand: ImageView = itemView.findViewById(R.id.ic_expand)
    var groupBtnCoor: LinearLayout = itemView.findViewById(R.id.group_btn_coor)
    var icInsertAbove: ImageView = itemView.findViewById(R.id.ic_insert_above)
    var icInsertUnder: ImageView = itemView.findViewById(R.id.ic_insert_under)
    var txtRowNumber: TextView = itemView.findViewById(R.id.txtRowNumber)

    fun bind(coordinateItems: CoordinateItem) {
//        icDelete!!.visibility = View.INVISIBLE
//        txtRowNumber!!.setTextColor(Color.LTGRAY)
//        edtXCoordinate!!.hint = ""
//        edtYCoordinate!!.hint = ""
//        edtXCoordinate!!.isEnabled = false
//        edtYCoordinate!!.isEnabled = false
        edtXCoordinate.setText(coordinateItems.getCoordinateX().toString())
        edtYCoordinate.setText(coordinateItems.getCoordinateY().toString())
        txtRowNumber.text = (coordinateItems.getIndex() + 1).toString()

        icExpand.setOnClickListener { onBtnExpandClick() }

    }

//    fun addEvents(position: Int) {
//        edtYCoordinate!!.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//            override fun afterTextChanged(editable: Editable) {}
//        })
//    }

    private fun onBtnExpandClick() {
        if (groupBtnCoor.visibility == View.VISIBLE) {
            groupBtnCoor.visibility = View.GONE
        } else {
            groupBtnCoor.visibility = View.VISIBLE
        }
    }

//    init {
//         edtXCoordinate = itemView.findViewById(R.id.edtXCoordinate)
//         edtYCoordinate= itemView.findViewById(R.id.edtYCoordinate)
//         icDelete = itemView.findViewById(R.id.ic_delete)
//         icExpand = itemView.findViewById(R.id.ic_expand)
//         groupBtnCoor = itemView.findViewById(R.id.group_btn_coor)
//         icInsertAbove = itemView.findViewById(R.id.ic_insert_above)
//         icInsertUnder = itemView.findViewById(R.id.ic_insert_under)
//         txtRowNumber = itemView.findViewById(R.id.txtRowNumber)
//
//    }


}