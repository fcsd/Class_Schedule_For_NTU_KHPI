package com.khpi.classschedule.presentation.main.fragments.task.item

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BaseView

interface TaskItemView: BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showTask(task: Task)

    @StateStrategyType(SkipStrategy::class)
    fun openActionTaskScreen(task: Task)
}