package com.khpi.classschedule.utils

import android.view.View

internal fun View.setVisibility(visible: Boolean) {
    this.visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}