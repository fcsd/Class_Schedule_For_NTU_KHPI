package com.khpi.classschedule.presentation.main.fragments.schedule.general.item

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseSchedule
import kotlinx.android.synthetic.main.item_general.view.*

class GeneralItemAdapter(private val scheduleInfo: List<BaseSchedule>,
                         private val presenter: GeneralItemPresenter)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_general, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(scheduleInfo[position], presenter)
    }

    override fun getItemCount() = scheduleInfo.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: BaseSchedule, presenter: GeneralItemPresenter) {
            itemView.general_name_text.text = item.title
            itemView.general_parent_text.text = item.parentName
            itemView.general_additional_text.text = itemView.context.resources.getText(R.string.course, item.course.toString())
            itemView.setOnClickListener { presenter.onItemClick(item) }
        }
    }
}