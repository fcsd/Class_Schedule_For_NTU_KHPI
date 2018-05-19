package com.khpi.classschedule.presentation.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.Constants
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseActivity
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.fragments.building.list.BuildingListFragment
import com.khpi.classschedule.presentation.main.fragments.category.list.CategoryListFragment
import com.khpi.classschedule.presentation.main.fragments.paramerts.ParametersFragment
import com.khpi.classschedule.presentation.main.fragments.schedule.list.ScheduleListFragment
import com.khpi.classschedule.presentation.main.fragments.task.item.TaskItemFragment
import com.khpi.classschedule.presentation.main.fragments.task.list.TaskListFragment
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
        tvToolbarTitle.text = title
        tvToolbarTitle.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down_white,0)
    }

    fun removeToolbarTitleFunctionForSchedule() {
        tvToolbarTitle.setOnClickListener(null)
        tvToolbarTitle.setCompoundDrawablesWithIntrinsicBounds(0,0, 0,0)
    }

    fun setToolbarTitleClickListener(function: (View) -> Unit) {
        tvToolbarTitle.setOnClickListener(function)
    }

    fun getToolbarTitle(): String {
        return tvToolbarTitle.text.toString()
    }

    fun getToolbarTitleView(): View {
        return tvToolbarTitle
    }

    fun replaceFragment(fragment: BaseFragment, isNeedClearBackStack: Boolean = false) {
        replaceFragment(R.id.vgFrame, fragment, isNeedClearBackStack)
    }

    fun setLeftClickListener(function: (View) -> Unit) {
        btnToolbarBack.setOnClickListener(function)
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
        btnToolbarRight2.isClickable = enabled
        btnToolbarRight2.setImageDrawable(icon)
    }

    fun setRightSecondClickListener(function: (View) -> Unit) {
        btnToolbarRight2.setOnClickListener(function)
    }

    override fun dismissProgressDialog() {
        progress_bar.visibility = View.GONE
    }

    override fun showProgressDialog() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun openCategoryScreen() {
        replaceFragment(CategoryListFragment.newInstance(), true)
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

}
