package com.khpi.classschedule.presentation.main.fragments.task.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.R
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.data.config.TaskRepository
import com.khpi.classschedule.data.models.*
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.views.BasePropertyAdapter
import javax.inject.Inject

@InjectViewState
class TaskListPresenter : BasePresenter<TaskListView>(), TaskListAdapter.OnTaskItemClickListener,
        BasePropertyAdapter.OnScheduleItemClickListener {

    @Inject lateinit var scheduleRepository: ScheduleRepository
    @Inject lateinit var taskRepository: TaskRepository
    @Inject lateinit var settingsRepository: SettingsRepository

    init {
        injector().inject(this)
    }

    private var tasks = mutableListOf<Task>()
    private var prefix: String = ""

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadActiveTask() {

        prefix = settingsRepository.getUserPrefix() ?: return
        val scheduleType = getTypeByPrefix(prefix) ?: return

        if (scheduleRepository.isHasSavedGroupByType(prefix)) {

            tasks = taskRepository.getAllTasks(prefix)
            removeOutdatedTasks()
            if (tasks.isEmpty()) {
                viewState.configureViewForAdding(R.string.add_task_empty, {
                    viewState.openActionTaskScreen(scheduleType)
                })
                return
            }

            tasks.forEach { task ->
                task.properties = mutableListOf()
                task.properties.add(Property("Видалити", R.drawable.ic_remove, R.color.c_e4a66c, PropertyType.REMOVE))
            }
            viewState.showActiveTasks(tasks, this)
        } else {
            when(prefix) {
                Constants.GROUP_PREFIX -> viewState.configureViewForAdding(R.string.add_group_schedule_from_task, {
                    viewState.openAddScheduleScreen(0)
                })
                Constants.TEACHER_PREFIX -> viewState.configureViewForAdding(R.string.add_teacher_schedule_from_task, {
                    viewState.openAddScheduleScreen(1)
                })
            }

        }
    }

    fun showGivenTask(tasks: List<Task>) {
        this.tasks = tasks.toMutableList()
        this.tasks.forEach { task ->
            task.properties = mutableListOf()
            task.properties.add(Property("Видалити", R.drawable.ic_remove, R.color.c_e4a66c, PropertyType.REMOVE))
        }
        viewState.showActiveTasks(this.tasks, this)
    }

    private fun removeOutdatedTasks() {
        val mills = TaskRemove.values()[settingsRepository.getRemovePosition()].timeMillis
        if (mills == 0L) return

        val iterator = tasks.iterator()
        for (task in iterator) {
            if (task.notificationTime + 1000 * 60 * 60 * 24 + mills < System.currentTimeMillis()) {
                taskRepository.removeTask(prefix, task.id)
                viewState.disableTaskNotification(task)
                iterator.remove()
            }
        }
    }

    fun onAddClicked() {
        val scheduleType = getTypeByPrefix(prefix) ?: return
        viewState.openActionTaskScreen(scheduleType)
    }

    override fun onItemClick(item: Task) {
        viewState.openDetailTaskScreen(item)
    }

    override fun onPropertyClick(property: Property, adapterPosition: Int) {
        val task = tasks[adapterPosition]
        removeTask(task)
    }

    private fun removeTask(task: Task) {
        taskRepository.removeTask(prefix, task.id)
        tasks.remove(task)
        viewState.disableTaskNotification(task)
        viewState.notifyDataSetChanged()
        viewState.showMessage("Завдання було видалено успішно")

        if (tasks.size < 1) {
            viewState.hideSortingButton()
        }

        if (tasks.isEmpty()) {
            val scheduleType = getTypeByPrefix(prefix) ?: return
            viewState.configureViewForAdding(R.string.add_task_empty, { viewState.openActionTaskScreen(scheduleType) })
        }
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
        taskRepository.saveTaskSortedIndex(prefix, sortedIndex)

        tasks.clear()
        tasks.addAll(sortedTask)
        viewState.notifyDataSetChanged()
        viewState.showMessage("Сортування за $type успішне")
    }
}