package com.khpi.classschedule.presentation.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.R
import com.khpi.classschedule.presentation.base.BaseActivity
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.fragments.building.list.BuildingListFragment
import com.khpi.classschedule.presentation.main.fragments.schedule.general.ScheduleGeneralFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity(), MainView {

    //@formatter:off
    @InjectPresenter lateinit var presenter: MainPresenter
    //@formatter:on

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        setSupportActionBar(toolbar)
        btnToolbarBack.setOnClickListener { super.onBackPressed() }

        setVisibleViews(schedule_text, task_text, teacher_text, building_text)
        replaceFragment(ScheduleGeneralFragment.newInstance())

        schedule_fragment.setOnClickListener {
            setVisibleViews(schedule_text, task_text, teacher_text, building_text)
            replaceFragment(ScheduleGeneralFragment.newInstance(), true)
        }

        task_fragment.setOnClickListener {
            setVisibleViews(task_text, schedule_text, teacher_text, building_text)
            replaceFragment(BuildingListFragment.newInstance(), true)
        }

        teacher_fragment.setOnClickListener {
            setVisibleViews(teacher_text, schedule_text, task_text, building_text)
            replaceFragment(BuildingListFragment.newInstance(), true)
        }

        building_fragment.setOnClickListener {
            setVisibleViews(building_text, schedule_text, task_text, teacher_text)
            replaceFragment(BuildingListFragment.newInstance(), true)
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

    fun setRightSecondClickListener(function: (View) -> Unit) {
        btnToolbarRight2.setOnClickListener(function)
    }

    override fun dismissProgressDialog() {
        progress_bar.visibility = View.GONE
    }

    override fun showProgressDialog() {
        progress_bar.visibility = View.VISIBLE
    }
}
