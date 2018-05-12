package com.khpi.classschedule.presentation.main.fragments.task.list


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
import com.khpi.classschedule.presentation.main.fragments.task.action.TaskActionFragment
import com.khpi.classschedule.presentation.main.fragments.task.item.TaskItemFragment
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
        val ctx = context?: return
        (activity as? MainActivity)?.setToolbarTitle(getString(R.string.task))
        (activity as? MainActivity)?.setRightFirstNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_sort))
        (activity as? MainActivity)?.setRightFirstClickListener { v -> openSortingPopup(v) }
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_add_new_item))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.onAddClicked() }
        presenter.loadActiveTask()
    }

    override fun showActiveTasks(tasks: MutableList<Task>, callback: TaskListPresenter) {
        taskAdapter = TaskListAdapter(tasks, callback)
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

    override fun openActionTaskScreen() {
        (activity as? MainActivity)?.replaceFragment(TaskActionFragment.newInstance(null))
    }

    override fun openDetailTaskScreen(task: Task) {
        (activity as? MainActivity)?.replaceFragment(TaskItemFragment.newInstance(task.id))
    }

    override fun notifyDataSetChanged() {
        taskAdapter.notifyDataSetChanged()
    }
}
