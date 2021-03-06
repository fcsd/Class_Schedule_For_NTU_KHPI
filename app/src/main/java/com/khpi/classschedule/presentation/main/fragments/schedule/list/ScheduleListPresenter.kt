package com.khpi.classschedule.presentation.main.fragments.schedule.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.data.models.ScheduleType
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
    private var schedules: MutableList<BaseModel> = mutableListOf()

    private var currentWeek = 1
    private var currentTab = -1

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadScheduleById(group: BaseModel, type: ScheduleType?) {
        this.group = group

        val id = group.id ?: return
        this.type = type ?: return
        val prefix = getPrefixByType(type)
        val groupPair = scheduleRepository.getSchedule(prefix, id)
        scheduleFirstWeek = groupPair?.first ?: run {
            loadScheduleFromInternet(id)
            return
        }

        scheduleSecondWeek = groupPair.second

        viewState.showToolbarIcons()

        if (currentTab == -1) {
            configureSchedule()
        } else {
            showSchedule()
        }
    }

    private fun loadScheduleFromInternet(id: Int) {
        viewState.setCustomProgressBarVisibility(true)

        when(type) {
            ScheduleType.GROUP -> {
                loadScheduleForWeeks("Schedule", id)
                loadScheduleForWeeks("Schedule2", id)
            }
            ScheduleType.TEACHER -> {
                loadScheduleForWeeks("ScheduleP", id)
                loadScheduleForWeeks("Schedule2P", id)
            }
            ScheduleType.AUDITORY -> {
                loadScheduleForWeeks("ScheduleA", id)
                loadScheduleForWeeks("Schedule2A", id)
            }
        }
    }

    private fun loadScheduleForWeeks(action: String, id: Int) {
        scheduleFirstWeek = null
        scheduleSecondWeek = null

        scheduleManager.getScheduleByWeekById(action, id, { schedule ->

            val monday = schedule.monday ?: return@getScheduleByWeekById
            val tuesday = schedule.tuesday ?: return@getScheduleByWeekById
            val wednesday = schedule.wednesday ?: return@getScheduleByWeekById
            val thursday = schedule.thursday ?: return@getScheduleByWeekById
            val friday = schedule.friday ?: return@getScheduleByWeekById

            if (action.contains("2"))  {
                scheduleSecondWeek = Schedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
            } else {
                scheduleFirstWeek = Schedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
            }

            synchronizeThreads(group, scheduleFirstWeek, scheduleSecondWeek)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.setCustomProgressBarVisibility(false)
            viewState.showError(errorMessage)
        })
    }

    private fun synchronizeThreads(baseModel: BaseModel?,
                                   scheduleFirstWeek: Schedule?,
                                   scheduleSecondWeek: Schedule?) {
        if (scheduleFirstWeek != null && scheduleSecondWeek != null) {

            val id = baseModel?.id ?: return
            val name = baseModel.title ?: return
            val course = baseModel.course
            val parentName = baseModel.parentName ?: "Test Faculty"
            val type = this.type ?: return

            val needPinned = !scheduleRepository.isHasSavedGroup()

            val scheduleInfo = BaseModel(id = id,
                    parentName = parentName,
                    title = name,
                    course = course,
                    scheduleType = type,
                    isPinned = needPinned)

            if (needPinned) {
                viewState.requestChangePinToActivity(scheduleInfo)
            }

            val prefix = getPrefixByType(type)
            val messageType = getMessageByType(type)

            scheduleRepository.saveSchedule(prefix, id, scheduleFirstWeek, scheduleSecondWeek, scheduleInfo, isUpdate = false)

            viewState.setCustomProgressBarVisibility(false)
            viewState.showMessage("Розклад $messageType $name був збережеий успiшно")

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

    fun setCurrentItem(position: Int) {
        currentTab = position
    }

    fun loadAllSchedules() {
        schedules = scheduleRepository.getScheduleInfoByTypes(Constants.GROUP_PREFIX)
        schedules.addAll(scheduleRepository.getScheduleInfoByTypes(Constants.TEACHER_PREFIX))
        schedules.addAll(scheduleRepository.getScheduleInfoByTypes(Constants.AUDITORY_PREFIX))
        viewState.showSchedulesPopup(schedules)
    }

    fun openCategoryScreen() {
        viewState.openCategoryScreen()
    }

    fun changeCurrentGroup(position: Int) {
        val info = schedules[position]
        val title = info.title ?: return
        this.type = info.scheduleType ?: return

        group = info
        viewState.changeScheduleType(info)
        viewState.changeToolbarTitle(title)
        loadScheduleById(info, type)
    }
}