package com.khpi.classschedule.presentation.main.fragments.task.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BasePresenter

@InjectViewState
class TaskItemPresenter : BasePresenter<TaskItemView>() {

    private var task: Task? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadTaskInfo(task: Task?) {
        this.task = task
        task?.let { viewState.showTask(it) }
    }

    fun onEditClicked() {
        task?.let { viewState.openActionTaskScreen(it) }
    }

}