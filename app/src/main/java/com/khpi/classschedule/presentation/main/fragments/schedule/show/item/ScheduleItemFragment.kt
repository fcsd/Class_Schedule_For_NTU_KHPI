package com.khpi.classschedule.presentation.main.fragments.schedule.show.item

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.building.item.BuildingItemFragment
import kotlinx.android.synthetic.main.fragment_schedule_item.*

class ScheduleItemFragment : BaseFragment(), ScheduleItemView {

    override var TAG = "ScheduleItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: ScheduleItemPresenter
    //@formatter:on

    private var schedule: List<ScheduleItem>? = null
    private var type: ScheduleType? = null

    companion object {
        fun newInstance(schedule: List<ScheduleItem>, type: ScheduleType): ScheduleItemFragment = ScheduleItemFragment().apply {
            this.schedule = schedule
            this.type = type
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_schedule_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val schedule = this.schedule ?: return
        val type = this.type ?: return
        presenter.prepareToShowSchedule(schedule, type)
    }

    override fun showSchedule(schedule: List<ScheduleItem>, callback: ScheduleItemPresenter) {
        val scheduleAdapter = ScheduleItemAdapter(schedule, callback)
        recycler_schedule.layoutManager = LinearLayoutManager(context)
        recycler_schedule.adapter = scheduleAdapter
    }

    override fun openBuildingScreen(shortName: String) {
        (activity as MainActivity).replaceFragment(BuildingItemFragment.newInstance(shortName))
    }
}
