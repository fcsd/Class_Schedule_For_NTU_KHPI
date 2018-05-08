package com.khpi.classschedule.presentation.main.fragments.schedule.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.config.MemoryRepository
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.data.models.BaseSchedule
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ScheduleListPresenter : BasePresenter<ScheduleListView>() {

    //@formatter:off
    @Inject lateinit var scheduleManager: ScheduleManager
    @Inject lateinit var memoryRepository: MemoryRepository
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var scheduleFirstWeek: BaseSchedule? = null
    private var scheduleSecondWeek: BaseSchedule? = null
    private var groupId: Int? = null
    private var groupName: String? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadScheduleById(groupId: Int, groupName: String) {
        this.groupId = groupId
        this.groupName = groupName

        val groupPair = memoryRepository.getGroupSchedule(groupId)
        scheduleFirstWeek = groupPair?.first ?: run {
            loadScheduleFromInternet(groupId)
            return
        }

        scheduleSecondWeek = groupPair.second
        scheduleFirstWeek?.let { showSchedule(it) }
    }

    private fun loadScheduleFromInternet(groupId: Int) {
        viewState.showProgressDialog()
        loadScheduleForWeeks("Schedule", groupId)
        loadScheduleForWeeks("Schedule2", groupId)
    }

    private fun loadScheduleForWeeks(week : String, groupId : Int) {
        scheduleManager.getScheduleByWeekById(week, groupId, { schedule ->

            val monday = schedule.monday ?: return@getScheduleByWeekById
            val tuesday = schedule.tuesday ?: return@getScheduleByWeekById
            val wednesday = schedule.wednesday ?: return@getScheduleByWeekById
            val thursday = schedule.thursday ?: return@getScheduleByWeekById
            val friday = schedule.friday ?: return@getScheduleByWeekById

            when (week) {
                "Schedule" -> scheduleFirstWeek = BaseSchedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
                "Schedule2" -> scheduleSecondWeek = BaseSchedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
            }

            viewState.dismissProgressDialog()
            synchronizeThreads(groupName, scheduleFirstWeek, scheduleSecondWeek)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.dismissProgressDialog()
            viewState.showError(errorMessage)
        })
    }

    private fun synchronizeThreads(name : String?, scheduleFirstWeek: BaseSchedule?, scheduleSecondWeek: BaseSchedule?) {
        if (scheduleFirstWeek != null && scheduleSecondWeek != null) {
            groupId?.let { memoryRepository.saveGroupSchedule(it, scheduleFirstWeek, scheduleSecondWeek) }
            viewState.showMessage("Група $name була збережена успiшно!")
            showSchedule(scheduleFirstWeek)
        }
    }

    private fun showSchedule(schedule: BaseSchedule) {
        viewState.showSchedule(schedule)
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

        return withoutEmptySchedule.sortedBy { it.couple }
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