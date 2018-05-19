package com.khpi.classschedule.presentation.main.fragments.category.item

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.utils.setVisibility
import com.khpi.classschedule.views.BasePropertyAdapter
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_category_add.view.*

class CategoryItemAdapter(private val scheduleInfo: List<BaseModel>,
                          private val listenerToParent: OnGeneralItemClickListener,
                          private val listener: BasePropertyAdapter.OnScheduleItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_CONTENT = 1
    private val TYPE_FOOTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_CONTENT -> return GeneralContentViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category, parent, false))
            TYPE_FOOTER -> return GeneralFooterViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category_add, parent, false))
        }
        throw IllegalArgumentException("Unsupported view scheduleType $viewType")
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GeneralContentViewHolder -> holder.onBind(scheduleInfo[position], listener, listenerToParent)
            is GeneralFooterViewHolder -> holder.onBind(listenerToParent)
        }
    }

    override fun getItemCount() = scheduleInfo.size + 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = when (position) {
        itemCount - 1 -> TYPE_FOOTER
        else -> TYPE_CONTENT
    }

    class GeneralContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BasePropertyAdapter.OnCloseClickListener {

        fun onBind(item: BaseModel,
                   listener: BasePropertyAdapter.OnScheduleItemClickListener,
                   listenerToParent: OnGeneralItemClickListener) {

            val propertyAdapter = BasePropertyAdapter(item.properties, listener, adapterPosition, this)
            itemView.recycler_general_item.layoutManager = LinearLayoutManager(itemView.context,
                    LinearLayoutManager.HORIZONTAL, false)
            itemView.recycler_general_item.adapter = propertyAdapter

            itemView.general_name_text.text = item.title
            itemView.general_parent_text.text = item.parentName
            itemView.general_additional_text.text = itemView.context.resources.getString(R.string.course, item.course)
            itemView.general_pin_image.setVisibility(item.isPinned)
            itemView.setOnClickListener { listenerToParent.onItemClick(item) }

            itemView.general_image_open.setOnClickListener {
                itemView.general_content_layout.alpha = 0.3f
                itemView.recycler_general_item.startAnimation(
                        AnimationUtils.loadAnimation(itemView.context, R.anim.up))
                itemView.recycler_general_item.visibility = View.VISIBLE
            }
        }

        override fun onCloseClick() {
            itemView.general_content_layout.alpha = 1f
            itemView.recycler_general_item.startAnimation(
                    AnimationUtils.loadAnimation(itemView.context, R.anim.down))
            itemView.recycler_general_item.visibility = View.GONE
        }
    }

    class GeneralFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(listenerToParent: OnGeneralItemClickListener) {
            itemView.general_add_image.setOnClickListener { listenerToParent.onAddClick() }
        }
    }

    interface OnGeneralItemClickListener {
        fun onItemClick(item: BaseModel)
        fun onAddClick()
    }

}