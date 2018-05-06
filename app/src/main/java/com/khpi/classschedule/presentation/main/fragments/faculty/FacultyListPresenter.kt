package com.khpi.classschedule.presentation.main.fragments.faculty

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.views.BaseAdapter
import javax.inject.Inject

@InjectViewState
class FacultyListPresenter : BasePresenter<FacultyListView>(), BaseAdapter.OnBaseItemClickListener {

    @Inject lateinit var scheduleManager: ScheduleManager

    private var backupFaculties = listOf<BaseModel>()
    private var faculties = mutableListOf<BaseModel>()

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadFacultyList() {
        viewState.showProgressDialog()
        scheduleManager.getFacultyList( { faculties ->

            backupFaculties = faculties
            this.faculties = backupFaculties.toMutableList()

            viewState.dismissProgressDialog()
            viewState.onFacultyLoaded(this.faculties, this)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.dismissProgressDialog()
            viewState.showError(errorMessage)
        })
    }

    override fun onItemClick(item: BaseModel) {
        viewState.openGroupScreen(item)
    }

    fun onSearchEntered(searchFaculty: String) {
        faculties.clear()
        if (searchFaculty.isEmpty()) {
            faculties.addAll(backupFaculties)
        } else {
            val foundedFaculties = backupFaculties.filter { it.title?.startsWith(searchFaculty, true) == true }
            faculties.addAll(foundedFaculties)
        }
        viewState.notifyDataSetChanged()
    }
}