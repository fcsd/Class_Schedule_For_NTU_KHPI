package com.khpi.classschedule.presentation.main.fragments.building.info

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.FullBuildingPair
import kotlinx.android.synthetic.main.item_building_pair.view.*
import kotlinx.android.synthetic.main.item_building_unit.view.*

class BuildingInfoAdapter (private val pairs: ArrayList<FullBuildingPair>,
                           private val unit:  Map<String, ArrayList<String>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_PAIR = 1
    private val TYPE_UNIT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_PAIR -> return BuildingInfoPairViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_building_pair, parent, false))
            TYPE_UNIT -> return BuildingInfoUnitViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_building_unit, parent, false))
        }
        throw IllegalArgumentException("Unsupported view scheduleType $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BuildingInfoPairViewHolder -> holder.onBind(pairs[position])
            is BuildingInfoUnitViewHolder -> {
                val key = unit.keys.toList()[position - pairs.size]
                val value = unit[key] ?: return
                holder.onBind(key, value)
            }
        }
    }

    override fun getItemCount() = pairs.size + unit.keys.size

    override fun getItemViewType(position: Int): Int = when {
        position < pairs.size -> TYPE_PAIR
        else -> TYPE_UNIT
    }

    class BuildingInfoPairViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: FullBuildingPair) {
            itemView.pair_title.text = item.title
            itemView.pair_content.text = item.value
        }
    }

    class BuildingInfoUnitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(title: String, value: List<String>) {
            itemView.unit_title.text = title

            val infoDepartmentAdapter = BuildingInfoDepartmentAdapter(value)
            itemView.recycler_unit.layoutManager = LinearLayoutManager(itemView.context)
            itemView.recycler_unit.adapter = infoDepartmentAdapter
        }
    }
}