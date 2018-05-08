package com.khpi.classschedule.data.config

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.khpi.classschedule.data.models.BaseSchedule
import android.R.id.edit



class MemoryRepository(context: Context, private val gson : Gson) {

    companion object {
        private val PREFS = "prefs"
    }

    private val sp: SharedPreferences

    private val groupPrefix = "group"

    init {
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun saveGroupSchedule(groupId: Int, scheduleForFirstWeek: BaseSchedule, scheduleForSecondWeek: BaseSchedule) {
        val prefsEditor = sp.edit()
        val jsonFirstWeek = gson.toJson(scheduleForFirstWeek)
        val jsonSecondWeek = gson.toJson(scheduleForSecondWeek)
        prefsEditor.putString("$groupPrefix $groupId 1", jsonFirstWeek)
        prefsEditor.putString("$groupPrefix $groupId 2", jsonSecondWeek)

        saveKey(groupPrefix, groupId)

        prefsEditor.putInt("$groupPrefix $groupId", groupId)
        prefsEditor.apply()
    }

    fun getGroupSchedule(groupId: Int) : Pair<BaseSchedule, BaseSchedule>? {

        val jsonFirstWeek = sp.getString("$groupPrefix $groupId 1", null) ?: return null
        val jsonSecondWeek = sp.getString("$groupPrefix $groupId 2", null) ?: return null

        val scheduleForFirstWeek = gson.fromJson(jsonFirstWeek, BaseSchedule::class.java)
        val scheduleForSecondWeek = gson.fromJson(jsonSecondWeek, BaseSchedule::class.java)

        return Pair(scheduleForFirstWeek, scheduleForSecondWeek)
    }

    private fun saveKey(prefix: String, groupId: Int) {
        val prefsEditor = sp.edit()
        val keys = getKeys(prefix)
        keys.add(groupId)

        val jsonText = gson.toJson(keys)
        prefsEditor.putString(groupPrefix, jsonText)
        prefsEditor.apply()
    }

    private fun getKeys(prefix: String) : MutableList<Int> {
        val jsonText = sp.getString(prefix, null)
        jsonText?.let { return gson.fromJson<Array<Int>>(it, Array<Int>::class.java).toMutableList() }
                ?: return mutableListOf()
    }

}
