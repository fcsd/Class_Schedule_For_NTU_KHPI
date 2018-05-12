package com.khpi.classschedule.di

import com.khpi.classschedule.di.modules.ApiModule
import com.khpi.classschedule.di.modules.NetworkModule
import com.khpi.classschedule.di.modules.AppModule
import com.khpi.classschedule.presentation.main.MainPresenter
import com.khpi.classschedule.presentation.main.fragments.building.info.BuildingInfoPresenter
import com.khpi.classschedule.presentation.main.fragments.building.list.BuildingListPresenter
import com.khpi.classschedule.presentation.main.fragments.building.item.BuildingItemPresenter
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListPresenter
import com.khpi.classschedule.presentation.main.fragments.group.list.GroupListPresenter
import com.khpi.classschedule.presentation.main.fragments.schedule.general.item.GeneralItemPresenter
import com.khpi.classschedule.presentation.main.fragments.schedule.general.list.GeneralListPresenter
import com.khpi.classschedule.presentation.main.fragments.schedule.show.item.ScheduleItemPresenter
import com.khpi.classschedule.presentation.main.fragments.schedule.show.list.ScheduleListPresenter
import com.khpi.classschedule.presentation.main.fragments.task.action.TaskActionPresenter
import com.khpi.classschedule.presentation.main.fragments.task.item.TaskItemPresenter
import com.khpi.classschedule.presentation.main.fragments.task.list.TaskListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, ApiModule::class, NetworkModule::class))

interface AppComponent {
    fun inject(mainPresenter: MainPresenter)
    fun inject(buildingListPresenter: BuildingListPresenter)
    fun inject(buildingItemPresenter: BuildingItemPresenter)
    fun inject(buildingInfoPresenter: BuildingInfoPresenter)
    fun inject(facultyListPresenter: FacultyListPresenter)
    fun inject(groupListPresenter: GroupListPresenter)
    fun inject(scheduleListPresenter: ScheduleListPresenter)
    fun inject(scheduleGeneralPresenter: GeneralListPresenter)
    fun inject(generalItemPresenter: GeneralItemPresenter)
    fun inject(scheduleItemPresenter: ScheduleItemPresenter)
    fun inject(taskListPresenter: TaskListPresenter)
    fun inject(taskCreatePresenter: TaskActionPresenter)
    fun inject(taskItemPresenter: TaskItemPresenter) {

    }
}