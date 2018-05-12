package com.khpi.classschedule.presentation.main.fragments.task.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseView
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListPresenter

interface TaskListView : BaseView {

    @StateStrategyType(SkipStrategy::class)
    fun openCreateTaskScreen()
}