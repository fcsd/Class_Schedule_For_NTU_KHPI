package com.khpi.classschedule.presentation.main.fragments.category.pin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import kotlinx.android.synthetic.main.item_category_pin.view.*

class CategoryPinAdapter(private val scheduleInfo: List<BaseModel>,
                         private val listener: OnCategoryPinItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CategoryPinViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category_pin, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CategoryPinViewHolder)?.onBind(scheduleInfo[position], listener)
    }

    override fun getItemCount() = scheduleInfo.size

    override fun getItemId(position: Int): Long = position.toLong()

    class CategoryPinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: BaseModel, listener: OnCategoryPinItemClickListener) {
            itemView.general_name_pin_text.text = item.title
            itemView.general_parent_pin_text.text = item.parentName
            itemView.general_pin_radiobutton.isChecked = item.isPinned
            itemView.setOnClickListener { listener.onRadioChanged(item) }
        }
    }

    interface OnCategoryPinItemClickListener {
        fun onRadioChanged(newPinnedInfo: BaseModel)
    }

}