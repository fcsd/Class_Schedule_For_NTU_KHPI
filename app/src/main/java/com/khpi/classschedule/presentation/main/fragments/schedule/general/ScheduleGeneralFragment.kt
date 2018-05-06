package com.khpi.classschedule.presentation.main.fragments.schedule.general


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.building.item.BuildingItemFragment
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListFragment

class ScheduleGeneralFragment : BaseFragment(), ScheduleGeneralView {

    override var TAG = "ScheduleGeneralFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: ScheduleGeneralPresenter
    //@formatter:on

    companion object {
        fun newInstance(): ScheduleGeneralFragment = ScheduleGeneralFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_schedule_general, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setToolbarTitle(resources.getString(R.string.my_groups))
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_add_new_item))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.onAddClicked() }
    }

    override fun opedFacultyScreen() {
        (activity as? MainActivity)?.replaceFragment(FacultyListFragment.newInstance())
    }
}
