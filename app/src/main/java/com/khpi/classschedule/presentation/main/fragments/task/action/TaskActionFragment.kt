package com.khpi.classschedule.presentation.main.fragments.task.action

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.utils.DateFormatter
import kotlinx.android.synthetic.main.fragment_task_create.*
import java.util.*
import android.text.Editable
import android.text.TextWatcher
import com.khpi.classschedule.data.models.Task
import android.app.PendingIntent
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.models.CoupleType
import com.khpi.classschedule.presentation.main.MainActivity

class TaskActionFragment : BaseFragment(), TaskActionView {

    override var TAG = "TaskActionFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: TaskActionPresenter
    //@formatter:on

    private lateinit var taskCreateAdapter : TaskActionAdapter
    private var task: Task? = null
    private var group: String? = null
    private var subject: String? = null
    private var type: String? = null
    private var prefix = ""

    companion object {
        fun newInstance(task: Task?, group: String?, subject: String?, type: String?): TaskActionFragment = TaskActionFragment().apply {
            this.task = task
            this.group = group
            this.subject = subject
            this.type = type
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_task_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val ctx = context?: return
        task?.let { (activity as? MainActivity)?.setToolbarTitle(getString(R.string.edit_task)) }
                ?: (activity as? MainActivity)?.setToolbarTitle(getString(R.string.create_task))

        (activity as? MainActivity)?.setRightSecondClickListener { presenter.saveTask() }
        name_content_text.setOnClickListener { presenter.prepareToShowGroup() }


        subject_content_text.setOnClickListener { presenter.prepareToShowSubject() }
        date_content_calendar.setOnClickListener { presenter.prepareToShowDatePicker() }

        detail_content_edit.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(description: CharSequence, start: Int, before: Int, count: Int) {
                presenter.onDescriptionChanged(description.toString())
            }
        })

        taskCreateAdapter = TaskActionAdapter(presenter)
        recycler_type.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        recycler_type.addItemDecoration(TaskActionDecorator(ctx))
        recycler_type.adapter = taskCreateAdapter

        presenter.loadInfoOfExistingTask(task)
        presenter.setGroupAndSubjectInfo(group, subject, type)
    }

    override fun updateTaskInfo(task: Task) {
        name_content_text.text = task.group
        subject_content_text.text = task.subject
        date_content_calendar.text = DateFormatter.getDateFromMillis(task.notificationTime)
        detail_content_edit.setText(task.description)
        taskCreateAdapter.changeSelectedItem(task.type.ordinal)
        (recycler_type.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(task.type.ordinal, 0)
    }

    override fun setGroupAndSubject(group: String, subject: String, type: CoupleType) {
        name_content_text.text = group
        subject_content_text.text = subject
        taskCreateAdapter.changeSelectedItem(type.ordinal)
        (recycler_type.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(type.ordinal, 0)
    }

    override fun showDatePicker(notificationTime: Long?) {

        val calendar = Calendar.getInstance()

        task?.let { calendar.timeInMillis = it.notificationTime + 1000 * 60 * 60 * 24 }

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            presenter.setNotificationTime(DateFormatter.getMillisFromPicker(year, month, day))
            date_content_calendar.text = DateFormatter.getDateFromPicker(year, month, day)
        }

        val picker = DatePickerDialog(context, R.style.DialogTheme, listener, currentYear, currentMonth, currentDay)
        picker.show()
    }

    override fun showPopupGroup(groupNames: List<String>) {
        val ctx = context ?: return
        val popupMenu = PopupMenu(ctx, name_content_text)

        groupNames.forEachIndexed { index, group ->
            popupMenu.menu.add(Menu.NONE, index, Menu.NONE, group )
        }

        popupMenu.setOnMenuItemClickListener { item ->
            subject_content_text.text = null
            subject_content_text.setTextColor(ContextCompat.getColor(ctx, R.color.c_000000))

            name_content_text.text = groupNames[item.itemId]
            presenter.onGroupSelected(groupNames[item.itemId])
            true
        }
        popupMenu.show()
    }

    override fun showPopupSubject(subjects: MutableList<String>) {
        val ctx = context ?: return
        val popupMenu = PopupMenu(ctx, name_content_text)

        subjects.forEachIndexed { index, group ->
            popupMenu.menu.add(Menu.NONE, index, Menu.NONE, group )
        }

        popupMenu.setOnMenuItemClickListener { item ->
            subject_content_text.text = null
            subject_content_text.setTextColor(ContextCompat.getColor(ctx, R.color.c_000000))

            subject_content_text.text = subjects[item.itemId]
            presenter.onSubjectSelected(subjects[item.itemId])
            true
        }
        popupMenu.show()
    }

    override fun showWarningEmptyGroup() {
        val ctx = context ?: return
        subject_content_text.setTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
        subject_content_text.text = ctx.resources.getString(R.string.subject_warning)
    }

    override fun highlightSelectedType(position: Int) {
        taskCreateAdapter.changeSelectedItem(position)
    }

    override fun setConfirmButtonEnabled(enabled: Boolean) {

        val ctx = context ?: return

        val icon = if (enabled) {
            ContextCompat.getDrawable(ctx, R.drawable.ic_apply)
        } else {
            ContextCompat.getDrawable(ctx, R.drawable.ic_apply_transparent)
        }

        (activity as? MainActivity)?.setRightSecondEnabled(enabled, icon)
    }

    override fun configureNotification(task: Task) {

        val ctx = context ?: return

        val notificationIntent = Intent(ctx, TaskActionAlarmAdapter::class.java)
                .putExtra(TaskActionAlarmAdapter.NOTIFICATION_ID, task.id)
                .putExtra(TaskActionAlarmAdapter.NOTIFICATION_TITLE, task.group)
                .putExtra(TaskActionAlarmAdapter.NOTIFICATION_MESSAGE, task.description)
                .putExtra(TaskActionAlarmAdapter.NOTIFICATION_CHANNEL, prefix)

        val pendingIntent = PendingIntent.getBroadcast(ctx, task.id,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, task.notificationTime, pendingIntent)
    }

    override fun showTitleByType(prefix: String) {
        this.prefix = prefix

        when(prefix) {
            Constants.GROUP_PREFIX -> {
                name_title_text.text = context?.resources?.getString(R.string.group)
                name_content_text.hint = context?.resources?.getString(R.string.choose_group)
            }
            Constants.TEACHER_PREFIX -> {
                name_title_text.text = context?.resources?.getString(R.string.teacher)
                name_content_text.hint = context?.resources?.getString(R.string.choose_teacher)
            }
        }
    }

    override fun closeScreen() {
        onBackPressed()
    }

}
