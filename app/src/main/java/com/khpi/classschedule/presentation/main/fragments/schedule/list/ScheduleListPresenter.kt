package com.khpi.classschedule.presentation.main.fragments.schedule.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.data.models.BaseSchedule
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ScheduleListPresenter : BasePresenter<ScheduleListView>() {

    //@formatter:off
    @Inject lateinit var scheduleManager: ScheduleManager
    //@formatter:on

    init {
        injector().inject(this)
    }

    var scheduleFirstWeek: BaseSchedule? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadScheduleById(groupId: Int) {
        viewState.showProgressDialog()
        scheduleManager.getScheduleFirstById(groupId, { schedule ->

            val monday = schedule.monday ?: return@getScheduleFirstById
            val tuesday = schedule.tuesday ?: return@getScheduleFirstById
            val wednesday = schedule.wednesday ?: return@getScheduleFirstById
            val thursday = schedule.thursday ?: return@getScheduleFirstById
            val friday = schedule.friday ?: return@getScheduleFirstById

            scheduleFirstWeek = BaseSchedule(setNormalFormForSchedule(monday),
                                             setNormalFormForSchedule(tuesday),
                                             setNormalFormForSchedule(wednesday),
                                             setNormalFormForSchedule(thursday),
                                             setNormalFormForSchedule(friday))

            viewState.dismissProgressDialog()
            showSchedule(this.scheduleFirstWeek)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.dismissProgressDialog()
            viewState.showError(errorMessage)
        })
    }

    private fun showSchedule(scheduleFirstWeek: BaseSchedule?) {
        scheduleFirstWeek?.let { viewState.showSchedule(it) }
    }

    private fun setNormalFormForSchedule(day: HashMap<String, Schedule>): List<Schedule> {
        for ((key, schedule) in day) {
            schedule.time = setScheduleTime(key)
            schedule.couple = setScheduleCouple(key)
        }

        val withoutEmptySchedule = ArrayList<Schedule>()

        day.values.forEach { couple ->
            if (!couple.name.isNullOrEmpty()) {
                withoutEmptySchedule.add(couple)
            }
        }

        val sort = withoutEmptySchedule.sortedBy { it.couple }

        return sort
    }

    private fun setScheduleTime(key: String): String = when (key) {
        "Para1" -> "08.30 - 10.05"
        "Para2" -> "10.25 - 12.00"
        "Para3" -> "12.35 - 14.10"
        "Para4" -> "14.30 - 16.05"
        "Para5" -> "16.25 - 18.00"
        else -> "18.10 - 19.45"
    }

    private fun setScheduleCouple(key: String): Int = when (key) {
        "Para1" -> 1
        "Para2" -> 2
        "Para3" -> 3
        "Para4" -> 4
        "Para5" -> 5
        else -> 6
    }
}