package com.khpi.classschedule.presentation.main.fragments.task.item

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.task.action.TaskActionFragment
import com.khpi.classschedule.utils.DateFormatter
import kotlinx.android.synthetic.main.fragment_task_item.*


class TaskItemFragment : BaseFragment(), TaskItemView {

    override var TAG = "TaskItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: TaskItemPresenter
    //@formatter:on

    private var taskId: Int? = null

    companion object {
        fun newInstance(taskId: Int): TaskItemFragment = TaskItemFragment().apply{
            this.taskId = taskId
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_item, container, false)
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
        (activity as? MainActivity)?.setToolbarTitle(getString(R.string.task_detail))
        (activity as? MainActivity)?.setRightFirstNavigationIcon(null)
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_content_edit))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.onEditClicked() }
        presenter.loadTaskInfo(taskId)
    }

    override fun showTask(task: Task) {
        description_content_text.text = task.description
        subject_content_text.text = task.subject
        time_content_text.text = DateFormatter.getDateFromMillis(task.notificationTime)
        type_content_text.text = task.type.title
    }

    override fun openActionTaskScreen(task: Task) {
        (activity as? MainActivity)?.replaceFragment(TaskActionFragment.newInstance(task = task, group = null, subject = null, type = null))
    }
}
