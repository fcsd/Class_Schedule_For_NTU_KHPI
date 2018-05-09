package com.khpi.classschedule.presentation.main.fragments.schedule.general.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseView

interface GeneralListView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSavedSchedulesInfo(infoGroups: MutableList<BaseModel>,
                               infoTeachers: MutableList<BaseModel>,
                               infoAuditories: MutableList<BaseModel>)

    @StateStrategyType(SkipStrategy::class)
    fun openFacultyScreen(type: ScheduleType)

}


