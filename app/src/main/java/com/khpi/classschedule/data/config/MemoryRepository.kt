package com.khpi.classschedule.data.config

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.data.models.BaseSchedule


class MemoryRepository(context: Context, private val gson : Gson) {

    companion object {
        private val PREFS = "prefs"
    }

    private val sp: SharedPreferences

    init {
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun saveGroupSchedule(prefix : String,
                          groupId: Int,
                          scheduleForFirstWeek: Schedule,
                          scheduleForSecondWeek: Schedule,
                          scheduleInfo: BaseSchedule) {

        val prefsEditor = sp.edit()
        val jsonFirstWeek = gson.toJson(scheduleForFirstWeek)
        val jsonSecondWeek = gson.toJson(scheduleForSecondWeek)
        prefsEditor.putString("$prefix $groupId 1", jsonFirstWeek)
        prefsEditor.putString("$prefix $groupId 2", jsonSecondWeek)
        prefsEditor.apply()

        saveKeySchedule(prefix, groupId)
        saveScheduleInfo(prefix, groupId, scheduleInfo)
    }

    fun getGroupSchedule(prefix: String, groupId: Int) : Pair<Schedule, Schedule>? {

        val jsonFirstWeek = sp.getString("$prefix $groupId 1", null) ?: return null
        val jsonSecondWeek = sp.getString("$prefix $groupId 2", null) ?: return null

        val scheduleForFirstWeek = gson.fromJson(jsonFirstWeek, Schedule::class.java)
        val scheduleForSecondWeek = gson.fromJson(jsonSecondWeek, Schedule::class.java)

        return Pair(scheduleForFirstWeek, scheduleForSecondWeek)
    }

    private fun saveScheduleInfo(prefix: String, groupId: Int, scheduleInfo: BaseSchedule) {
        val prefsEditor = sp.edit()
        val schedule = gson.toJson(scheduleInfo)
        prefsEditor.putString("$prefix $groupId info", schedule)
        prefsEditor.apply()
    }

    fun getScheduleInfoByTypes(prefix: String) : MutableList<BaseSchedule>? {
        val keySchedule = getKeysSchedule(prefix)
        val scheduleInfo = mutableListOf<BaseSchedule>()

        keySchedule.forEach { key ->
            val jsonInfo = sp.getString("$prefix $key info", null) ?: return null
            val info = gson.fromJson(jsonInfo, BaseSchedule::class.java)
            scheduleInfo.add(info)
        }

        return scheduleInfo
    }

    private fun saveKeySchedule(prefix: String, groupId: Int) {
        val prefsEditor = sp.edit()
        val keysSchedule = getKeysSchedule(prefix)
        keysSchedule.add(groupId)

        val jsonText = gson.toJson(keysSchedule)
        prefsEditor.putString(prefix, jsonText)
        prefsEditor.apply()
    }

    private fun getKeysSchedule(prefix: String) : MutableList<Int> {
        val jsonText = sp.getString(prefix, null)
        jsonText?.let { return gson.fromJson<Array<Int>>(it, Array<Int>::class.java).toMutableList() }
                ?: return mutableListOf()
    }

}
