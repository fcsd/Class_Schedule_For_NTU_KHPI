package com.khpi.classschedule.presentation.main.fragments.schedule.list

import android.view.View
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.presentation.base.BaseView

interface ScheduleListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showSchedule(schedule: Schedule, currentWeek: Int, currentTab: Int)

    @StateStrategyType(SkipStrategy::class)
    fun showToolbarIcons()

    @StateStrategyType(SkipStrategy::class)
    fun openCategoryScreen()

    @StateStrategyType(SkipStrategy::class)
    fun showSchedulesPopup(schedules: MutableList<BaseModel>)

    @StateStrategyType(SkipStrategy::class)
    fun changeToolbarTitle(title: String)
}