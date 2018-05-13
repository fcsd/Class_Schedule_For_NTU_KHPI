package com.khpi.classschedule.presentation.main.fragments.schedule.general.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class GeneralListPresenter : BasePresenter<GeneralListView>() {

    //@formatter:off
    @Inject lateinit var scheduleRepository: ScheduleRepository
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var currentTab = 0

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadSchedules() {
        val infoGroups = scheduleRepository.getScheduleInfoByTypes(Constants.GROUP_PREFIX)
        val infoTeachers = scheduleRepository.getScheduleInfoByTypes(Constants.TEACHER_PREFIX)
        val infoAuditories = scheduleRepository.getScheduleInfoByTypes(Constants.AUDITORY_PREFIX)
        viewState.showSavedSchedulesInfo(infoGroups, infoTeachers, infoAuditories, currentTab)
    }

    fun onAddClicked() {
        when (currentTab) {
            0 -> viewState.openFacultyScreen(ScheduleType.GROUP)
            1 -> viewState.showMessage("TEACHER")
            2 -> viewState.showMessage("AUDITORY")
        }

    }

    fun setCurrentItem(position: Int) {
        currentTab = position
    }
}