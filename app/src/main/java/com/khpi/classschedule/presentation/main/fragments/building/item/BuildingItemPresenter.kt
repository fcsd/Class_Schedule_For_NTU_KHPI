package com.khpi.classschedule.presentation.main.fragments.building.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.BuildingManager
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class BuildingItemPresenter : BasePresenter<BuildingItemView>() {

    //@formatter:off
    @Inject lateinit var buildingManager: BuildingManager
    //@formatter:on

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadBuildingByShortName(shortName: String) {
        val building = buildingManager.getShortBuildingByShortName(shortName)
        building?.let { viewState.onBuildingLoaded(it) } ?: viewState.showError("Неможливо знайти корпус!")
    }
}