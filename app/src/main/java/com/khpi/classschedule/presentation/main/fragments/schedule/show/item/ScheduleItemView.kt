package com.khpi.classschedule.presentation.main.fragments.schedule.show.item

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.presentation.base.BaseView

interface ScheduleItemView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun showSchedule(schedule: List<ScheduleItem>, callback: ScheduleItemPresenter)
}