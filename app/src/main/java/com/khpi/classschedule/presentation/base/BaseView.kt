package com.khpi.classschedule.presentation.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.Task

interface BaseView : MvpView {
    fun configureView()

    @StateStrategyType(SkipStrategy::class)
    fun onBackPressed()

    @StateStrategyType(SkipStrategy::class)
    fun showError(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun showMessage(message: String)

    fun setCustomProgressBarVisibility(visibility: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun notifyDataSetChanged()

    @StateStrategyType(SkipStrategy::class)
    fun overrideStartAnimation()

    @StateStrategyType(SkipStrategy::class)
    fun overrideBackAnimation()

    @StateStrategyType(SkipStrategy::class)
    fun disableTaskNotification(task: Task)
}
