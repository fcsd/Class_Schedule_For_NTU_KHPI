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
import com.khpi.classschedule.data.SwipeHelper
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.building.item.BuildingItemFragment
import kotlinx.android.synthetic.main.fragment_general_item.*
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
        presenter.prepareToShowSchedule(schedule)
    }

    override fun showSchedule(schedule: List<ScheduleItem>, callback: ScheduleItemPresenter) {
        val ctx = context ?: return
        val scheduleAdapter = ScheduleItemAdapter(schedule, callback)
        recycler_schedule.layoutManager = LinearLayoutManager(context)
        recycler_schedule.adapter = scheduleAdapter

        object : SwipeHelper(ctx, recycler_schedule) {

            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder,
                                                   underlayButtons: MutableList<SwipeHelper.UnderlayButton>) {

                when (type) {
                    ScheduleType.GROUP -> {

                        val drawableRemove = ContextCompat.getDrawable(ctx, R.drawable.ic_content_delete) ?: return
                        val drawableRefresh = ContextCompat.getDrawable(ctx, R.drawable.ic_add_new_item) ?: return

                        underlayButtons.add(SwipeHelper.UnderlayButton(
                                resources.getString(R.string.map),
                                drawableToBitmap(drawableRemove),
                                ContextCompat.getColor(ctx, R.color.colorPrimary),
                                object : SwipeHelper.UnderlayButtonClickListener {
                                    override fun onClick(pos: Int) {
                                        presenter.loadBuilding(viewHolder.adapterPosition)
                                    }
                                }
                        ))

                        underlayButtons.add(SwipeHelper.UnderlayButton(
                                resources.getString(R.string.task),
                                drawableToBitmap(drawableRefresh),
                                ContextCompat.getColor(ctx, R.color.c_4bc173),
                                object : SwipeHelper.UnderlayButtonClickListener {
                                    override fun onClick(pos: Int) {
                                        showError("Tasks not implemented now")
                                    }
                                }
                        ))
                    }

                    ScheduleType.TEACHER -> {
                        //todo add teacher swipe
                    }

                    ScheduleType.AUDITORY -> {
                        //todo add auditory swipe
                    }
                }

            }
        }
    }

    override fun openBuildingScreen(shortName: String) {
        (activity as MainActivity).replaceFragment(BuildingItemFragment.newInstance(shortName))
    }
}