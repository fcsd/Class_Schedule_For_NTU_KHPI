package com.khpi.classschedule.presentation.main.fragments.task.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.MemoryRepository
import com.khpi.classschedule.data.models.Task
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
        viewState.openCreateTaskScreen()
    }

    override fun onItemClick(item: Task) {
        viewState.openDetailTaskScreen(item)
    }

}