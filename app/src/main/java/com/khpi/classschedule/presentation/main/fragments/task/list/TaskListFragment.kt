package com.khpi.classschedule.presentation.main.fragments.task.list


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListFragment
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListPresenter
import com.khpi.classschedule.presentation.main.fragments.task.create.TaskCreateFragment

class TaskListFragment : BaseFragment(), TaskListView {

    override var TAG = "TaskListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: TaskListPresenter
    //@formatter:on

    companion object {
        fun newInstance(): TaskListFragment = TaskListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val ctx = context?: return
        (activity as? MainActivity)?.setToolbarTitle(getString(R.string.task))
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_add_new_item))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.onAddClicked() }
        presenter.loadActiveTask()
    }

    override fun openCreateTaskScreen() {
        (activity as? MainActivity)?.replaceFragment(TaskCreateFragment.newInstance(null))
    }


}
