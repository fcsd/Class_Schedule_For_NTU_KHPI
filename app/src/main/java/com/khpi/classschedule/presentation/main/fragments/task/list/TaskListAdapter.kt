package com.khpi.classschedule.presentation.main.fragments.task.list

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.utils.DateFormatter
import com.khpi.classschedule.views.BasePropertyAdapter
import kotlinx.android.synthetic.main.item_task.view.*

class TaskListAdapter(private val task: List<Task>,
                      private val listenerToParent: OnTaskItemClickListener,
                      private val listener: BasePropertyAdapter.OnScheduleItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(task[position], listener, listenerToParent)
    }

    override fun getItemCount() = task.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BasePropertyAdapter.OnCloseClickListener {

        fun onBind(item: Task,
                   listener: BasePropertyAdapter.OnScheduleItemClickListener,
                   listenerToParent: OnTaskItemClickListener) {

            val propertyAdapter = BasePropertyAdapter(item.properties, listener, adapterPosition, this)
            itemView.recycler_task_item.layoutManager = LinearLayoutManager(itemView.context,
                    LinearLayoutManager.HORIZONTAL, false)
            itemView.recycler_task_item.adapter = propertyAdapter

            itemView.task_date_text.text = itemView.context.resources.getString(R.string.task_deadline,
                    DateFormatter.getDateFromMillis(item.notificationTime))
            itemView.task_group_text.text = item.group
            itemView.task_subject_text.text = item.subject
            itemView.task_description_text.text = item.description


            itemView.setOnClickListener { listenerToParent.onItemClick(item) }

            itemView.task_image_open.setOnClickListener {
                itemView.task_content_layout.alpha = 0.3f
                itemView.recycler_task_item.startAnimation(
                        AnimationUtils.loadAnimation(itemView.context, R.anim.up))
                itemView.recycler_task_item.visibility = View.VISIBLE
            }

        }

        override fun onCloseClick() {
            itemView.task_content_layout.alpha = 1f
            itemView.recycler_task_item.startAnimation(
                    AnimationUtils.loadAnimation(itemView.context, R.anim.down))
            itemView.recycler_task_item.visibility = View.GONE
        }
    }

    interface OnTaskItemClickListener {
        fun onItemClick(item: Task)
    }
}