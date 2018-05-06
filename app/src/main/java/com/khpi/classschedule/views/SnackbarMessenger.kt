package com.khpi.classschedule.views

import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import com.khpi.classschedule.R

class SnackbarMessenger(private val coordinatorLayout: CoordinatorLayout) {

    private fun make(view: View, resId: String, duration: Int, colorId: Int): Snackbar {
        val snackbar = Snackbar.make(view, resId, duration)
                .setActionTextColor(ContextCompat.getColor(view.context, R.color.c_ffffff))
        snackbar.view.setBackgroundColor(colorId)
        snackbar.view.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        return snackbar
    }

    fun showErrorSnackBar(message: String) {
        make(coordinatorLayout, message, Snackbar.LENGTH_LONG, ContextCompat.getColor(coordinatorLayout.context, R.color.c_e92823)).show()

    }

    fun showMessageSnackBar(message: String) {
        make(coordinatorLayout, message, Snackbar.LENGTH_LONG, ContextCompat.getColor(coordinatorLayout.context, R.color.c_323232)).show()
    }

}