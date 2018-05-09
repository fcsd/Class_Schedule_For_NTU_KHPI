package com.khpi.classschedule.presentation.main.fragments.group.list

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseView

interface GroupListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun showGroupsByCourse(groupsByFirstCourse: List<BaseModel>,
                           groupsBySecondCourse: List<BaseModel>,
                           groupsByThirdCourse: List<BaseModel>,
                           groupsByFourthCourse: List<BaseModel>,
                           groupsByFifthCourse: List<BaseModel>,
                           groupsBySixthCourse: List<BaseModel>,
                           type: ScheduleType)
}