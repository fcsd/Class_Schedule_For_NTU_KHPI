package com.khpi.classschedule.presentation.main.fragments.schedule.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.BaseSchedule
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.schedule.item.ScheduleItemFragment
import com.khpi.classschedule.views.BasePagerAdapter
import kotlinx.android.synthetic.main.fragment_schedule_list.*


class ScheduleListFragment : BaseFragment(), ScheduleListView {

    override var TAG = "ScheduleListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: ScheduleListPresenter
    //@formatter:on

    private var group: BaseSchedule? = null

    companion object {
        fun newInstance(group: BaseSchedule): ScheduleListFragment = ScheduleListFragment().apply {
            this.group = group
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_schedule_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val groupName = this.group?.title ?: return
        (activity as? MainActivity)?.setToolbarTitle(groupName)
        group?.let { presenter.loadScheduleById(it) }
    }

    override fun showSchedule(schedule: Schedule) {
        val adapter = BasePagerAdapter(childFragmentManager)

        val monday = ScheduleItemFragment.newInstance(schedule.monday)
        val tuesday = ScheduleItemFragment.newInstance(schedule.tuesday)
        val wednesday = ScheduleItemFragment.newInstance(schedule.wednesday)
        val thursday = ScheduleItemFragment.newInstance(schedule.thursday)
        val friday = ScheduleItemFragment.newInstance(schedule.friday)

        adapter.addFragment(monday, getString(R.string.monday))
        adapter.addFragment(tuesday, getString(R.string.tuesday))
        adapter.addFragment(wednesday, getString(R.string.wednesday))
        adapter.addFragment(thursday, getString(R.string.thursday))
        adapter.addFragment(friday, getString(R.string.friday))

        schedule_view_pager.adapter = adapter
        schedule_tab.visibility = View.VISIBLE
        schedule_tab.setupWithViewPager(schedule_view_pager)
    }
}
