package com.khpi.classschedule.presentation.main.fragments.schedule.general

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.presentation.base.BaseView

interface ScheduleGeneralView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun opedFacultyScreen()

}


