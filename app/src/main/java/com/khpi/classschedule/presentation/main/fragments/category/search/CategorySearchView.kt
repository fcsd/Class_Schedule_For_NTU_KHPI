package com.khpi.classschedule.presentation.main.fragments.category.search

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseView

interface CategorySearchView : BaseView {

    fun configureRecyclerView(foundedGroup: MutableList<BaseModel>, callback: CategorySearchPresenter)

    @StateStrategyType(SkipStrategy::class)
    fun hideOrShowRecyclerView(rvVisibility: Boolean, tvVisibility: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun openScheduleScreen(baseSchedule: BaseModel)
}