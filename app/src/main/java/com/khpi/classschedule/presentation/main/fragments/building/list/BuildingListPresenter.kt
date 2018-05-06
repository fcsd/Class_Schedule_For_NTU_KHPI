package com.khpi.classschedule.presentation.main.fragments.building.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.BuildingManager
import com.khpi.classschedule.data.models.ShortBuilding
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class BuildingListPresenter : BasePresenter<BuildingListView>() {

    //@formatter:off
    @Inject lateinit var buildingManager: BuildingManager
    //@formatter:on

    private var buildings = mutableListOf<ShortBuilding>()

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()

        buildings = buildingManager.getAllShortBuildings()
        viewState.onBuildingsLoaded(buildings)
    }

    fun onBuildingSelected(shortName: String) {
        viewState.openBuildingScreen(shortName)
    }

    fun onSearchEntered(searchBuilding: String) {
        buildings.clear()
        if (searchBuilding.isEmpty()) {
            buildings.addAll(buildingManager.getAllShortBuildings())
        } else {
            val foundedBuildings = buildingManager.getAllShortBuildings().filter { it.shortName.startsWith(searchBuilding, true) }
            buildings.addAll(foundedBuildings)
        }
        viewState.notifyDataSetChanged()
    }
}