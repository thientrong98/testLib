
import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.example.mymap.R

class CoordinateAdapter(
    coordinateItems: ArrayList<CoordinateItem>,
    activity: Fragment
) :
    RecyclerView.Adapter<CoordinateVH?>() {
    private val coordinateItems: ArrayList<CoordinateItem> = coordinateItems
//    private val activity: Fragment = activity
    private val listener: AddRowCoodinateListener = activity as AddRowCoodinateListener

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CoordinateVH {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coordinate, parent, false)
        return CoordinateVH(v)
    }

    override fun onBindViewHolder(holder: CoordinateVH, @SuppressLint("RecyclerView") position: Int) {
        val x: String = coordinateItems[position].getCoordinateX()!!
        val y: String = coordinateItems[position].getCoordinateY()!!
        if (x.isNotEmpty()) {
            holder.edtXCoordinate.setText(x)
        }
        if (y.isNotEmpty()) {
            holder.edtYCoordinate.setText(y)
        }
        holder.txtRowNumber.text = (position + 1).toString()
        holder.icDelete.setOnClickListener {
            if (position < coordinateItems.size && coordinateItems.size > 4) {
                coordinateItems.removeAt(position)
//                notifyItemRemoved(position)
                listener.onRemoveRowAt(position)
            }else{
                ToastUtils.showShort("Vui lòng nhập ít nhất 4 cặp tọa độ để tìm kiếm !!")
            }
        }
        holder.edtYCoordinate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (holder.edtXCoordinate.text.toString().trim().isNotEmpty() && charSequence.toString().length > 2 && position == coordinateItems.size - 1
                ) {
                    Log.d("hahah", position.toString())
                    try {
                        coordinateItems.add(position + 1,CoordinateItem(position + 1, "", ""))
                        notifyItemInserted(position +1)
                        //                        Log.d("CoordinateAdapter", "onTextChanged = " + coordinateItems.size());
                        //holder.edtYCoordinate.removeTextChangedListener(this);
                        listener.onAddedRowCoor()
//                        for (i in position + 1 until coordinateItems.size) {
//                            notifyItemChanged(i + 1)
//                        }
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                }
                if (position < coordinateItems.size) {
                    coordinateItems[position].setCoordinateY(charSequence.toString())
                }
            }

            override fun afterTextChanged(editable: Editable) {
                if (position < coordinateItems.size) {
                    coordinateItems[position].setCoordinateY(editable.toString())
                    coordinateItems[position].setCoordinateX(
                        holder.edtXCoordinate.text.toString()
                    )
                }
            }
        })
        holder.edtXCoordinate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (position < coordinateItems.size) {
                    coordinateItems[position].setCoordinateX(charSequence.toString())
                }
            }

            override fun afterTextChanged(editable: Editable) {
                if (position < coordinateItems.size) {
                    coordinateItems[position].setCoordinateX(editable.toString())
                    coordinateItems[position].setCoordinateY(
                        holder.edtYCoordinate.text.toString()
                    )
                }
            }
        })
        holder.icInsertUnder.setOnClickListener {    //TODO: TRONG: hơi khó hiểu một chút, có thể debug để xem cách chạy nhé
            coordinateItems.add(position + 1, CoordinateItem(position + 1, "", ""))
//            coordinateItems.add(CoordinateItem(position + 1, "", ""))
            notifyItemInserted(position + 1)
            listener.onAddedRowCoor()
            for (i in position + 1 until coordinateItems.size) {
                notifyItemChanged(i + 1)
            }
//            for (i in coordinateItems.size - 1 downTo position + 1) {
//                coordinateItems[i].setCoordinateX(coordinateItems[i - 1].getCoordinateX())
//                coordinateItems[i].setCoordinateY(coordinateItems[i - 1].getCoordinateY())
//            }
            coordinateItems[position ].setCoordinateX("")
            coordinateItems[position ].setCoordinateY("")
        }
        holder.icInsertAbove.setOnClickListener {    //TODO: TRONG: hơi khó hiểu một chút, có thể debug để xem cách chạy nhé
            if (position == 0) {

                coordinateItems.add(CoordinateItem(0, "", ""))
                notifyItemInserted(0)
                listener.onAddedRowCoor()
            } else {
                coordinateItems.add(position-1 ,CoordinateItem(position - 1, "", ""))
//                coordinateItems.add(CoordinateItem(position - 1, "", ""))
                notifyItemInserted(position-1)
                listener.onAddedRowCoor()
                for (i in 0 until coordinateItems.size) {
                    notifyItemChanged(i)
                }
//                for (i in coordinateItems.size - 1 downTo position + 1) {
//                    coordinateItems[i]
//                        .setCoordinateX(coordinateItems[i - 1].getCoordinateX())
//                    coordinateItems[i]
//                        .setCoordinateY(coordinateItems[i - 1].getCoordinateY())
//                }
                coordinateItems[position].setCoordinateX("")
                coordinateItems[position].setCoordinateY("")
            }
        }
    }

//    val itemCount: Int
//        get() = coordinateItems.size

    fun getCoordinateItems(): ArrayList<CoordinateItem> {
        return coordinateItems
    }

    interface AddRowCoodinateListener {
        fun onAddedRowCoor() // listener de scroll down to bottom of list in CoorSearchFragment
        fun onRemoveRowAt(index: Int)
    }

    override fun getItemCount() : Int {
        return coordinateItems.size
    }

//    companion object {
//        private var listener: AddRowCoodinateListener
//    }

    init {
    }


}