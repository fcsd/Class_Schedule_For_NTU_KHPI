package com.khpi.classschedule.presentation.main.fragments.schedule.general.item

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.utils.setVisibility
import kotlinx.android.synthetic.main.item_general.view.*
import kotlinx.android.synthetic.main.item_general_add.view.*

class GeneralItemAdapter(private val scheduleInfo: List<BaseModel>,
                         private val presenter: GeneralItemPresenter)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_CONTENT = 1
    private val TYPE_FOOTER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_CONTENT -> return GeneralContentViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_general, parent, false))
            TYPE_FOOTER -> return GeneralFooterViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_general_add, parent, false))
        }
        throw IllegalArgumentException("Unsupported view scheduleType $viewType")
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GeneralContentViewHolder -> holder.onBind(scheduleInfo[position], presenter)
            is GeneralFooterViewHolder -> holder.onBind(presenter)
        }
    }

    override fun getItemCount() = scheduleInfo.size + 1

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = when (position) {
        itemCount - 1 -> TYPE_FOOTER
        else -> TYPE_CONTENT
    }

    class GeneralContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: BaseModel, presenter: GeneralItemPresenter) {
            itemView.general_name_text.text = item.title
            itemView.general_parent_text.text = item.parentName
            itemView.general_additional_text.text = itemView.context.resources.getString(R.string.course, item.course)

            if (item.isPinned) {
                itemView.general_pin_image.setVisibility(true)
            } else {
                itemView.general_pin_image.setVisibility(false)
            }

            itemView.setOnClickListener { presenter.onItemClick(item) }
        }
    }

    class GeneralFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(presenter: GeneralItemPresenter) {
            itemView.general_add_image.setOnClickListener { presenter.onAddClicked() }
        }
    }

}