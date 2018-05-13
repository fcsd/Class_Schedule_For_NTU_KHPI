package com.khpi.classschedule.data.config

import android.content.Context
import android.content.SharedPreferences

class SettingsRepository(context: Context) {

    companion object {
        private val PREFS = "prefs"
    }

    private val sp: SharedPreferences

    init {
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun savePreferenceByKey(key : String, value: Boolean) {
        val prefsEditor = sp.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }

    fun getPreferenceByKey(key : String): Boolean {
        return sp.getBoolean(key, false)
    }

    fun saveRemovePosition(position: Int) {
        val prefsEditor = sp.edit()
        prefsEditor.putInt("remove_task", position)
        prefsEditor.apply()
    }

    fun getRemovePosition(): Int {
        return sp.getInt("remove_task", 0)
    }
}