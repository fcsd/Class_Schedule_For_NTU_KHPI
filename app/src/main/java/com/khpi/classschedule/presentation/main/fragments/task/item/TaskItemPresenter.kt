package com.khpi.classschedule.presentation.main.fragments.task.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.data.config.TaskRepository
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class TaskItemPresenter : BasePresenter<TaskItemView>() {

    @Inject lateinit var taskRepository: TaskRepository
    @Inject lateinit var settingsRepository: SettingsRepository

    init {
        injector().inject(this)
    }

    private var task: Task? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadTaskInfo(taskId: Int?) {
        val id = taskId ?: return
        val prefix = settingsRepository.getUserPrefix() ?: return
        task = taskRepository.getTaskById(prefix, id)
        task?.let { viewState.showTask(it) }
    }

    fun onEditClicked() {
        task?.let { viewState.openActionTaskScreen(it) }
    }
}