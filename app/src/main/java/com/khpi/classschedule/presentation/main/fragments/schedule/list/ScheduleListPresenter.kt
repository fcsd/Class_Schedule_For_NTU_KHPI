package com.khpi.classschedule.presentation.main.fragments.schedule.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.config.MemoryRepository
import com.khpi.classschedule.data.models.*
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

    private var scheduleFirstWeek: Schedule? = null
    private var scheduleSecondWeek: Schedule? = null
    private var group: BaseModel? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadScheduleById(group: BaseModel) {
        this.group = group

        val id = group.id ?: return
        val groupPair = memoryRepository.getSchedule(Constants.GROUP_PREFIX, id)
        scheduleFirstWeek = groupPair?.first ?: run {
            loadScheduleFromInternet(id)
            return
        }

        scheduleSecondWeek = groupPair.second
        scheduleFirstWeek?.let { showSchedule(it) }
    }

    private fun loadScheduleFromInternet(id: Int) {
        viewState.showProgressDialog()
        loadScheduleForWeeks("Schedule", id)
        loadScheduleForWeeks("Schedule2", id)
    }

    private fun loadScheduleForWeeks(week: String, id: Int) {
        scheduleManager.getScheduleByWeekById(week, id, { schedule ->

            val monday = schedule.monday ?: return@getScheduleByWeekById
            val tuesday = schedule.tuesday ?: return@getScheduleByWeekById
            val wednesday = schedule.wednesday ?: return@getScheduleByWeekById
            val thursday = schedule.thursday ?: return@getScheduleByWeekById
            val friday = schedule.friday ?: return@getScheduleByWeekById

            when (week) {
                "Schedule" -> scheduleFirstWeek = Schedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
                "Schedule2" -> scheduleSecondWeek = Schedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
            }

            synchronizeThreads(group, scheduleFirstWeek, scheduleSecondWeek)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.dismissProgressDialog()
            viewState.showError(errorMessage)
        })
    }

    private fun synchronizeThreads(baseModel: BaseModel?, scheduleFirstWeek: Schedule?, scheduleSecondWeek: Schedule?) {
        if (scheduleFirstWeek != null && scheduleSecondWeek != null) {

            val id = baseModel?.id ?: return
            val name = baseModel.title ?: return
            val course = baseModel.course ?: return

            val scheduleInfo = BaseModel(id = id,
                    parentName = "Test Faculty",
                    title = name,
                    course = course,
                    scheduleType = ScheduleType.GROUP)

            memoryRepository.saveSchedule(Constants.GROUP_PREFIX, id, scheduleFirstWeek, scheduleSecondWeek, scheduleInfo)

            viewState.dismissProgressDialog()
            viewState.showMessage("Група $name була збережена успiшно!")
            showSchedule(scheduleFirstWeek)
        }
    }

    private fun showSchedule(schedule: Schedule) {
        viewState.showSchedule(schedule)
    }
}