package com.khpi.classschedule.presentation.main.fragments.category.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseView

interface CategoryListView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSavedSchedulesInfo(infoGroups: MutableList<BaseModel>,
                               infoTeachers: MutableList<BaseModel>,
                               infoAuditories: MutableList<BaseModel>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPinSchedulesInfo(infoGroups: MutableList<BaseModel>,
                             infoTeachers: MutableList<BaseModel>,
                             infoAuditories: MutableList<BaseModel>,
                             listener: CategoryListPresenter)


    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeToolbarSecondButtonForPin()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeToolbarSecondButtonForShow()
}


