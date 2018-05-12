package com.khpi.classschedule.presentation.main.fragments.task.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.MemoryRepository
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.data.models.TaskSort
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class TaskListPresenter : BasePresenter<TaskListView>(),  TaskListAdapter.OnTaskItemClickListener {

    @Inject lateinit var memoryRepository: MemoryRepository

    init {
        injector().inject(this)
    }

    private var tasks = mutableListOf<Task>()

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadActiveTask() {
        tasks = memoryRepository.getAllTasks(Constants.GROUP_PREFIX)
        viewState.showActiveTasks(tasks, this)
    }

    fun onAddClicked() {
        viewState.openActionTaskScreen()
    }

    override fun onItemClick(item: Task) {
        viewState.openDetailTaskScreen(item)
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
        memoryRepository.saveTaskSortedIndex(Constants.GROUP_PREFIX, sortedIndex)

        tasks.clear()
        tasks.addAll(sortedTask)
        viewState.notifyDataSetChanged()
        viewState.showMessage("Сортування за $type успішне")
    }

}