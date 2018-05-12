package com.khpi.classschedule.presentation.main.fragments.task.create

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.CoupleType
import kotlinx.android.synthetic.main.item_couple_type.view.*

class TaskCreateAdapter(private val presenter: TaskCreatePresenter)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val coupleTypes = listOf(CoupleType.LECTURE, CoupleType.LABORATORY, CoupleType.PRACTICE, CoupleType.SEMINAR)
    private var selectedItem = 0

    fun changeSelectedItem(position: Int) {
        selectedItem = position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            BaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_couple_type, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BaseViewHolder)?.onBind(coupleTypes[position], presenter, selectedItem)
    }

    override fun getItemCount() = coupleTypes.size

    override fun getItemId(position: Int): Long = position.toLong()

    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: CoupleType, presenter: TaskCreatePresenter, selectedItem: Int) {
            itemView.couple_type.text = item.title

            if (adapterPosition == selectedItem) {
                itemView.couple_type.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.c_e2e2e2))
            } else {
                itemView.couple_type.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.c_f6f6f6))
            }

            itemView.setOnClickListener { presenter.onTypeSelected(item, adapterPosition) }
        }
    }
}