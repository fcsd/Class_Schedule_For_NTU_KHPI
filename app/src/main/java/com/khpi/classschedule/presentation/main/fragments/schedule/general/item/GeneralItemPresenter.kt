package com.khpi.classschedule.presentation.main.fragments.schedule.general.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.config.MemoryRepository
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class GeneralItemPresenter : BasePresenter<GeneralItemView>() {

    //@formatter:off
    @Inject lateinit var memoryRepository: MemoryRepository
    @Inject lateinit var scheduleManager: ScheduleManager
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var scheduleInfo = mutableListOf<BaseModel>()

    private var scheduleFirstWeek: Schedule? = null
    private var scheduleSecondWeek: Schedule? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun setScheduleInfo(scheduleInfo: List<BaseModel>?) {
        scheduleInfo?.let {
            this.scheduleInfo = scheduleInfo.toMutableList()
            viewState.showScheduleInfo(this.scheduleInfo)
        }
    }

    fun onItemClick(item: BaseModel) {
        viewState.openScheduleScreen(item)
    }

    fun onRemoveClicked(adapterPosition: Int) {

        val itemInfo = scheduleInfo[adapterPosition]
        val itemId = itemInfo.id ?: return
        val type = itemInfo.scheduleType ?: return

        val prefix = getPrefixByType(type)
        val messageType = getMessageByType(type)

        memoryRepository.removeSchedule(prefix, itemId)

        scheduleInfo.remove(itemInfo)
        viewState.notifyDataSetChanged()
        viewState.showMessage("Розклад $messageType ${itemInfo.title} був видален успішно")
    }

    fun onRefreshClicked(adapterPosition: Int) {
        val itemInfo = scheduleInfo[adapterPosition]
        val itemId = itemInfo.id ?: return

        viewState.showProgressDialog()
        loadScheduleForWeeks(itemInfo, "Schedule", itemId)
        loadScheduleForWeeks(itemInfo, "Schedule2", itemId)
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
                    scheduleType = type)

            val prefix = getPrefixByType(type)
            val messageType = getMessageByType(type)

            memoryRepository.saveSchedule(prefix, id, scheduleFirstWeek, scheduleSecondWeek, scheduleInfo, isUpdate = true)

            viewState.dismissProgressDialog()
            viewState.showMessage("Розклад $messageType $name був оновлений успiшно!")
        }
    }
}