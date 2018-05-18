package com.khpi.classschedule.presentation.main.fragments.task.list


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.data.models.TaskSort
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.category.list.CategoryListFragment
import com.khpi.classschedule.presentation.main.fragments.task.action.TaskActionAlarmAdapter
import com.khpi.classschedule.presentation.main.fragments.task.action.TaskActionFragment
import com.khpi.classschedule.presentation.main.fragments.task.item.TaskItemFragment
import com.khpi.classschedule.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_task_list.*

class TaskListFragment : BaseFragment(), TaskListView {

    override var TAG = "TaskListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: TaskListPresenter
    //@formatter:on

    private lateinit var taskAdapter : TaskListAdapter

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

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.setRightFirstNavigationIcon(null)
    }


    override fun configureView() {
        (activity as? MainActivity)?.setToolbarTitle(getString(R.string.task))
        presenter.loadActiveTask()
    }

    override fun configureViewForAdding(resId: Int, action: () -> Unit) {
        recycler_task.setVisibility(false)
        layout_task_add.setVisibility(true)
        description_task_text.text = resources.getString(resId)
        layout_task_add.setOnClickListener { action() }
    }

    override fun showActiveTasks(tasks: MutableList<Task>, callback: TaskListPresenter) {
        val ctx = context?: return
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_add))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.onAddClicked() }

        if (tasks.size > 1) {
            (activity as? MainActivity)?.setRightFirstNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_sort))
            (activity as? MainActivity)?.setRightFirstClickListener { v -> openSortingPopup(v) }
        }

        taskAdapter = TaskListAdapter(tasks, callback, callback)
        recycler_task.layoutManager = LinearLayoutManager(context)
        recycler_task.adapter = taskAdapter
    }

    private fun openSortingPopup(view: View) {

        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.menu_task_sorting)

        popupMenu.setOnMenuItemClickListener { item ->

            when (item.itemId) {

                R.id.sort_date -> {
                    presenter.sortedBy(TaskSort.DATE)
                    true
                }
                R.id.sort_group -> {
                    presenter.sortedBy(TaskSort.GROUP)
                    true
                }
                R.id.sort_subject -> {
                    presenter.sortedBy(TaskSort.SUBJECT)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    override fun disableTaskNotification(task: Task) {
        val ctx = context ?: return
        val notificationIntent = Intent(ctx, TaskActionAlarmAdapter::class.java)
        val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(ctx, task.id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

    override fun openActionTaskScreen() {
        (activity as? MainActivity)?.replaceFragment(TaskActionFragment.newInstance(task = null, group = null, subject = null, type = null))
    }

    override fun openDetailTaskScreen(task: Task) {
        (activity as? MainActivity)?.replaceFragment(TaskItemFragment.newInstance(task.id))
    }

    override fun openAddScheduleScreen() {
        (activity as? MainActivity)?.replaceFragment(CategoryListFragment.newInstance())
    }

    override fun notifyDataSetChanged() {
        taskAdapter.notifyDataSetChanged()
    }
}
