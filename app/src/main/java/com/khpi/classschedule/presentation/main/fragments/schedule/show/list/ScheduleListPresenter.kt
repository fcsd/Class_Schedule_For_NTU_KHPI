package com.khpi.classschedule.presentation.main.fragments.schedule.show.list

import android.preference.PreferenceManager
import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.data.models.*
import com.khpi.classschedule.presentation.base.BasePresenter
import java.util.*
import javax.inject.Inject

@InjectViewState
class ScheduleListPresenter : BasePresenter<ScheduleListView>() {

    //@formatter:off
    @Inject lateinit var scheduleManager: ScheduleManager
    @Inject lateinit var scheduleRepository: ScheduleRepository
    @Inject lateinit var settingsRepository: SettingsRepository
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var scheduleFirstWeek: Schedule? = null
    private var scheduleSecondWeek: Schedule? = null
    private var group: BaseModel? = null
    private var type: ScheduleType? = null

    private var currentWeek = 1
    private var currentTab = 0

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun setType(type: ScheduleType?) {
        this.type = type
    }

    fun loadScheduleById(group: BaseModel) {
        this.group = group

        val id = group.id ?: return
        val groupPair = scheduleRepository.getSchedule(Constants.GROUP_PREFIX, id)
        scheduleFirstWeek = groupPair?.first ?: run {
            loadScheduleFromInternet(id, isUpdate = false)
            return
        }

        scheduleSecondWeek = groupPair.second

        viewState.showToolbarIcons()
        configureSchedule()
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

            scheduleRepository.saveSchedule(prefix, id, scheduleFirstWeek, scheduleSecondWeek, scheduleInfo, isUpdate)

            viewState.dismissProgressDialog()

            if (isUpdate) {
                viewState.showMessage("Розклад $messageType $name був оновлений успiшно")
            } else {
                viewState.showMessage("Розклад $messageType $name був збережеий успiшно")
            }

            viewState.showToolbarIcons()
            configureSchedule()
        }
    }

    private fun showSchedule() {
        if (currentWeek == 1) {
            scheduleFirstWeek?.let { viewState.showSchedule(it, currentWeek, currentTab) }
        } else {
            scheduleSecondWeek?.let { viewState.showSchedule(it, currentWeek, currentTab) }
        }
    }

    fun changeCurrentWeek() {
        currentWeek = currentWeek %2 + 1
        showSchedule()
    }

    fun onRemoveClicked() {

        val type = type ?: return
        val id = group?.id ?: return

        val prefix = getPrefixByType(type)
        val messageType = getMessageByType(type)

        scheduleRepository.removeSchedule(prefix, id)

        viewState.showMessage("Розклад $messageType ${group?.title} був видален успішно")
        viewState.closeScreen()
    }

    private fun configureSchedule() {
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        currentTab = if (dayOfWeek == 1 || dayOfWeek == 7) {
            0
        } else {
            dayOfWeek - 2
        }

        val invert = settingsRepository.getPreferenceByKey(Constants.INVERT)

        if (invert) {
            changeCurrentWeek()
        } else {
            showSchedule()
        }
    }

    fun onRefreshClicked() {
        val id = group?.id ?: return
        loadScheduleFromInternet(id, isUpdate = true)
    }

    fun setCurrentItem(position: Int) {
        currentTab = position
    }
}