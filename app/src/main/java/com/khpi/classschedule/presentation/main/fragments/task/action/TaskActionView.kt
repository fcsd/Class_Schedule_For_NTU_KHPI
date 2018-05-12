package com.khpi.classschedule.presentation.main.fragments.task.action

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BaseView

interface TaskActionView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showPopupGroup(groupNames: List<String>)

    @StateStrategyType(SkipStrategy::class)
    fun showWarningEmptyGroup()

    @StateStrategyType(SkipStrategy::class)
    fun showPopupSubject(subjects: MutableList<String>)

    @StateStrategyType(SkipStrategy::class)
    fun highlightSelectedType(position: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showDatePicker(notificationTime: Long?)

    fun setConfirmButtonEnabled(enabled: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun closeScreen()

    @StateStrategyType(SkipStrategy::class)
    fun updateTaskInfo(task: Task)

}