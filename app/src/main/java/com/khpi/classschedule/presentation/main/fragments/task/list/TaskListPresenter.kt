package com.khpi.classschedule.presentation.main.fragments.task.list

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.R
import com.khpi.classschedule.data.config.TaskRepository
import com.khpi.classschedule.data.models.Property
import com.khpi.classschedule.data.models.PropertyType
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.data.models.TaskSort
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.views.BasePropertyAdapter
import javax.inject.Inject

@InjectViewState
class TaskListPresenter : BasePresenter<TaskListView>(), TaskListAdapter.OnTaskItemClickListener,
        BasePropertyAdapter.OnScheduleItemClickListener {

    @Inject lateinit var taskRepository: TaskRepository

    init {
        injector().inject(this)
    }

    private var tasks = mutableListOf<Task>()

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadActiveTask() {
        tasks = taskRepository.getAllTasks(Constants.GROUP_PREFIX)
        tasks.forEach { task ->
            task.properties = mutableListOf()
            task.properties.add(Property("Видалити", R.drawable.ic_remove_orange, PropertyType.REMOVE))
        }

        viewState.showActiveTasks(tasks, this)
    }

    fun onAddClicked() {
        viewState.openActionTaskScreen()
    }

    override fun onItemClick(item: Task) {
        viewState.openDetailTaskScreen(item)
    }

    override fun onPropertyClick(property: Property, adapterPosition: Int) {
        val task = tasks[adapterPosition]
        removeTask(task)
    }

    private fun removeTask(task: Task) {
        taskRepository.removeTask(Constants.GROUP_PREFIX, task.id)
        tasks.remove(task)
        viewState.disableTaskNotification(task)
        viewState.notifyDataSetChanged()
        viewState.showMessage("Завдання було видалено успішно")
    }


    fun sortedBy(sortedBy: TaskSort) {

        val sortedTask = when (sortedBy) {
            TaskSort.DATE -> tasks.sortedBy { it.notificationTime }
            TaskSort.GROUP -> tasks.sortedBy { it.group }
            TaskSort.SUBJECT -> tasks.sortedBy { it.subject }
        }

        val type = when (sortedBy) {
            TaskSort.DATE -> "датою"
            TaskSort.GROUP -> "групою"
            TaskSort.SUBJECT -> "викладачем"
        }

        val sortedIndex = sortedTask.map { it.id }
        taskRepository.saveTaskSortedIndex(Constants.GROUP_PREFIX, sortedIndex)

        tasks.clear()
        tasks.addAll(sortedTask)
        viewState.notifyDataSetChanged()
        viewState.showMessage("Сортування за $type успішне")
    }
}