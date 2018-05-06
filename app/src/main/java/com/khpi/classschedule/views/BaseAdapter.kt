package com.khpi.classschedule.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import kotlinx.android.synthetic.main.item_base.view.*

class BaseAdapter(private val baseItems: MutableList<BaseModel>,
                  private val listener: OnBaseItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_base, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(baseItems[position], listener)
    }

    override fun getItemCount() = baseItems.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: BaseModel, listener: OnBaseItemClickListener) {
            itemView.building_text.text = item.title
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }

    interface OnBaseItemClickListener {
        fun onItemClick(item: BaseModel)
    }
}