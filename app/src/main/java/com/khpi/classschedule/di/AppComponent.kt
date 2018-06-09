package com.khpi.classschedule.di

import com.khpi.classschedule.di.modules.ApiModule
import com.khpi.classschedule.di.modules.AppModule
import com.khpi.classschedule.di.modules.NetworkModule
import com.khpi.classschedule.presentation.main.MainPresenter
import com.khpi.classschedule.presentation.main.fragments.building.info.BuildingInfoPresenter
import com.khpi.classschedule.presentation.main.fragments.building.item.BuildingItemPresenter
import com.khpi.classschedule.presentation.main.fragments.building.list.BuildingListPresenter
import com.khpi.classschedule.presentation.main.fragments.category.item.CategoryItemPresenter
import com.khpi.classschedule.presentation.main.fragments.category.list.CategoryListPresenter
import com.khpi.classschedule.presentation.main.fragments.category.pin.CategoryPinPresenter
import com.khpi.classschedule.presentation.main.fragments.category.search.CategorySearchPresenter
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListPresenter
import com.khpi.classschedule.presentation.main.fragments.first.FirstPresenter
import com.khpi.classschedule.presentation.main.fragments.group.list.GroupListPresenter
import com.khpi.classschedule.presentation.main.fragments.paramerts.ParametersPresenter
import com.khpi.classschedule.presentation.main.fragments.schedule.item.ScheduleItemPresenter
import com.khpi.classschedule.presentation.main.fragments.schedule.list.ScheduleListPresenter
import com.khpi.classschedule.presentation.main.fragments.task.action.TaskActionAlarmAdapter
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
    fun inject(scheduleGeneralPresenter: CategoryListPresenter)
    fun inject(generalItemPresenter: CategoryItemPresenter)
    fun inject(scheduleItemPresenter: ScheduleItemPresenter)
    fun inject(taskListPresenter: TaskListPresenter)
    fun inject(taskCreatePresenter: TaskActionPresenter)
    fun inject(taskItemPresenter: TaskItemPresenter)
    fun inject(settingsPresenter: ParametersPresenter)
    fun inject(taskActionAlarmAdapter: TaskActionAlarmAdapter)
    fun inject(categoryPinPresenter: CategoryPinPresenter)
    fun inject(firstPresenter: FirstPresenter)
    fun inject(categorySearchPresenter: CategorySearchPresenter)
}