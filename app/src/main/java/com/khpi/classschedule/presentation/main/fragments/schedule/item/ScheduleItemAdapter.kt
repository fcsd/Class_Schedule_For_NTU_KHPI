package com.khpi.classschedule.presentation.main.fragments.schedule.item

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.Schedule
import kotlinx.android.synthetic.main.item_schedule.view.*

class ScheduleItemAdapter(private val schedule: List<Schedule>,
                          private val listener: OnScheduleItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(schedule[position], listener)
    }

    override fun getItemCount() = schedule.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: Schedule, listener: OnScheduleItemClickListener) {
            itemView.schedule_time_text.text = item.time
            itemView.schedule_name_text.text = item.name
            itemView.schedule_teacher_text.text = item.teacher
            itemView.schedule_type_text.text = item.type
            itemView.schedule_auditory_text.text = item.auditory
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }

    interface OnScheduleItemClickListener {
        fun onItemClick(item: Schedule)
    }
}