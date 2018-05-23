package com.khpi.classschedule.presentation.main.fragments.schedule.item

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.building.item.BuildingItemFragment
import com.khpi.classschedule.presentation.main.fragments.task.action.TaskActionFragment
import com.khpi.classschedule.presentation.main.fragments.task.item.TaskItemFragment
import com.khpi.classschedule.presentation.main.fragments.task.list.TaskListFragment
import kotlinx.android.synthetic.main.fragment_schedule_item.*

class ScheduleItemFragment : BaseFragment(), ScheduleItemView {

    override var TAG = "ScheduleItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: ScheduleItemPresenter
    //@formatter:on

    private var schedule: List<ScheduleItem>? = null
    private var type: ScheduleType? = null

    companion object {
        fun newInstance(schedule: List<ScheduleItem>, type: ScheduleType): ScheduleItemFragment = ScheduleItemFragment().apply {
            this.schedule = schedule
            this.type = type
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_schedule_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val schedule = this.schedule ?: return
        val type = this.type ?: return
        val toolbarTitle = (activity as? MainActivity)?.getToolbarTitleSchedule() ?: return
        presenter.prepareToShowSchedule(schedule, type, toolbarTitle)
    }

    override fun showSchedule(schedule: List<ScheduleItem>, callback: ScheduleItemPresenter) {
        val scheduleAdapter = ScheduleItemAdapter(schedule, callback)
        recycler_schedule.layoutManager = LinearLayoutManager(context)
        recycler_schedule.adapter = scheduleAdapter
    }

    override fun openBuildingScreen(shortName: String) {
        (activity as MainActivity).replaceFragment(BuildingItemFragment.newInstance(shortName))
    }

    override fun openTaskAddScreen(group: String, subject: String, type: String) {
        (activity as MainActivity).replaceFragment(TaskActionFragment.newInstance(task = null, group = group, subject = subject, type = type))
    }

    override fun openTaskDetailScreen(taskId: Int) {
        (activity as MainActivity).replaceFragment(TaskItemFragment.newInstance(taskId))
    }

    override fun openTaskListScreen(tasksBySubject: List<Task>) {
        (activity as MainActivity).replaceFragment(TaskListFragment.newInstance(tasksBySubject))
    }


}
