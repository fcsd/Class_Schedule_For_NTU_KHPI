package com.khpi.classschedule.presentation.main.fragments.task.action

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.khpi.classschedule.R


class TaskActionDecorator(private val context: Context) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val paddingRight = context.resources.getDimension(R.dimen._12sdp)
        val params = view.layoutParams as RecyclerView.LayoutParams
        val count = parent.adapter.itemCount

        for (i in 0 until count) {
            val position = params.viewAdapterPosition
            if (position != count - 1) {
                outRect.right = paddingRight.toInt()
            }
        }
    }
}