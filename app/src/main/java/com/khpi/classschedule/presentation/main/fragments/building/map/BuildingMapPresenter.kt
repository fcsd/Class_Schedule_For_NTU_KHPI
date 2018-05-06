package com.khpi.classschedule.presentation.main.fragments.building.map

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.presentation.base.BasePresenter

@InjectViewState
class BuildingMapPresenter : BasePresenter<BuildingMapView>() {

    override fun onViewLoaded() {
        viewState.configureView()
    }
}