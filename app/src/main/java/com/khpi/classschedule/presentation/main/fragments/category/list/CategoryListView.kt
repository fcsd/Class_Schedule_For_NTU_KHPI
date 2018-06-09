package com.khpi.classschedule.presentation.main.fragments.category.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseView

interface CategoryListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showSavedSchedulesInfo(infoGroups: MutableList<BaseModel>,
                               infoTeachers: MutableList<BaseModel>,
                               infoAuditories: MutableList<BaseModel>,
                               currentTab: Int,
                               listener: CategoryListPresenter)

    @StateStrategyType(SkipStrategy::class)
    fun showPinSchedulesInfo(infoGroups: MutableList<BaseModel>,
                             infoTeachers: MutableList<BaseModel>,
                             infoAuditories: MutableList<BaseModel>,
                             currentTab: Int,
                             listener: CategoryListPresenter)


    @StateStrategyType(SkipStrategy::class)
    fun changeToolbarSecondButtonForPin()

    @StateStrategyType(SkipStrategy::class)
    fun changeToolbarSecondButtonForShow()

    @StateStrategyType(SkipStrategy::class)
    fun requestChangePinToActivity(newInfo: BaseModel)

    @StateStrategyType(SkipStrategy::class)
    fun openSearchScreen(type: ScheduleType)
}


