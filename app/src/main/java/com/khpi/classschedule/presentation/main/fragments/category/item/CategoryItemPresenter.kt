package com.khpi.classschedule.presentation.main.fragments.category.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.R
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.config.TaskRepository
import com.khpi.classschedule.data.models.*
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.presentation.main.fragments.category.list.CategoryListPresenter
import com.khpi.classschedule.views.BasePropertyAdapter
import javax.inject.Inject

@InjectViewState
class CategoryItemPresenter : BasePresenter<CategoryItemView>(), CategoryItemAdapter.OnGeneralItemClickListener,
        BasePropertyAdapter.OnScheduleItemClickListener {

    //@formatter:off
    @Inject lateinit var scheduleRepository: ScheduleRepository
    @Inject lateinit var scheduleManager: ScheduleManager
    @Inject lateinit var taskRepository: TaskRepository
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var scheduleInfo = mutableListOf<BaseModel>()
    private var type: ScheduleType? = null
    private var listener: CategoryListPresenter? = null

    private var scheduleFirstWeek: Schedule? = null
    private var scheduleSecondWeek: Schedule? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun setScheduleInfo(scheduleInfo: List<BaseModel>?, type: ScheduleType?, listener: CategoryListPresenter?) {
        this.type = type
        this.listener = listener

        scheduleInfo?.let {
            this.scheduleInfo = it.toMutableList()
            this.scheduleInfo.forEach { info ->
                info.properties = mutableListOf()
                info.properties.add(Property("Оновити", R.drawable.ic_update, R.color.c_987ab6, PropertyType.UPDATE))
                info.properties.add(Property("Видалити", R.drawable.ic_remove, R.color.c_e4a66c, PropertyType.REMOVE))
            }

            viewState.showScheduleInfo(this.scheduleInfo, this)
        }
    }

    private fun refreshSchedule(adapterPosition: Int) {
        val itemInfo = scheduleInfo[adapterPosition]
        val itemId = itemInfo.id ?: return

        viewState.setCustomProgressBarVisibility(true)

        when(type) {
            ScheduleType.GROUP -> {
                loadScheduleForWeeks(itemInfo, "Schedule", itemId)
                loadScheduleForWeeks(itemInfo, "Schedule2", itemId)
            }
            ScheduleType.TEACHER -> {
                loadScheduleForWeeks(itemInfo, "ScheduleP", itemId)
                loadScheduleForWeeks(itemInfo, "Schedule2P", itemId)
            }
            ScheduleType.AUDITORY -> {
                throw NotImplementedError()
            }
        }
    }

    private fun removeSchedule(adapterPosition: Int) {

        val itemInfo = scheduleInfo[adapterPosition]
        val id = itemInfo.id ?: return
        val type = type ?: return
        val group = itemInfo.title ?: return
        val prefix = getPrefixByType(type)
        val messageType = getMessageByType(type)

        scheduleRepository.removeSchedule(prefix, id)

        scheduleInfo.removeAt(adapterPosition)

        if (itemInfo.isPinned) {
            setFirstSchedulePinned()
        }

        removeTasksByGroup(prefix, group)
        listener?.onRemovedSchedule()

        viewState.notifyDataSetChanged()
        viewState.showMessage("Розклад $messageType ${itemInfo.title} був видален успішно")
    }

    private fun removeTasksByGroup(prefix: String, group: String) {
        val tasks = taskRepository.getTasksByGroup(prefix, group)
        tasks?.forEach { task ->
            taskRepository.removeTask(prefix, task.id)
            viewState.disableTaskNotification(task)
        }
    }

    private fun setFirstSchedulePinned() {

        val schedules = scheduleRepository.getScheduleInfoByTypes(Constants.GROUP_PREFIX)
        schedules.addAll(scheduleRepository.getScheduleInfoByTypes(Constants.TEACHER_PREFIX))
        schedules.addAll(scheduleRepository.getScheduleInfoByTypes(Constants.AUDITORY_PREFIX))

        if (schedules.isNotEmpty()) {
            val newPinned = schedules.first()
            val id = newPinned.id ?: return
            val type = newPinned.scheduleType ?: return
            val prefix = getPrefixByType(type)
            newPinned.isPinned = true
            scheduleRepository.saveScheduleInfo(prefix, id, newPinned)
            viewState.requestChangePinToActivity(newPinned)
        } else {
            viewState.requestChangePinToActivity(null)
        }
    }

    private fun loadScheduleForWeeks(model: BaseModel, action: String, id: Int) {

        scheduleManager.getScheduleByWeekById(action, id, { schedule ->

            val monday = schedule.monday ?: return@getScheduleByWeekById
            val tuesday = schedule.tuesday ?: return@getScheduleByWeekById
            val wednesday = schedule.wednesday ?: return@getScheduleByWeekById
            val thursday = schedule.thursday ?: return@getScheduleByWeekById
            val friday = schedule.friday ?: return@getScheduleByWeekById

            if (action.contains("2"))  {
                scheduleSecondWeek = Schedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
            } else {
                scheduleFirstWeek = Schedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
            }

            synchronizeThreads(model, scheduleFirstWeek, scheduleSecondWeek)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.setCustomProgressBarVisibility(false)
            viewState.showError(errorMessage)
        })
    }

    private fun synchronizeThreads(baseModel: BaseModel?, scheduleFirstWeek: Schedule?, scheduleSecondWeek: Schedule?) {
        if (scheduleFirstWeek != null && scheduleSecondWeek != null) {

            val id = baseModel?.id ?: return
            val name = baseModel.title ?: return
            val type = baseModel.scheduleType ?: return

            val scheduleInfo = BaseModel(id = id,
                    parentName = baseModel.parentName,
                    title = name,
                    course = baseModel.course,
                    scheduleType = type,
                    isPinned = baseModel.isPinned)

            val prefix = getPrefixByType(type)
            val messageType = getMessageByType(type)

            scheduleRepository.saveSchedule(prefix, id, scheduleFirstWeek, scheduleSecondWeek, scheduleInfo, isUpdate = true)

            viewState.setCustomProgressBarVisibility(false)
            viewState.showMessage("Розклад $messageType $name був оновлений успiшно")
        }
    }

    override fun onItemClick(item: BaseModel) {
        viewState.openScheduleScreen(item)
    }

    override fun onAddClick() {
        val unwrappedType = type ?: return
        when(unwrappedType) {
            ScheduleType.GROUP -> viewState.openFacultyScreen(unwrappedType, Constants.FACULTY_FRAGMENT)
            ScheduleType.TEACHER -> viewState.openFacultyScreen(unwrappedType, Constants.FACULTY_FRAGMENT)
            ScheduleType.AUDITORY -> viewState.showMessage("Click: Auditory")
        }
    }

    override fun onPropertyClick(property: Property, adapterPosition: Int) {
        when(property.type) {
            PropertyType.UPDATE -> refreshSchedule(adapterPosition)
            PropertyType.REMOVE -> removeSchedule(adapterPosition)
            else -> return
        }
    }

    interface OnRemovedSchedule {
        fun onRemovedSchedule()
    }
}