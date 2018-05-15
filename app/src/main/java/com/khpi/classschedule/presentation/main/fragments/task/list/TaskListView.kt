package com.khpi.classschedule.presentation.main.fragments.task.list

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BaseView

interface TaskListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun openActionTaskScreen()

    @StateStrategyType(SkipStrategy::class)
    fun showActiveTasks(tasks: MutableList<Task>, callback: TaskListPresenter)

    @StateStrategyType(SkipStrategy::class)
    fun openDetailTaskScreen(task: Task)

    @StateStrategyType(SkipStrategy::class)
    fun disableTaskNotification(task: Task)
}