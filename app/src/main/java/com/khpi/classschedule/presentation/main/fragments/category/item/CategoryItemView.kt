package com.khpi.classschedule.presentation.main.fragments.category.item

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseView

interface CategoryItemView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showScheduleInfo(scheduleInfo: List<BaseModel>, callback: CategoryItemPresenter)

    @StateStrategyType(SkipStrategy::class)
    fun openScheduleScreen(baseSchedule: BaseModel)

    @StateStrategyType(SkipStrategy::class)
    fun openFacultyScreen(type: ScheduleType, tag: String)

    @StateStrategyType(SkipStrategy::class)
    fun requestChangePinToActivity(newPinned: BaseModel?)
}