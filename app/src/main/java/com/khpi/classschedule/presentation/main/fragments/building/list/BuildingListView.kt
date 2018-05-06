package com.khpi.classschedule.presentation.main.fragments.building.list

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.ShortBuilding
import com.khpi.classschedule.presentation.base.BaseView


interface BuildingListView : BaseView {
    fun onBuildingsLoaded(buildings: MutableList<ShortBuilding>)

    @StateStrategyType(SkipStrategy::class)
    fun openBuildingScreen(shortName: String)
}