package com.khpi.classschedule.presentation.main.fragments.schedule.show.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.presentation.base.BasePresenter

@InjectViewState
class ScheduleItemPresenter : BasePresenter<ScheduleItemView>(), ScheduleItemAdapter.OnScheduleItemClickListener {

    private var schedule: List<ScheduleItem>? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun prepareToShowSchedule(schedule: List<ScheduleItem>) {
        this.schedule = schedule
        this.schedule?.let { viewState.showSchedule(it, this) }

    }

    override fun onItemClick(item: ScheduleItem) {

    }
}