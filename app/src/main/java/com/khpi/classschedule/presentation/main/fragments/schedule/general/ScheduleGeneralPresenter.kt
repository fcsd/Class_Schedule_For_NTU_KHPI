package com.khpi.classschedule.presentation.main.fragments.schedule.general

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.presentation.base.BasePresenter

@InjectViewState
class ScheduleGeneralPresenter : BasePresenter<ScheduleGeneralView>() {

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun onAddClicked() {
        viewState.opedFacultyScreen()
    }
}