package com.khpi.classschedule.presentation.main.fragments.schedule.general.item


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseSchedule
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListFragment
import com.khpi.classschedule.presentation.main.fragments.schedule.list.ScheduleListFragment
import kotlinx.android.synthetic.main.fragment_general_item.*


class GeneralItemFragment : BaseFragment(), GeneralItemView {

    override var TAG = "GeneralItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: GeneralItemPresenter
    //@formatter:on

    private var scheduleInfo: List<BaseSchedule>? = null
    private var type: ScheduleType? = null

    companion object {
        fun newInstance(scheduleInfo: List<BaseSchedule>, type: ScheduleType): GeneralItemFragment = GeneralItemFragment().apply {
            this.scheduleInfo = scheduleInfo
            this.type = type
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_general_item, container, false)
    }

    override fun configureView() {
        presenter.setScheduleInfo(scheduleInfo)
    }

    override fun showScheduleInfo(scheduleInfo: List<BaseSchedule>) {
        val generalAdapter = GeneralItemAdapter(scheduleInfo, presenter)
        recycler_general.layoutManager = LinearLayoutManager(context)
        recycler_general.adapter = generalAdapter
    }

    override fun openFacultyScreen() {
        (activity as? MainActivity)?.replaceFragment(FacultyListFragment.newInstance(type))
    }

    override fun openScheduleScreen(baseSchedule: BaseSchedule) {
        (activity as? MainActivity)?.replaceFragment(ScheduleListFragment.newInstance(baseSchedule))
    }
}
