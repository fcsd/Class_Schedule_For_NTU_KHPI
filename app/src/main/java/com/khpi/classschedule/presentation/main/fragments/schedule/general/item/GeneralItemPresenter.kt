package com.khpi.classschedule.presentation.main.fragments.schedule.general.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BasePresenter

@InjectViewState
class GeneralItemPresenter : BasePresenter<GeneralItemView>() {

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun setScheduleInfo(scheduleInfo: List<BaseModel>?) {
        scheduleInfo?.let { viewState.showScheduleInfo(it) }
    }

    fun onItemClick(item: BaseModel) {
        viewState.openScheduleScreen(item)
    }
}