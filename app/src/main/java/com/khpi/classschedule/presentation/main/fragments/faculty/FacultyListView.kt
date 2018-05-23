package com.khpi.classschedule.presentation.main.fragments.faculty

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseView

interface FacultyListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun onFacultyLoaded(faculties: MutableList<BaseModel>, callback: FacultyListPresenter)

    @StateStrategyType(SkipStrategy::class)
    fun openGroupScreen(model: BaseModel)

    @StateStrategyType(SkipStrategy::class)
    fun reopenCurrentScreen(model: BaseModel, newFragmentTag: String)

    @StateStrategyType(SkipStrategy::class)
    fun openScheduleScreen(model: BaseModel)
}