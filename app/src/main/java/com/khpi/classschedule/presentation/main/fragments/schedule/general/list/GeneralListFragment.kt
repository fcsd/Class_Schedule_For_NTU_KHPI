package com.khpi.classschedule.presentation.main.fragments.schedule.general.list


import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.schedule.general.item.GeneralItemFragment
import com.khpi.classschedule.views.BasePagerAdapter
import kotlinx.android.synthetic.main.fragment_general_list.*
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListFragment


class GeneralListFragment : BaseFragment(), GeneralListView {

    override var TAG = "GeneralListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: GeneralListPresenter
    //@formatter:on

    companion object {
        fun newInstance(): GeneralListFragment = GeneralListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_general_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.setRightSecondNavigationIcon(null)
    }

    override fun configureView() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_add))
        (activity as? MainActivity)?.setRightSecondClickListener { }

        (activity as? MainActivity)?.setToolbarTitle(resources.getString(R.string.my_groups))
        presenter.loadSchedules()
    }

    override fun showSavedSchedulesInfo(infoGroups: MutableList<BaseModel>,
                                        infoTeachers: MutableList<BaseModel>,
                                        infoAuditories: MutableList<BaseModel>) {

        val adapter = BasePagerAdapter(childFragmentManager)

        val groups = GeneralItemFragment.newInstance(infoGroups, ScheduleType.GROUP)
        val teachers = GeneralItemFragment.newInstance(infoTeachers, ScheduleType.TEACHER)
        val auditories = GeneralItemFragment.newInstance(infoAuditories, ScheduleType.AUDITORY)

        adapter.addFragment(groups, getString(R.string.groups))
        adapter.addFragment(teachers, getString(R.string.teachers))
        adapter.addFragment(auditories, getString(R.string.auditories))

        general_view_pager.adapter = adapter
        general_tab.visibility = View.VISIBLE
        general_tab.setupWithViewPager(general_view_pager)
    }
}
