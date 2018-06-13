package com.khpi.classschedule.presentation.main.fragments.schedule.item

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.PropertyType
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.utils.setVisibility
import com.khpi.classschedule.views.BasePropertyAdapter
import kotlinx.android.synthetic.main.item_schedule.view.*
import android.text.style.ImageSpan
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.widget.TextView


class ScheduleItemAdapter(private val schedule: List<ScheduleItem>,
                          private val type: ScheduleType,
                          private val listener: BasePropertyAdapter.OnScheduleItemClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(schedule[position], type, listener)
    }

    override fun getItemCount() = schedule.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), BasePropertyAdapter.OnCloseClickListener {

        fun onBind(item: ScheduleItem, type: ScheduleType, listener: BasePropertyAdapter.OnScheduleItemClickListener) {

            val propertyAdapter = BasePropertyAdapter(item.properties, listener, adapterPosition, this)
            itemView.recycler_schedule_item.layoutManager = LinearLayoutManager(itemView.context,
                    LinearLayoutManager.HORIZONTAL, false)
            itemView.recycler_schedule_item.adapter = propertyAdapter

            itemView.schedule_time_text.text = item.time
            itemView.schedule_name_text.text = item.name
            itemView.schedule_auditory_text.text = item.auditory

            when (type) {
                ScheduleType.AUDITORY,
                ScheduleType.TEACHER -> {
                    val replacedGroup = item.teacher?.trim()?.split(" ") ?: return
                    if (replacedGroup.size == 1) {
                        setDefaultSpannable(item)
                    } else {

                        itemView.schedule_teacher_text.movementMethod = object:LinkMovementMethod() {
                            override fun onTouchEvent(widget:TextView, buffer:Spannable, event:MotionEvent):Boolean {
                                Selection.removeSelection(buffer)
                                return super.onTouchEvent(widget, buffer, event)
                            }
                        }
                        val fewGroups = itemView.context.resources
                                .getQuantityString(R.plurals.few_groups, replacedGroup.size, replacedGroup.size)
                        val text = "$fewGroups   \$   ${item.type}"
                        setNotDefaultSpannable(text, R.drawable.ic_arrow_right, item, false)
                    }
                }
                else -> setDefaultSpannable(item)
            }

            val propertyTypes = item.properties.map { it.type }
            if (propertyTypes.contains(PropertyType.TASK_ADD)) {
                itemView.schedule_image_open.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_arrow_down_orange))
                itemView.schedule_image_task.setVisibility(false)
            } else if (propertyTypes.contains(PropertyType.TASK_SHOW)) {
                itemView.schedule_image_open.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_arrow_down_blue))
                itemView.schedule_image_task.setVisibility(true)
            }

            itemView.schedule_image_open.setOnClickListener {
                itemView.schedule_content_layout.alpha = 0.3f
                itemView.recycler_schedule_item.startAnimation(
                        AnimationUtils.loadAnimation(itemView.context, R.anim.up))
                itemView.recycler_schedule_item.visibility = View.VISIBLE
            }
        }

        private fun setDefaultSpannable(item: ScheduleItem) {
            val text = "${item.teacher?.trim()}   ${item.type}"
            val spanString = Spannable.Factory.getInstance().newSpannable(text)

            val lengthType = item.type?.length ?: 0
            spanString.setSpan(ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.c_000000)),
                    text.length - lengthType, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            itemView.schedule_teacher_text.text = spanString
        }

        private fun setNotDefaultSpannable(content: String, drawable: Int, item: ScheduleItem, isExpanded: Boolean) {

            val imageIndex = content.indexOf("$")

            val spanString = Spannable.Factory.getInstance().newSpannable(content)

            val lineHeight = itemView.context.resources.getDimension(R.dimen._16sdp).toInt()
            val drawableBounds = ContextCompat.getDrawable(itemView.context, drawable)
            drawableBounds?.setBounds(0, 0, lineHeight, lineHeight)
            spanString.setSpan(ImageSpan(drawableBounds), imageIndex, imageIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            val lengthType = item.type?.length ?: 0
            spanString.setSpan(ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.c_000000)),
                    content.length - lengthType, content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            spanString.setSpan(object : ClickableSpan() {
                override fun onClick(v: View) {
                    if (isExpanded) {
                        val replacedGroup = item.teacher?.trim()?.split(" ") ?: return
                        val fewGroups = itemView.context.resources
                                .getQuantityString(R.plurals.few_groups, replacedGroup.size, replacedGroup.size)
                        val text = "$fewGroups   \$   ${item.type}"
                        setNotDefaultSpannable(text, R.drawable.ic_arrow_right, item, !isExpanded)
                    } else {
                        val text = "${item.teacher?.trim()}   \$   ${item.type}"
                        setNotDefaultSpannable(text, R.drawable.ic_arrow_left, item, !isExpanded)
                    }
                }
            }, imageIndex, imageIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            itemView.schedule_teacher_text.text = spanString
        }

        override fun onCloseClick() {
            itemView.schedule_content_layout.alpha = 1f
            itemView.recycler_schedule_item.startAnimation(
                    AnimationUtils.loadAnimation(itemView.context, R.anim.down))
            itemView.recycler_schedule_item.visibility = View.GONE
        }
    }
}