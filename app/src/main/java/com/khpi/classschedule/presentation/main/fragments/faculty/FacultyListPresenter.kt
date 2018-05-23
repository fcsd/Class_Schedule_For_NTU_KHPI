package com.khpi.classschedule.presentation.main.fragments.faculty

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BasePresenter
import com.khpi.classschedule.views.BaseAdapter
import javax.inject.Inject

@InjectViewState
class FacultyListPresenter : BasePresenter<FacultyListView>(), BaseAdapter.OnBaseItemClickListener {

    @Inject lateinit var scheduleManager: ScheduleManager

    private var type: ScheduleType? = null
    private var tag: String? = null
    private var backupFaculties = listOf<BaseModel>()
    private var faculties = mutableListOf<BaseModel>()

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun manageActionByTypeAndTag(type: ScheduleType?, tag: String, id: Int?) {

        this.tag = tag
        this.type = type

        when(type) {
            ScheduleType.GROUP -> loadFacultyList()
            ScheduleType.TEACHER -> {
                when (tag) {
                    Constants.FACULTY_FRAGMENT -> loadFacultyList()
                    Constants.DEPARTMENT_FRAGMENT -> loadActionListByFacultyId("DeptsByFacultyP", id)
                    Constants.TEACHER_FRAGMENT -> loadActionListByFacultyId("PrepodListByDeptP", id)
                }
            }
            ScheduleType.AUDITORY -> throw NotImplementedError()
        }
    }

    private fun loadFacultyList() {
        viewState.setCustomProgressBarVisibility(true)
        scheduleManager.getFacultyList( { faculties ->

            backupFaculties = faculties
            this.faculties = backupFaculties.toMutableList()

            viewState.setCustomProgressBarVisibility(false)
            viewState.onFacultyLoaded(this.faculties, this)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.setCustomProgressBarVisibility(false)
            viewState.showError(errorMessage)
        })
    }

    private fun loadActionListByFacultyId(action: String, id: Int?) {
        val unwrappedId = id ?: return

        viewState.setCustomProgressBarVisibility(true)
        scheduleManager.getActionListById(action, unwrappedId, { faculties ->

            backupFaculties = faculties
            this.faculties = backupFaculties.toMutableList()

            viewState.setCustomProgressBarVisibility(false)
            viewState.onFacultyLoaded(this.faculties, this)

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.setCustomProgressBarVisibility(false)
            viewState.showError(errorMessage)
        })
    }

    override fun onItemClick(item: BaseModel) {
        when (type) {
            ScheduleType.GROUP -> viewState.openGroupScreen(item)
            ScheduleType.TEACHER -> {
                when (tag) {
                    Constants.FACULTY_FRAGMENT -> viewState.reopenCurrentScreen(item, Constants.DEPARTMENT_FRAGMENT)
                    Constants.DEPARTMENT_FRAGMENT -> viewState.reopenCurrentScreen(item, Constants.TEACHER_FRAGMENT)
                    Constants.TEACHER_FRAGMENT -> viewState.openScheduleScreen(item)
                }
            }
            ScheduleType.AUDITORY -> throw NotImplementedError()
        }
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