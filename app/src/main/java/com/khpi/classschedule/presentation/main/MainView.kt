package com.khpi.classschedule.presentation.main

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseView

interface MainView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openCategoryScreen()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openScheduleScreen(pinnedInfo: BaseModel)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openFirstScreen()
}