package com.khpi.classschedule.presentation.main.fragments.category.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.presentation.main.fragments.category.pin.CategoryPinAdapter
import javax.inject.Inject

@InjectViewState
class CategoryListPresenter : BasePresenter<CategoryListView>(), CategoryPinAdapter.OnCategoryPinItemClickListener {

    //@formatter:off
    @Inject lateinit var scheduleRepository: ScheduleRepository
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var infoGroups: MutableList<BaseModel> = mutableListOf()
    private var infoTeachers: MutableList<BaseModel> = mutableListOf()
    private var infoAuditories: MutableList<BaseModel> = mutableListOf()
    private var backupPinnedInfo: BaseModel? = null
    private var oldPinnedInfo: BaseModel? = null

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadSchedules() {
        infoGroups = scheduleRepository.getScheduleInfoByTypes(Constants.GROUP_PREFIX)
        infoTeachers = scheduleRepository.getScheduleInfoByTypes(Constants.TEACHER_PREFIX)
        infoAuditories = scheduleRepository.getScheduleInfoByTypes(Constants.AUDITORY_PREFIX)
        viewState.showSavedSchedulesInfo(infoGroups, infoTeachers, infoAuditories)
    }

    fun loadPinSchedulesInfo() {

        oldPinnedInfo = findPinnedInfoOrNull(infoGroups)
                ?: findPinnedInfoOrNull(infoTeachers)
                ?: findPinnedInfoOrNull(infoAuditories)
                ?: return

        backupPinnedInfo = oldPinnedInfo
        viewState.changeToolbarSecondButtonForPin()
        viewState.showPinSchedulesInfo(infoGroups, infoTeachers, infoAuditories, this)
    }

    private fun findPinnedInfoOrNull(infos: List<BaseModel>): BaseModel? {
        return infos.find { it.isPinned }
    }

    override fun onRadioChanged(newPinnedInfo: BaseModel) {
        val oldPinnedInfo = this.oldPinnedInfo ?: return

        when(oldPinnedInfo.scheduleType) {
            ScheduleType.GROUP -> infoGroups.find { it == oldPinnedInfo }?.isPinned = false
            ScheduleType.TEACHER -> infoTeachers.find { it == oldPinnedInfo }?.isPinned = false
            ScheduleType.AUDITORY -> infoAuditories.find { it == oldPinnedInfo }?.isPinned = false
        }

        when(newPinnedInfo.scheduleType) {
            ScheduleType.GROUP -> infoGroups.find { it == newPinnedInfo }?.isPinned = true
            ScheduleType.TEACHER -> infoTeachers.find { it == newPinnedInfo }?.isPinned = true
            ScheduleType.AUDITORY -> infoAuditories.find { it == newPinnedInfo }?.isPinned = true
        }

        this.oldPinnedInfo = newPinnedInfo
        viewState.notifyDataSetChanged()
    }

    fun confirmPinGroup() {
        val oldInfo = backupPinnedInfo ?: return
        val newInfo = oldPinnedInfo ?: return
        val oldType = oldInfo.scheduleType ?: return
        val newType = newInfo.scheduleType ?: return
        val oldPrefix = getPrefixByType(oldType)
        val newPrefix = getPrefixByType(newType)
        val oldId = oldInfo.id ?: return
        val newId = newInfo.id ?: return

        scheduleRepository.saveScheduleInfo(oldPrefix, oldId, oldInfo)
        scheduleRepository.saveScheduleInfo(newPrefix, newId, newInfo)
        viewState.changeToolbarSecondButtonForShow()
        viewState.showSavedSchedulesInfo(infoGroups, infoTeachers, infoAuditories)
        viewState.showMessage("Ви успішно закріпили новий розклад")
    }
}