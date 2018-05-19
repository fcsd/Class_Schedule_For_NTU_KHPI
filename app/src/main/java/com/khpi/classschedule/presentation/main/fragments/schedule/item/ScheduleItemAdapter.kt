package com.khpi.classschedule.presentation.main.fragments.schedule.item

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.PropertyType
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.utils.setVisibility
import com.khpi.classschedule.views.BasePropertyAdapter
import kotlinx.android.synthetic.main.item_schedule.view.*

class ScheduleItemAdapter(private val schedule: List<ScheduleItem>,
                          private val listener: BasePropertyAdapter.OnScheduleItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(schedule[position], listener)
    }

    override fun getItemCount() = schedule.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BasePropertyAdapter.OnCloseClickListener {

        fun onBind(item: ScheduleItem, listener: BasePropertyAdapter.OnScheduleItemClickListener) {

            val propertyAdapter = BasePropertyAdapter(item.properties, listener, adapterPosition, this)
            itemView.recycler_schedule_item.layoutManager = LinearLayoutManager(itemView.context,
                    LinearLayoutManager.HORIZONTAL, false)
            itemView.recycler_schedule_item.adapter = propertyAdapter

            itemView.schedule_time_text.text = item.time
            itemView.schedule_name_text.text = item.name
            itemView.schedule_teacher_text.text = item.teacher
            itemView.schedule_type_text.text = item.type
            itemView.schedule_auditory_text.text = item.auditory

            val propertyTypes = item.properties.map { it.type }
            if (propertyTypes.contains(PropertyType.TASK_ADD)) {
                itemView.schedule_image_task.setVisibility(false)
            } else if (propertyTypes.contains(PropertyType.TASK_SHOW)) {
                itemView.schedule_image_task.setVisibility(true)
            }

            itemView.schedule_image_open.setOnClickListener {
                itemView.schedule_content_layout.alpha = 0.3f
                itemView.recycler_schedule_item.startAnimation(
                        AnimationUtils.loadAnimation(itemView.context, R.anim.up))
                itemView.recycler_schedule_item.visibility = View.VISIBLE
            }
        }

        override fun onCloseClick() {
            itemView.schedule_content_layout.alpha = 1f
            itemView.recycler_schedule_item.startAnimation(
                    AnimationUtils.loadAnimation(itemView.context, R.anim.down))
            itemView.recycler_schedule_item.visibility = View.GONE
        }
    }
}