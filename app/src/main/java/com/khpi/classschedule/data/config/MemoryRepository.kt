package com.khpi.classschedule.data.config

import android.content.Context
import android.content.SharedPreferences

class MemoryRepository(context: Context) {

    companion object {
        private val PREFS = "prefs"
    }

    private val sp: SharedPreferences

    init {
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }
}
