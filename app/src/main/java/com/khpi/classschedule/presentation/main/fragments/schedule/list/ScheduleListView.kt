package com.khpi.classschedule.presentation.main.fragments.schedule.list

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.presentation.base.BaseView

interface ScheduleListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showSchedule(schedule: Schedule)

}