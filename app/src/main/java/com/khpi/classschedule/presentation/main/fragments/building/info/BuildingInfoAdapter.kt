package com.khpi.classschedule.presentation.main.fragments.building.info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.FullBuildingPair
import kotlinx.android.synthetic.main.item_building_pair.view.*
import kotlinx.android.synthetic.main.item_building_unit.view.*
import kotlinx.android.synthetic.main.item_building_unit_title.view.*

class BuildingInfoAdapter (private val pairs: ArrayList<FullBuildingPair>,
                           private val unitTitle: String,
                           private val unit: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_PAIR = 1
    private val TYPE_UNIT_TITLE = 2
    private val TYPE_UNIT = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_PAIR -> return BuildingInfoPairViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_building_pair, parent, false))
            TYPE_UNIT_TITLE -> return BuildingInfoUnitTitleViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_building_unit_title, parent, false))
            TYPE_UNIT -> return BuildingInfoUnitViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_building_unit, parent, false))
        }
        throw IllegalArgumentException("Unsupported view scheduleType " + viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BuildingInfoPairViewHolder -> holder.onBind(pairs[position])
            is BuildingInfoUnitTitleViewHolder -> holder.onBind(unitTitle)
            is BuildingInfoUnitViewHolder -> holder.onBind(unit[position - pairs.size])
        }
    }

    override fun getItemCount() = pairs.size + unit.size

    override fun getItemViewType(position: Int): Int = when {
        position < pairs.size -> TYPE_PAIR
        position == pairs.size -> TYPE_UNIT_TITLE
        else -> TYPE_UNIT
    }

    class BuildingInfoPairViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: FullBuildingPair) {
            itemView.pair_title.text = item.title
            itemView.pair_content.text = item.value
        }
    }

    class BuildingInfoUnitTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(title: String) {
            itemView.unit_title.text = title
        }
    }

    class BuildingInfoUnitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: String) {
            itemView.unit_content.text = item
        }
    }
}