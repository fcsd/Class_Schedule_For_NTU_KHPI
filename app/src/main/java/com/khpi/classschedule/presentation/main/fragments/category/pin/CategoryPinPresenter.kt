package com.khpi.classschedule.presentation.main.fragments.category.pin

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BasePresenter

@InjectViewState
class CategoryPinPresenter : BasePresenter<CategoryPinView>() {

    private var scheduleInfo = mutableListOf<BaseModel>()

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun setPinScheduleInfo(scheduleInfo: List<BaseModel>?) {
        scheduleInfo?.let {
            this.scheduleInfo = it.toMutableList()
            viewState.showPinScheduleInfo(this.scheduleInfo)
        }
    }
}