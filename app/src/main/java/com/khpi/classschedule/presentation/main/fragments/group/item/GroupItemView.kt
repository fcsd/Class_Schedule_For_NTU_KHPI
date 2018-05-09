package com.khpi.classschedule.presentation.main.fragments.group.item

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseView

interface GroupItemView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun showGroups(groups: MutableList<BaseModel>, callback: GroupItemPresenter)

    @StateStrategyType(SkipStrategy::class)
    fun openScheduleScreen(group: BaseModel)

}