package com.khpi.classschedule.presentation.main.fragments.schedule.general.item


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.schedule.show.list.ScheduleListFragment
import kotlinx.android.synthetic.main.fragment_general_item.*


class GeneralItemFragment : BaseFragment(), GeneralItemView {

    override var TAG = "GeneralItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: GeneralItemPresenter
    //@formatter:on

    private var scheduleInfo: List<BaseModel>? = null
    private lateinit var generalAdapter : GeneralItemAdapter

    companion object {
        fun newInstance(scheduleInfo: List<BaseModel>): GeneralItemFragment = GeneralItemFragment().apply {
            this.scheduleInfo = scheduleInfo
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

    override fun showScheduleInfo(scheduleInfo: List<BaseModel>) {
        val ctx = context ?: return
        if (scheduleInfo.isEmpty()) {
            recycler_general.visibility = View.GONE
            general_no_items.visibility = View.VISIBLE
        } else {
            generalAdapter = GeneralItemAdapter(scheduleInfo, presenter)
            recycler_general.layoutManager = LinearLayoutManager(ctx)
            recycler_general.adapter = generalAdapter
        }
    }

    override fun openScheduleScreen(baseSchedule: BaseModel) {
        baseSchedule.scheduleType?.let { (activity as? MainActivity)?.replaceFragment(
                ScheduleListFragment.newInstance(baseSchedule, it)) }
    }

    override fun notifyDataSetChanged() {
        generalAdapter.notifyDataSetChanged()
    }
}
