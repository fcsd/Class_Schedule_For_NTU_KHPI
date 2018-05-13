package com.khpi.classschedule.data.config

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.Schedule

class ScheduleRepository(context: Context, private val gson : Gson) {

    companion object {
        private val PREFS = "prefs"
    }

    private val sp: SharedPreferences

    init {
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun saveSchedule(prefix : String,
                     id: Int,
                     scheduleForFirstWeek: Schedule,
                     scheduleForSecondWeek: Schedule,
                     scheduleInfo: BaseModel,
                     isUpdate: Boolean = false) {

        val prefsEditor = sp.edit()
        val jsonFirstWeek = gson.toJson(scheduleForFirstWeek)
        val jsonSecondWeek = gson.toJson(scheduleForSecondWeek)
        prefsEditor.putString("$prefix $id 1", jsonFirstWeek)
        prefsEditor.putString("$prefix $id 2", jsonSecondWeek)
        prefsEditor.apply()

        if (!isUpdate) {
            addKeySchedule(prefix, id)
        }
        saveScheduleInfo(prefix, id, scheduleInfo)
    }

    fun getSchedule(prefix: String, id: Int) : Pair<Schedule, Schedule>? {

        val jsonFirstWeek = sp.getString("$prefix $id 1", null) ?: return null
        val jsonSecondWeek = sp.getString("$prefix $id 2", null) ?: return null

        val scheduleForFirstWeek = gson.fromJson(jsonFirstWeek, Schedule::class.java)
        val scheduleForSecondWeek = gson.fromJson(jsonSecondWeek, Schedule::class.java)

        return Pair(scheduleForFirstWeek, scheduleForSecondWeek)
    }

    fun removeSchedule(prefix: String, id: Int) {
        val prefsEditor = sp.edit()
        prefsEditor.remove("$prefix $id 1")
        prefsEditor.remove("$prefix $id 2")
        prefsEditor.apply()

        removeKeySchedule(prefix, id)
        removeScheduleInfo(prefix, id)
    }

    private fun saveScheduleInfo(prefix: String, id: Int, scheduleInfo: BaseModel) {
        val prefsEditor = sp.edit()
        val schedule = gson.toJson(scheduleInfo)
        prefsEditor.putString("$prefix $id info", schedule)
        prefsEditor.apply()
    }

    private fun removeScheduleInfo(prefix: String, id: Int) {
        val prefsEditor = sp.edit()
        prefsEditor.remove("$prefix $id info")
        prefsEditor.apply()
    }

    fun getScheduleInfoByTypes(prefix: String) : MutableList<BaseModel> {
        val keySchedule = getKeysSchedule(prefix)
        val scheduleInfo = mutableListOf<BaseModel>()

        keySchedule.forEach { key ->
            val jsonInfo = sp.getString("$prefix $key info", null) ?: return scheduleInfo
            val info = gson.fromJson(jsonInfo, BaseModel::class.java)
            scheduleInfo.add(info)
        }

        return scheduleInfo
    }

    private fun addKeySchedule(prefix: String, id: Int) {
        val prefsEditor = sp.edit()
        val keysSchedule = getKeysSchedule(prefix)
        keysSchedule.add(id)

        val jsonText = gson.toJson(keysSchedule)
        prefsEditor.putString("$prefix schedule", jsonText)
        prefsEditor.apply()
    }

    private fun getKeysSchedule(prefix: String) : MutableList<Int> {
        val jsonText = sp.getString("$prefix schedule", null)
        jsonText?.let { return gson.fromJson<Array<Int>>(it, Array<Int>::class.java).toMutableList() }
                ?: return mutableListOf()
    }

    private fun removeKeySchedule(prefix: String, id: Int) {
        val prefsEditor = sp.edit()
        val keysSchedule = getKeysSchedule(prefix)
        keysSchedule.remove(id)

        val jsonText = gson.toJson(keysSchedule)
        prefsEditor.putString("$prefix schedule", jsonText)
        prefsEditor.apply()
    }
}