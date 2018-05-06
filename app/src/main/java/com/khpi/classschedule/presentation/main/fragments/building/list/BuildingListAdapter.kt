package com.khpi.classschedule.presentation.main.fragments.building.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.ShortBuilding
import kotlinx.android.synthetic.main.item_base.view.*

class BuildingListAdapter(private val data: MutableList<ShortBuilding>,
                          private val presenter: BuildingListPresenter)  :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_base, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(data[position], presenter)
    }

    override fun getItemCount() = data.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: ShortBuilding, listPresenter: BuildingListPresenter) {
            itemView.building_text.text = item.fullName
            itemView.setOnClickListener { listPresenter.onBuildingSelected(item.shortName) }

        }
    }
}