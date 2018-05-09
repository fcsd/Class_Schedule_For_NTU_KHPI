package com.khpi.classschedule.presentation.main.fragments.schedule.general.item

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseSchedule
import com.khpi.classschedule.presentation.base.BaseView

interface GeneralItemView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showScheduleInfo(scheduleInfo: List<BaseSchedule>)

    @StateStrategyType(SkipStrategy::class)
    fun openFacultyScreen()

    @StateStrategyType(SkipStrategy::class)
    fun openScheduleScreen(baseSchedule: BaseSchedule)
}