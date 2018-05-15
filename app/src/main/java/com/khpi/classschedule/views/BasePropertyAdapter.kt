package com.khpi.classschedule.views

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.Property
import kotlinx.android.synthetic.main.item_property.view.*

class BasePropertyAdapter(private val properties: List<Property>,
                          private val parentListener: OnScheduleItemClickListener,
                          private val parentAdapterPosition: Int,
                          private val childListener: OnCloseClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_CONTENT = 1
    private val TYPE_FOOTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_CONTENT -> return BasePopupContentViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_property, parent, false))
            TYPE_FOOTER -> return BasePopupFooterViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_property_close, parent, false))
        }
        throw IllegalArgumentException("Unsupported view scheduleType $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BasePopupContentViewHolder -> holder.onBind(properties[position], parentAdapterPosition, parentListener)
            is BasePopupFooterViewHolder -> holder.onBind(childListener)
        }
    }

    override fun getItemCount() = properties.size + 1

    override fun getItemViewType(position: Int): Int = when (position) {
        itemCount - 1 -> TYPE_FOOTER
        else -> TYPE_CONTENT
    }

    override fun getItemId(position: Int): Long = position.toLong()

    class BasePopupContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: Property, parentAdapterPosition: Int, listener: OnScheduleItemClickListener) {
            itemView.image_property.setImageDrawable(ContextCompat.getDrawable(itemView.context, item.image))
            itemView.text_property.text = item.title
            itemView.setOnClickListener { listener.onPropertyClick(item, parentAdapterPosition) }
        }
    }

    class BasePopupFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(listener: OnCloseClickListener) {
            itemView.setOnClickListener { listener.onCloseClick() }
        }

    }

    interface OnScheduleItemClickListener {
        fun onPropertyClick(property: Property, adapterPosition: Int)
    }

    interface OnCloseClickListener {
        fun onCloseClick()
    }
}