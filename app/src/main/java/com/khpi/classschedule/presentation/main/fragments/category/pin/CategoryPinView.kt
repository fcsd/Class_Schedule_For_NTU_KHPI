package com.khpi.classschedule.presentation.main.fragments.category.pin

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseView

interface CategoryPinView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showPinScheduleInfo(scheduleInfo: MutableList<BaseModel>)
}