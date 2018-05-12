package com.khpi.classschedule.presentation.main.fragments.task.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.utils.DateFormatter
import kotlinx.android.synthetic.main.item_schedule.view.*
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(private val task: List<Task>,
                      private val listener: OnTaskItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(task[position], listener)
    }

    override fun getItemCount() = task.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: Task, listener: OnTaskItemClickListener) {

            itemView.task_date_text.text = itemView.context.resources.getString(R.string.task_deadline,
                    DateFormatter.getDateFromMillis(item.notificationTime))
            itemView.task_group_text.text = item.group
            itemView.task_subject_text.text = item.subject
            itemView.task_description_text.text = item.description
            itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }

    interface OnTaskItemClickListener {
        fun onItemClick(item: Task)
    }
}