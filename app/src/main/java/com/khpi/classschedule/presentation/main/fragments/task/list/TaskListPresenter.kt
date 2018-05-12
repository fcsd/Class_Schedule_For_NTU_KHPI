package com.khpi.classschedule.presentation.main.fragments.task.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.config.MemoryRepository
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class TaskListPresenter : BasePresenter<TaskListView>() {

    @Inject lateinit var memoryRepository: MemoryRepository

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadActiveTask() {

    }

    fun onAddClicked() {
        viewState.openCreateTaskScreen()
    }
}