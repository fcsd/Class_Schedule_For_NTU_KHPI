package com.khpi.classschedule.presentation.main.fragments.schedule.show.list

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.presentation.base.BaseView

interface ScheduleListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showSchedule(schedule: Schedule, currentWeek: Int, currentTab: Int)

    @StateStrategyType(SkipStrategy::class)
    fun closeScreen()

    @StateStrategyType(SkipStrategy::class)
    fun showToolbarIcons()

}