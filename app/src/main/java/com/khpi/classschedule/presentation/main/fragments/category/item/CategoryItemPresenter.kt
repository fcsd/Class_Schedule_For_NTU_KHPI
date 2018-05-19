package com.khpi.classschedule.presentation.main.fragments.category.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.R
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.models.*
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.views.BasePropertyAdapter
import javax.inject.Inject

@InjectViewState
class CategoryItemPresenter : BasePresenter<CategoryItemView>(), CategoryItemAdapter.OnGeneralItemClickListener,
        BasePropertyAdapter.OnScheduleItemClickListener {

    //@formatter:off
    @Inject lateinit var scheduleRepository: ScheduleRepository
    @Inject lateinit var scheduleManager: ScheduleManager
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var scheduleInfo = mutableListOf<BaseModel>()
    private var type: ScheduleType? = null

    private var scheduleFirstWeek: Schedule? = null
    private var scheduleSecondWeek: Schedule? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun setScheduleInfo(scheduleInfo: List<BaseModel>?, type: ScheduleType?) {
        this.type = type

        scheduleInfo?.let {
            this.scheduleInfo = it.toMutableList()
            this.scheduleInfo.forEach { info ->
                info.properties = mutableListOf()
                info.properties.add(Property("Оновити", R.drawable.ic_update, PropertyType.UPDATE))
                info.properties.add(Property("Видалити", R.drawable.ic_remove_orange, PropertyType.REMOVE))
            }

            viewState.showScheduleInfo(this.scheduleInfo, this)
        }
    }

    private fun refreshSchedule(adapterPosition: Int) {
        val itemInfo = scheduleInfo[adapterPosition]
        val itemId = itemInfo.id ?: return

        viewState.showProgressDialog()
        loadScheduleForWeeks(itemInfo, "Schedule", itemId)
        loadScheduleForWeeks(itemInfo, "Schedule2", itemId)
    }

    private fun removeSchedule(adapterPosition: Int) {

        val itemInfo = scheduleInfo[adapterPosition]
        val itemId = itemInfo.id ?: return
        val type = type ?: return
        val prefix = getPrefixByType(type)
        val messageType = getMessageByType(type)

        scheduleRepository.removeSchedule(prefix, itemId)

        scheduleInfo.removeAt(adapterPosition)
        viewState.notifyDataSetChanged()
        viewState.showMessage("Розклад $messageType ${itemInfo.title} був видален успішно")
    }

    private fun loadScheduleForWeeks(model: BaseModel, week: String, id: Int) {

        scheduleManager.getScheduleByWeekById(week, id, { schedule ->

            val monday = schedule.monday ?: return@getScheduleByWeekById
            val tuesday = schedule.tuesday ?: return@getScheduleByWeekById
            val wednesday = schedule.wednesday ?: return@getScheduleByWeekById
            val thursday = schedule.thursday ?: return@getScheduleByWeekById
            val friday = schedule.friday ?: return@getScheduleByWeekById

            when (week) {
                "Schedule" -> scheduleFirstWeek = Schedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
                "Schedule2" -> scheduleSecondWeek = Schedule(
                        setNormalFormForSchedule(monday),
                        setNormalFormForSchedule(tuesday),
                        setNormalFormForSchedule(wednesday),
                        setNormalFormForSchedule(thursday),
                        setNormalFormForSchedule(friday))
            }

            synchronizeThreads(model, scheduleFirstWeek, scheduleSecondWeek)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.dismissProgressDialog()
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

            viewState.dismissProgressDialog()
            viewState.showMessage("Розклад $messageType $name був оновлений успiшно")
        }
    }

    override fun onItemClick(item: BaseModel) {
        viewState.openScheduleScreen(item)
    }

    override fun onAddClick() {
        val unwrappedType = type ?: return
        when(unwrappedType) {
            ScheduleType.GROUP -> viewState.openFacultyScreen(unwrappedType)
            ScheduleType.TEACHER -> viewState.showMessage("Click: Teacher")
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
}