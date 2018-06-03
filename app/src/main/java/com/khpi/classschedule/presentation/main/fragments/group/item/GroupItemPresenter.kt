package com.khpi.classschedule.presentation.main.fragments.group.item

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.views.BaseAdapter

@InjectViewState
class GroupItemPresenter : BasePresenter<GroupItemView>(), BaseAdapter.OnBaseItemClickListener {

    private var groups = mutableListOf<BaseModel>()
    private var backupGroups = listOf<BaseModel>()

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun prepareToShowGroups(groups: List<BaseModel>) {
        this.backupGroups = groups
        this.groups = backupGroups.toMutableList()
        viewState.showGroups(this.groups, this)
    }

    override fun onItemClick(item: BaseModel) {
        viewState.openScheduleScreen(item)
    }

    fun onSearchEntered(searchGroup: String) {
        groups.clear()
        if (searchGroup.isEmpty()) {
            groups.addAll(backupGroups)
        } else {
            val foundedFaculties = backupGroups.filter { it.title?.startsWith(searchGroup, true) == true }
            groups.addAll(foundedFaculties)
        }
        viewState.notifyDataSetChanged()
    }
}