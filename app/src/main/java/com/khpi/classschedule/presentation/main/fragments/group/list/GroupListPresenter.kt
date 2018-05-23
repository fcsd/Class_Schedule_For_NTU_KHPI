package com.khpi.classschedule.presentation.main.fragments.group.list

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.business.ScheduleManager
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class GroupListPresenter : BasePresenter<GroupListView>() {

    //@formatter:off
    @Inject lateinit var scheduleManager: ScheduleManager
    //@formatter:on

    init {
        injector().inject(this)
    }

    private var type: ScheduleType? = null
    private var currentTab = 0

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun setType(type: ScheduleType?) {
        this.type = type
    }

    fun loadGroupListById(facultyId: Int) {

        viewState.setCustomProgressBarVisibility(true)
        scheduleManager.getActionListById("GroupByFacultyList", facultyId, { groups ->

            when (type) {
                ScheduleType.GROUP -> replaceGroupByCourse(groups)
                ScheduleType.TEACHER -> throw NotImplementedError()
                ScheduleType.AUDITORY -> throw NotImplementedError()
            }

        }, {
            val errorMessage = it ?: "Unknown error"
            viewState.setCustomProgressBarVisibility(false)
            viewState.showError(errorMessage)
        })
    }

    private fun replaceGroupByCourse(groups: ArrayList<BaseModel>) {
        val groupsByFirstCourse = groups.filter { it.course == 1 }
        val groupsBySecondCourse = groups.filter { it.course == 2 }
        val groupsByThirdCourse = groups.filter { it.course == 3 }
        val groupsByFourthCourse = groups.filter { it.course == 4 }
        val groupsByFifthCourse = groups.filter { it.course == 5 }
        val groupsBySixthCourse = groups.filter { it.course == 6 }
        viewState.setCustomProgressBarVisibility(false)
        viewState.showGroupsByCourse(groupsByFirstCourse, groupsBySecondCourse, groupsByThirdCourse,
                groupsByFourthCourse, groupsByFifthCourse, groupsBySixthCourse, ScheduleType.GROUP, currentTab)
    }

    fun setCurrentItem(position: Int) {
        currentTab = position
    }
}