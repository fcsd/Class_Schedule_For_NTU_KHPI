package com.khpi.classschedule.presentation.main.fragments.first

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.presentation.base.BaseView

interface FirstView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openCategoryScreen()
}