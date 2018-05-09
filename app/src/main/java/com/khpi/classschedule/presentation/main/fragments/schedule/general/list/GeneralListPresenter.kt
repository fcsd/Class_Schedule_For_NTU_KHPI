package com.khpi.classschedule.presentation.main.fragments.schedule.general.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.MemoryRepository
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class GeneralListPresenter : BasePresenter<GeneralListView>() {

    //@formatter:off
    @Inject lateinit var memoryRepository: MemoryRepository
    //@formatter:on

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadSchedules() {
        val infoGroups = memoryRepository.getScheduleInfoByTypes(Constants.GROUP_PREFIX) ?: return
        val infoTeachers = memoryRepository.getScheduleInfoByTypes(Constants.TEACHER_PREFIX) ?: return
        val infoAuditories = memoryRepository.getScheduleInfoByTypes(Constants.AUDITORY_PREFIX) ?: return
        viewState.showSavedSchedulesInfo(infoGroups, infoTeachers, infoAuditories)
    }

    fun onAddClicked(visibleTab: Int) {
        when (visibleTab) {
            0 -> viewState.openFacultyScreen(ScheduleType.GROUP)
            1 -> viewState.showMessage("TEACHER")
            2 -> viewState.showMessage("AUDITORY")
        }

    }
}