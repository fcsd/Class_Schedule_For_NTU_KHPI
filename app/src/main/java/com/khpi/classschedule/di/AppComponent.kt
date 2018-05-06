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
}