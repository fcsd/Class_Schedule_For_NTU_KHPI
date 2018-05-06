package com.khpi.classschedule.presentation.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface BaseView : MvpView {
    fun configureView()

    @StateStrategyType(SkipStrategy::class)
    fun onBackPressed()

    @StateStrategyType(SkipStrategy::class)
    fun showError(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun showMessage(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun dismissProgressDialog()

    @StateStrategyType(SkipStrategy::class)
    fun showProgressDialog()

    @StateStrategyType(SkipStrategy::class)
    fun notifyDataSetChanged()
}
