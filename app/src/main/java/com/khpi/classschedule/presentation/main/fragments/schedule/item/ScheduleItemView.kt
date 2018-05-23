package com.khpi.classschedule.presentation.main.fragments.schedule.item

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BaseView

interface ScheduleItemView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun showSchedule(schedule: List<ScheduleItem>, callback: ScheduleItemPresenter)

    @StateStrategyType(SkipStrategy::class)
    fun openBuildingScreen(shortName: String)

    @StateStrategyType(SkipStrategy::class)
    fun openTaskAddScreen(group: String, subject: String, type: String)

    @StateStrategyType(SkipStrategy::class)
    fun openTaskDetailScreen(taskId: Int)

    @StateStrategyType(SkipStrategy::class)
    fun openTaskListScreen(tasksBySubject: List<Task>)
}