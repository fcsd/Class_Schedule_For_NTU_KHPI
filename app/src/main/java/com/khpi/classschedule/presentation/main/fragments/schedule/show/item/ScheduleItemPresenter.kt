package com.khpi.classschedule.presentation.main.fragments.schedule.show.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.BuildingManager
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ScheduleItemPresenter : BasePresenter<ScheduleItemView>(), ScheduleItemAdapter.OnScheduleItemClickListener {

    //@formatter:off
    @Inject lateinit var buildingManager: BuildingManager
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var schedule: List<ScheduleItem>? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun prepareToShowSchedule(schedule: List<ScheduleItem>) {
        this.schedule = schedule
        this.schedule?.let { viewState.showSchedule(it, this) }

    }

    override fun onItemClick(item: ScheduleItem) {

    }

    fun loadBuilding(adapterPosition: Int) {
        val couple = schedule?.get(adapterPosition) ?: return
        val auditory = couple.auditory ?: return
        val splitArray = auditory.split(" ")
        val coupleShortName = splitArray[splitArray.size - 1]
        val buildings = buildingManager.getAllShortBuildings()
        val shortName = buildings.find { it.shortName == coupleShortName }?.shortName
        shortName?.let { viewState.openBuildingScreen(it) } ?: viewState.showError("Неможливо знайти корпус")
    }
}