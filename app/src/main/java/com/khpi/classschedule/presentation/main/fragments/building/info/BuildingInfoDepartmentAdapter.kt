package com.khpi.classschedule.presentation.main.fragments.building.info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import kotlinx.android.synthetic.main.item_building_department.view.*

class BuildingInfoDepartmentAdapter(private val value: List<String>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_building_department, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(value[position])
    }

    override fun getItemCount() = value.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: String) {
            itemView.unit_department_value.text = item
        }
    }
}