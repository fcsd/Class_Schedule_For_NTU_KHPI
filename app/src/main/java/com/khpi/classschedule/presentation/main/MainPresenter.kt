package com.khpi.classschedule.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    //@formatter:off
    @Inject lateinit var scheduleRepository: ScheduleRepository
    //@formatter:on

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {

        viewState.configureView()

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


}