package com.khpi.classschedule.data.config

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.models.Task

class TaskRepository(context: Context, private val gson : Gson) {

    companion object {
        private val PREFS = "prefs"
    }

    private val sp: SharedPreferences

    init {
        this.sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun getAllTasks(prefix: String) : MutableList<Task> {

        val tasks = mutableListOf<Task>()
        val keysTask = getKeysTask(prefix)

        keysTask.forEach { key ->
            val jsonTask = sp.getString("$prefix $key task", null) ?: return tasks
            val task = gson.fromJson(jsonTask, Task::class.java)
            tasks.add(task)
        }

        return tasks
    }

    fun getTaskById(prefix: String, key: Int) : Task? {
        val jsonTask = sp.getString("$prefix $key task", null) ?: return null
        return gson.fromJson(jsonTask, Task::class.java)
    }

    fun saveTask(task: Task, isUpdate: Boolean) {
        val prefsEditor = sp.edit()
        val jsonTask = gson.toJson(task)

        prefsEditor.putString("${Constants.GROUP_PREFIX} ${task.id} task", jsonTask)
        prefsEditor.apply()

        if (!isUpdate) {
            addKeyTask(Constants.GROUP_PREFIX, task.id)
        }
    }

    private fun addKeyTask(prefix: String, id: Int) {
        val prefsEditor = sp.edit()
        val keysTask = getKeysTask(prefix)
        keysTask.add(id)

        val jsonText = gson.toJson(keysTask)
        prefsEditor.putString("$prefix task", jsonText)
        prefsEditor.apply()
    }

    private fun getKeysTask(prefix: String) : MutableList<Int> {
        val jsonText = sp.getString("$prefix task", null)
        jsonText?.let { return gson.fromJson<Array<Int>>(it, Array<Int>::class.java).toMutableList() }
                ?: return mutableListOf()
    }

    fun getLastTaskId(prefix: String): Int {
        return getKeysTask(prefix).max() ?: 0
    }

    fun saveTaskSortedIndex(prefix: String, sortedKeys: List<Int>) {
        val prefsEditor = sp.edit()
        val jsonText = gson.toJson(sortedKeys)
        prefsEditor.putString("$prefix task", jsonText)
        prefsEditor.apply()
    }
}