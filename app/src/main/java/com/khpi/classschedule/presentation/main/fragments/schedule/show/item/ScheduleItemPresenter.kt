package com.khpi.classschedule.presentation.main.fragments.schedule.show.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.R
import com.khpi.classschedule.business.BuildingManager
import com.khpi.classschedule.data.config.TaskRepository
import com.khpi.classschedule.data.models.Property
import com.khpi.classschedule.data.models.PropertyType
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.views.BasePropertyAdapter
import javax.inject.Inject

@InjectViewState
class ScheduleItemPresenter : BasePresenter<ScheduleItemView>(), BasePropertyAdapter.OnScheduleItemClickListener {

    //@formatter:off
    @Inject lateinit var buildingManager: BuildingManager
    @Inject lateinit var taskRepository: TaskRepository
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var schedule: List<ScheduleItem>? = null
    private var group: String? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun prepareToShowSchedule(schedule: List<ScheduleItem>, type: ScheduleType, group: String) {

        this.group = group

        val prefix = getPrefixByType(type)
        val tasks = taskRepository.getTasksByGroup(prefix, group)

        schedule.forEach { couple ->
            val subject = couple.name ?: return@forEach
            val tasksBySubject = tasks?.filter { it.subject == subject }
            couple.properties = mutableListOf()

            tasksBySubject?.let {
                if (it.isNotEmpty()) {
                    couple.properties.add(Property("Завдання", R.drawable.ic_task_blue, PropertyType.TASK_SHOW))
                } else {
                    couple.properties.add(Property("Завдання", R.drawable.ic_add_black, PropertyType.TASK_ADD))
                }
            }
            couple.properties.add(Property("Мапа", R.drawable.ic_building_green, PropertyType.BUILDING))
        }

        this.schedule = schedule
        this.schedule?.let { viewState.showSchedule(it, this) }
    }

    override fun onPropertyClick(property: Property, adapterPosition: Int) {
        when(property.type) {
            PropertyType.TASK_ADD -> {
                val unwrappedGroup = group ?: return
                val unwrappedSubject = schedule?.get(adapterPosition)?.name ?: return
                val unwrappedType = schedule?.get(adapterPosition)?.type ?: return
                viewState.openTaskAddScreen(unwrappedGroup, unwrappedSubject, unwrappedType)
            }
            PropertyType.TASK_SHOW -> viewState.showMessage("Show task!")
            PropertyType.BUILDING -> loadBuilding(adapterPosition)
            else -> return
        }
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