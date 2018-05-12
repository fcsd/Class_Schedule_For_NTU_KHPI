package com.khpi.classschedule.presentation.main.fragments.task.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.MemoryRepository
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class TaskItemPresenter : BasePresenter<TaskItemView>() {

    @Inject lateinit var memoryRepository: MemoryRepository

    init {
        injector().inject(this)
    }

    private var task: Task? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadTaskInfo(taskId: Int?) {
        val id = taskId ?: return
        task = memoryRepository.getTaskById(Constants.GROUP_PREFIX, id)
        task?.let { viewState.showTask(it) }
    }

    fun onEditClicked() {
        task?.let { viewState.openActionTaskScreen(it) }
    }

}