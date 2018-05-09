package com.khpi.classschedule.presentation.main.fragments.schedule.general.list

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseSchedule
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseView

interface GeneralListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showSavedSchedulesInfo(infoGroups: MutableList<BaseSchedule>,
                               infoTeachers: MutableList<BaseSchedule>,
                               infoAuditories: MutableList<BaseSchedule>)

    @StateStrategyType(SkipStrategy::class)
    fun openFacultyScreen(type: ScheduleType)

}


