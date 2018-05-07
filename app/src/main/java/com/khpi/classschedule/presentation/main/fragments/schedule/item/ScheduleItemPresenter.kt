package com.khpi.classschedule.presentation.main.fragments.schedule.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.BaseSchedule
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.presentation.main.fragments.group.item.GroupItemView
import com.khpi.classschedule.views.BaseAdapter

@InjectViewState
class ScheduleItemPresenter : BasePresenter<ScheduleItemView>(), ScheduleItemAdapter.OnScheduleItemClickListener {

    private var schedule: List<Schedule>? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun prepareToShowSchedule(schedule: List<Schedule>) {
        this.schedule = schedule
        this.schedule?.let { viewState.showSchedule(it, this) }

    }

    override fun onItemClick(item: Schedule) {

    }
}