package com.khpi.classschedule.presentation.main.fragments.paramerts

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.presentation.base.BaseView

interface ParametersView: BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun setPreferencesValue(invert: Boolean,
                            everydayUpdate: Boolean,
                            vibrate: Boolean,
                            sound: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun setRemoveValue(remove: String)

    @StateStrategyType(SkipStrategy::class)
    fun showTaskAlert(titles: Array<CharSequence>)

    @StateStrategyType(SkipStrategy::class)
    fun openDeveloperPage(link: String)
}