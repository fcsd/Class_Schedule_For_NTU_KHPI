package com.khpi.classschedule.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.utils.DateFormatter
import javax.inject.Inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    //@formatter:off
    @Inject lateinit var scheduleRepository: ScheduleRepository
    @Inject lateinit var settingsRepository: SettingsRepository
    @Inject lateinit var scheduleManager: ScheduleManager
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var allScheduleSize = 0
    private var currentUpdatedSchedule = 0

    override fun onViewLoaded() {

        viewState.configureView()

        if (settingsRepository.getPreferenceByKey(Constants.EVERYDAY_UPDATE) &&
                !DateFormatter.isCurrentDay(settingsRepository.getLastUpdatedMillis())) {
            everydayUpdateSchedules()
        } else {
            openCategoryOrFirstScreen()
        }
    }

    private fun openCategoryOrFirstScreen() {
        settingsRepository.getUserPrefix()?.let { openCategory() } ?: viewState.openFirstScreen()
    }

    private fun everydayUpdateSchedules() {
        val infoGroups = scheduleRepository.getScheduleInfoByTypes(Constants.GROUP_PREFIX)
        val infoTeachers = scheduleRepository.getScheduleInfoByTypes(Constants.TEACHER_PREFIX)
        val infoAuditories = scheduleRepository.getScheduleInfoByTypes(Constants.AUDITORY_PREFIX)

        currentUpdatedSchedule = 0
        allScheduleSize = infoGroups.size + infoTeachers.size + infoAuditories.size

        viewState.setCustomProgressBarVisibility(true)
        infoGroups.forEach { info -> loadScheduleForWeeks("Schedule", "Schedule2", info) }
        infoTeachers.forEach { info -> loadScheduleForWeeks("ScheduleP", "Schedule2P", info) }
        infoAuditories.forEach { info -> loadScheduleForWeeks("ScheduleA", "Schedule2A", info) }
    }

    fun openCategory() {

        val infoGroups = scheduleRepository.getScheduleInfoByTypes(Constants.GROUP_PREFIX)
        val infoTeachers = scheduleRepository.getScheduleInfoByTypes(Constants.TEACHER_PREFIX)
        val infoAuditories = scheduleRepository.getScheduleInfoByTypes(Constants.AUDITORY_PREFIX)

        val pinnedInfo = findPinnedInfoOrNull(infoGroups)
                ?: findPinnedInfoOrNull(infoTeachers)
                ?: findPinnedInfoOrNull(infoAuditories)
                ?: run {
                    viewState.openCategoryScreen()
                    return
                }

        viewState.openScheduleScreen(pinnedInfo)
    }

    private fun loadScheduleForWeeks(action: String, action2: String, baseModel: BaseModel) {

        val id = baseModel.id ?: return
        var scheduleFirstWeek: Schedule? = null
        var scheduleSecondWeek: Schedule? = null

        scheduleManager.getScheduleByWeekById(action, id, { schedule ->

            val monday = schedule.monday ?: return@getScheduleByWeekById
            val tuesday = schedule.tuesday ?: return@getScheduleByWeekById
            val wednesday = schedule.wednesday ?: return@getScheduleByWeekById
            val thursday = schedule.thursday ?: return@getScheduleByWeekById
            val friday = schedule.friday ?: return@getScheduleByWeekById

            scheduleFirstWeek = Schedule(
                    setNormalFormForSchedule(monday),
                    setNormalFormForSchedule(tuesday),
                    setNormalFormForSchedule(wednesday),
                    setNormalFormForSchedule(thursday),
                    setNormalFormForSchedule(friday))

            synchronizeThreads(baseModel, scheduleFirstWeek, scheduleSecondWeek)

        }, {
            viewState.setCustomProgressBarVisibility(false)
            openCategoryOrFirstScreen()
        })

        scheduleManager.getScheduleByWeekById(action2, id, { schedule ->

            val monday = schedule.monday ?: return@getScheduleByWeekById
            val tuesday = schedule.tuesday ?: return@getScheduleByWeekById
            val wednesday = schedule.wednesday ?: return@getScheduleByWeekById
            val thursday = schedule.thursday ?: return@getScheduleByWeekById
            val friday = schedule.friday ?: return@getScheduleByWeekById

            scheduleSecondWeek = Schedule(
                    setNormalFormForSchedule(monday),
                    setNormalFormForSchedule(tuesday),
                    setNormalFormForSchedule(wednesday),
                    setNormalFormForSchedule(thursday),
                    setNormalFormForSchedule(friday))

            synchronizeThreads(baseModel, scheduleFirstWeek, scheduleSecondWeek)

        }, {
            viewState.setCustomProgressBarVisibility(false)
            openCategoryOrFirstScreen()
        })
    }

    private fun synchronizeThreads(baseModel: BaseModel,
                                   scheduleFirstWeek: Schedule?,
                                   scheduleSecondWeek: Schedule?) {
        if (scheduleFirstWeek != null && scheduleSecondWeek != null) {

            val id =  baseModel.id ?: return
            val type = baseModel.scheduleType ?: return

            val prefix = getPrefixByType(type)
            scheduleRepository.saveSchedule(prefix, id, scheduleFirstWeek, scheduleSecondWeek, baseModel, isUpdate = true)

            currentUpdatedSchedule++
            if (currentUpdatedSchedule == allScheduleSize) {
                viewState.setCustomProgressBarVisibility(false)
                settingsRepository.saveLastUpdatedMillis(System.currentTimeMillis())
                viewState.showMessage("Щоденне оновлення усіх розкладів завершене!")
                openCategoryOrFirstScreen()
            }
        }
    }
}