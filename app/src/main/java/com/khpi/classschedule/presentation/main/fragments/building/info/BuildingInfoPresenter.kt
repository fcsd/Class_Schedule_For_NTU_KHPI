package com.khpi.classschedule.presentation.main.fragments.building.info

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.BuildingManager
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class BuildingInfoPresenter : BasePresenter<BuildingInfoView>() {

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
        val building = buildingManager.getFullBuildingByShortName(shortName)

        building?.let {
            val buildingPairs = it.data
            val units = it.units
            viewState.showBuildingInfo(buildingPairs, units)
        }
    }

}