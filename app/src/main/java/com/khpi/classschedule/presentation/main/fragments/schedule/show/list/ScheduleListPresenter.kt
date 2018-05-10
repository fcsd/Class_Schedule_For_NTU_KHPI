package com.khpi.classschedule.presentation.main.fragments.schedule.show.list

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
    private var type: ScheduleType? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun setType(type: ScheduleType?) {
        this.type = type
    }

    fun loadScheduleById(group: BaseModel) {
        this.group = group

        val id = group.id ?: return
        val groupPair = memoryRepository.getSchedule(Constants.GROUP_PREFIX, id)
        scheduleFirstWeek = groupPair?.first ?: run {
            loadScheduleFromInternet(id, isUpdate = false)
            return
        }

        scheduleSecondWeek = groupPair.second
        scheduleFirstWeek?.let { showSchedule(it) }
    }

    private fun loadScheduleFromInternet(id: Int, isUpdate: Boolean) {
        viewState.showProgressDialog()
        loadScheduleForWeeks("Schedule", id, isUpdate)
        loadScheduleForWeeks("Schedule2", id, isUpdate)
    }

    private fun loadScheduleForWeeks(week: String, id: Int, isUpdate: Boolean) {
        scheduleFirstWeek = null
        scheduleSecondWeek = null

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

            synchronizeThreads(group, scheduleFirstWeek, scheduleSecondWeek, isUpdate)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.dismissProgressDialog()
            viewState.showError(errorMessage)
        })
    }

    private fun synchronizeThreads(baseModel: BaseModel?,
                                   scheduleFirstWeek: Schedule?,
                                   scheduleSecondWeek: Schedule?,
                                   isUpdate: Boolean) {
        if (scheduleFirstWeek != null && scheduleSecondWeek != null) {

            val id = baseModel?.id ?: return
            val name = baseModel.title ?: return
            val course = baseModel.course ?: return
            val type = this.type ?: return

            val scheduleInfo = BaseModel(id = id,
                    parentName = "Test Faculty",
                    title = name,
                    course = course,
                    scheduleType = type)

            val prefix = getPrefixByType(type)
            val messageType = getMessageByType(type)

            memoryRepository.saveSchedule(prefix, id, scheduleFirstWeek, scheduleSecondWeek, scheduleInfo, isUpdate)

            viewState.dismissProgressDialog()

            if (isUpdate) {
                viewState.showMessage("Розклад $messageType $name був оновлений успiшно")
            } else {
                viewState.showMessage("Розклад $messageType $name був збережеий успiшно")
            }

            showSchedule(scheduleFirstWeek)
        }
    }

    private fun showSchedule(schedule: Schedule) {
        viewState.showSchedule(schedule)
    }

    fun onRemoveClicked() {

        val type = type ?: return
        val id = group?.id ?: return

        val prefix = getPrefixByType(type)
        val messageType = getMessageByType(type)

        memoryRepository.removeSchedule(prefix, id)

        viewState.showMessage("Розклад $messageType ${group?.title} був видален успішно")
        viewState.closeScreen()
    }

    fun onRefreshClicked() {
        val id = group?.id ?: return
        loadScheduleFromInternet(id, isUpdate = true)
    }
}