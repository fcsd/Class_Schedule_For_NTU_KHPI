package com.khpi.classschedule.presentation.main.fragments.category.search

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.views.BaseAdapter
import javax.inject.Inject

@InjectViewState
class CategorySearchPresenter : BasePresenter<CategorySearchView>(), BaseAdapter.OnBaseItemClickListener {

    //@formatter:off
    @Inject lateinit var scheduleManager: ScheduleManager
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var type: ScheduleType? = null
    private var foundedGroup = mutableListOf<BaseModel>()

    override fun onViewLoaded() {
        viewState.configureView()
        viewState.configureRecyclerView(foundedGroup, this)
    }

    fun setType(type: ScheduleType?) {
        this.type = type
    }

    fun onSearchEntered(searchedText: String) {
        if (searchedText.length < 2) {
            viewState.hideOrShowRecyclerView(false, true)
            return
        }

        viewState.hideOrShowRecyclerView(true, false)
        val unwrappedType = type ?: return
        when(unwrappedType) {
            ScheduleType.GROUP -> searchGroup("SearchGroups", searchedText)
            ScheduleType.TEACHER -> viewState.showMessage("Search Teacher")
            ScheduleType.AUDITORY -> viewState.showMessage("Search Auditory")
        }
    }

    private fun searchGroup(action: String, searchedText: String) {

        foundedGroup.clear()
        viewState.notifyDataSetChanged()
        viewState.setCustomProgressBarVisibility(true)
        scheduleManager.getSearchedList(action, searchedText, { bufferSchedules ->

            bufferSchedules.forEach { buffer ->
                val baseModel = BaseModel(title = buffer.gAbbr,
                        course = buffer.gKurs,
                        parentName = buffer.fAbbr,
                        id = buffer.gId,
                        scheduleType = type)
                foundedGroup.add(baseModel)
            }

            viewState.setCustomProgressBarVisibility(false)
            viewState.notifyDataSetChanged()
        }, {
            viewState.setCustomProgressBarVisibility(false)
            viewState.showError("При завантаженні даних трапилася помилка. Перевірте ваше підключення к мережі.")
        })
    }

    override fun onItemClick(item: BaseModel) {
        viewState.openScheduleScreen(item)
    }
}