package com.khpi.classschedule.presentation.main.fragments.building.info

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.khpi.classschedule.R

class BuildingInfoDecorator(context: Context,
                            private val paddingLeft : Int? = null,
                            private val count: Int) : RecyclerView.ItemDecoration() {

    private val divider: Drawable? = ContextCompat.getDrawable(context, R.drawable.dotted)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val divider = this.divider ?: return

        val left = paddingLeft ?: parent.paddingLeft
        val right = parent.width

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }
}