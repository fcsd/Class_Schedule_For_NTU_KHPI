package com.khpi.classschedule.presentation.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.Constants
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.Screen
import com.khpi.classschedule.data.models.Task
import com.khpi.classschedule.presentation.base.BaseActivity
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.fragments.building.list.BuildingListFragment
import com.khpi.classschedule.presentation.main.fragments.category.list.CategoryListFragment
import com.khpi.classschedule.presentation.main.fragments.first.FirstFragment
import com.khpi.classschedule.presentation.main.fragments.paramerts.ParametersFragment
import com.khpi.classschedule.presentation.main.fragments.schedule.list.ScheduleListFragment
import com.khpi.classschedule.presentation.main.fragments.task.action.TaskActionAlarmAdapter
import com.khpi.classschedule.presentation.main.fragments.task.item.TaskItemFragment
import com.khpi.classschedule.presentation.main.fragments.task.list.TaskListFragment
import com.khpi.classschedule.utils.setVisibility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity(), MainView {

    //@formatter:off
    @InjectPresenter lateinit var presenter: MainPresenter
    //@formatter:on

    private var pinnedInfo: BaseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        setSupportActionBar(toolbar)
        btnToolbarBack.setOnClickListener { super.onBackPressed() }

        schedule_fragment.setOnClickListener {
            pinnedInfo?.let { openScheduleScreen(it) } ?: openCategoryScreen()
        }

        task_fragment.setOnClickListener {
            setVisibleViews(task_text, schedule_text, building_text, settings_text)
            replaceFragment(TaskListFragment.newInstance(), true)
        }

        building_fragment.setOnClickListener {
            setVisibleViews(building_text, schedule_text, task_text, settings_text)
            replaceFragment(BuildingListFragment.newInstance(), true)
        }

        settings_fragment.setOnClickListener {
            setVisibleViews(settings_text, schedule_text, task_text, building_text)
            replaceFragment(ParametersFragment.newInstance(), true)
        }
    }

    fun requestVisibleViews(type: Screen) {
        when (type) {
            Screen.SCHEDULE -> setVisibleViews(schedule_text, task_text, building_text, settings_text)
            Screen.TASK -> setVisibleViews(task_text, schedule_text, building_text, settings_text)
            Screen.BUILDING -> setVisibleViews(building_text, schedule_text, task_text, settings_text)
            Screen.SETTINGS -> setVisibleViews(settings_text, schedule_text, task_text, building_text)
        }
    }

    private fun setVisibleViews(visibleView: TextView, goneView1: TextView, goneView2: TextView, goneView3: TextView) {
        visibleView.visibility = View.VISIBLE
        goneView1.visibility = View.GONE
        goneView2.visibility = View.GONE
        goneView3.visibility = View.GONE
    }

    fun setToolbarTitle(title: String) {
        tvToolbarTitle.text = title
    }

    fun setToolbarTitleForSchedule(title: String) {
        tvToolbarTitle.setVisibility(false)

        if (btnToolbarBack.visibility == View.GONE) {
            tvToolbarTitleSchedule.maxWidth = resources.getDimension(R.dimen._200sdp).toInt()
        } else {
            tvToolbarTitleSchedule.maxWidth = resources.getDimension(R.dimen._160sdp).toInt()
        }

        tvToolbarTitleSchedule.setVisibility(true)
        tvToolbarTitleSchedule.text = title
    }

    fun removeToolbarTitleFunctionForSchedule() {
        tvToolbarTitleSchedule.setVisibility(false)
        tvToolbarTitle.setVisibility(true)
        tvToolbarTitleSchedule.setOnClickListener(null)
    }

    fun setToolbarTitleClickListener(function: (View) -> Unit) {
        tvToolbarTitleSchedule.setOnClickListener(function)
    }

    fun getToolbarTitleSchedule(): String {
        return tvToolbarTitleSchedule.text.toString()
    }

    fun getToolbarTitleScheduleView(): View {
        return tvToolbarTitleSchedule
    }

    fun replaceFragment(fragment: BaseFragment, isNeedClearBackStack: Boolean = false, isAnimate: Boolean = true) {
        replaceFragment(R.id.vgFrame, fragment, isNeedClearBackStack, isAnimate)
    }

    fun setRightFirstNavigationIcon(icon: Drawable?) {
        btnToolbarRight1.visibility = if (icon == null) View.GONE else View.VISIBLE
        btnToolbarRight1.setImageDrawable(icon)
    }

    fun setRightFirstClickListener(function: (View) -> Unit) {
        btnToolbarRight1.setOnClickListener(function)
    }

    fun setRightSecondNavigationIcon(icon: Drawable?) {
        btnToolbarRight2.visibility = if (icon == null) View.GONE else View.VISIBLE
        btnToolbarRight2.setImageDrawable(icon)
    }

    fun setRightSecondEnabled(enabled: Boolean, icon: Drawable?) {
        if (btnToolbarRight2.visibility == View.GONE) {
            btnToolbarRight2.setVisibility(true)
        }
        btnToolbarRight2.isClickable = enabled
        btnToolbarRight2.setImageDrawable(icon)
    }

    fun setRightSecondClickListener(function: (View) -> Unit) {
        btnToolbarRight2.setOnClickListener(function)
    }

    override fun openCategoryScreen()
    {
        setVisibleViews(schedule_text, task_text, building_text, settings_text)
        replaceFragment(CategoryListFragment.newInstance(), true)
    }

    override fun openFirstScreen() {
        replaceFragment(FirstFragment.newInstance(), true)
    }

    override fun openScheduleScreen(pinnedInfo: BaseModel) {

        this.pinnedInfo = pinnedInfo
        val taskId = intent.getIntExtra(Constants.REQUEST_OPEN_TASK_INFO, -1)

        if (taskId == -1) {
            val type = pinnedInfo.scheduleType ?: return
            setVisibleViews(schedule_text, task_text, building_text, settings_text)
            replaceFragment(ScheduleListFragment.newInstance(pinnedInfo, type), true)
        } else {
            setVisibleViews(task_text, schedule_text, building_text, settings_text)
            replaceFragment(TaskItemFragment.newInstance(taskId), true)
        }
    }

    override fun disableTaskNotification(task: Task) {
        val notificationIntent = Intent(this, TaskActionAlarmAdapter::class.java)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(this, task.id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

    fun changePin(newPinned: BaseModel?) {
        pinnedInfo = newPinned
    }

    override fun setCustomProgressBarVisibility(visibility: Boolean) {
        progress_bar.setVisibility(visibility)
    }

    fun setBackButtonVisibility(visibility: Boolean) {
        btnToolbarBack.setVisibility(visibility)
    }

    fun setNavigationVisibility(visibility: Boolean) {
        schedule_fragment.setVisibility(visibility)
        task_fragment.setVisibility(visibility)
        building_fragment.setVisibility(visibility)
        settings_fragment.setVisibility(visibility)
    }

    fun openCategory() {
        presenter.openCategory()
    }
}
