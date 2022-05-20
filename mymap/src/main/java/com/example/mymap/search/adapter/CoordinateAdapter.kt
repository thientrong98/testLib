package com.example.mymap.search.adapter

import CoordinateItem
import CoordinateVH
import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mymap.Helper.Extension
import com.example.mymap.R
import com.example.mymap.utils.GlobalVariables

class CoordinateAdapter(
    private var coordinateItems: ArrayList<CoordinateItem>,
    activity: Fragment
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listener: AddRowCoodinateListener = activity as AddRowCoodinateListener

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CoordinateVH {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coordinate, parent, false)
        return CoordinateVH(v)
    }

    fun getCoordinateItems(): ArrayList<CoordinateItem> {
        return coordinateItems
    }

    private fun setCoordinateItems(list: ArrayList<CoordinateItem>) {
        this.coordinateItems = list
    }

    override fun getItemCount(): Int {
        return coordinateItems.size
    }

    interface AddRowCoodinateListener {
        fun onAddedRowCoor(index: Int)
        fun onRemoveRowAt(index: Int)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        when (holder) {
            is CoordinateVH -> {
                holder.bind(coordinateItems[position])

                holder.edtYCoordinate.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        charSequence: CharSequence,
                        i: Int,
                        i1: Int,
                        i2: Int
                    ) {
                    }

                    override fun onTextChanged(
                        charSequence: CharSequence,
                        i: Int,
                        i1: Int,
                        i2: Int
                    ) {
                        if (holder.edtXCoordinate.text.toString().trim()
                                .isNotEmpty() && charSequence.toString().length > 2 && position == coordinateItems.size - 1
                        ) {
                            try {
                                coordinateItems.add(
                                    position + 1,
                                    CoordinateItem(position + 1, "", "")
                                )
                                notifyItemInserted(position + 1)
                                for (i in position + 1 until coordinateItems.size) {
                                    coordinateItems[i].setIndex(i)
                                    notifyItemChanged(i + 1)
                                }
                                setCoordinateItems(coordinateItems)
                                listener.onAddedRowCoor(position+1)

                            } catch (e: IllegalStateException) {
                                e.printStackTrace()
                            }
                        }
                        if (position < coordinateItems.size) {
                            coordinateItems[position].setCoordinateY(charSequence.toString())
                            setCoordinateItems(coordinateItems)
                        }
                    }

                    override fun afterTextChanged(editable: Editable) {
                        if (position < coordinateItems.size) {
                            coordinateItems[position].setCoordinateY(editable.toString())
                            coordinateItems[position].setCoordinateX(
                                holder.edtXCoordinate.text.toString()
                            )
                            setCoordinateItems(coordinateItems)
                        }
                    }
                })

                holder.edtXCoordinate.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        charSequence: CharSequence,
                        i: Int,
                        i1: Int,
                        i2: Int
                    ) {
                    }

                    override fun onTextChanged(
                        charSequence: CharSequence,
                        i: Int,
                        i1: Int,
                        i2: Int
                    ) {
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

                holder.icInsertUnder.setOnClickListener {
                    coordinateItems.add(position + 1, CoordinateItem(position + 1, "", ""))
                    notifyItemInserted(position + 1)

                    for (i in position + 1 until coordinateItems.size) {
                        coordinateItems[i].setIndex(i)
                        notifyItemChanged(i + 1)
                    }
                    setCoordinateItems(coordinateItems)
                    listener.onAddedRowCoor(position)
                }

                holder.icInsertAbove.setOnClickListener {
                    coordinateItems.add(
                        position,
                        CoordinateItem(position, "", "")
                    )
                    notifyItemInserted(position)
                    for (i in position until coordinateItems.size) {
                        coordinateItems[i].setIndex(i)
                        notifyItemChanged(i)
                    }
                    setCoordinateItems(coordinateItems)
                    listener.onAddedRowCoor(position)
                }

                holder.icDelete.setOnClickListener {
                    if (position < coordinateItems.size && coordinateItems.size > 4) {
                        coordinateItems.removeAt(position)
                        notifyItemRemoved(position)
                        for (i in position until coordinateItems.size) {
                            coordinateItems[i].setIndex(i)
                            notifyItemChanged(i)
                        }
                        setCoordinateItems(coordinateItems)
                        listener.onRemoveRowAt(position)
                    } else {
                        Extension().showToast(R.string.txt_toado, GlobalVariables.activity.applicationContext)

                    }
                }

            }
        }
    }
}