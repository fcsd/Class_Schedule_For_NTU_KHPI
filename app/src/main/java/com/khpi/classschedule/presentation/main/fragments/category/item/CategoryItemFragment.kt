package com.khpi.classschedule.presentation.main.fragments.category.item

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.category.list.CategoryListPresenter
import com.khpi.classschedule.presentation.main.fragments.faculty.FacultyListFragment
import com.khpi.classschedule.presentation.main.fragments.schedule.list.ScheduleListFragment
import com.khpi.classschedule.utils.setVisibility
import kotlinx.android.synthetic.main.fragment_category_item.*

class CategoryItemFragment : BaseFragment(), CategoryItemView {

    override var TAG = "CategoryItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: CategoryItemPresenter
    //@formatter:on

    private var scheduleInfo: List<BaseModel>? = null
    private var type: ScheduleType? = null
    private var listener: CategoryListPresenter? = null
    private lateinit var generalAdapter: CategoryItemAdapter

    companion object {
        fun newInstance(scheduleInfo: List<BaseModel>, type: ScheduleType, listener: CategoryListPresenter):
                CategoryItemFragment = CategoryItemFragment().apply {
            this.listener = listener
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
        return inflater.inflate(R.layout.fragment_category_item, container, false)
    }

    override fun configureView() {
        presenter.setScheduleInfo(scheduleInfo, type, listener)
    }

    override fun showScheduleInfo(scheduleInfo: List<BaseModel>, callback: CategoryItemPresenter) {

        if (scheduleInfo.isEmpty()) {
            layout_general_add.setVisibility(true)
            recycler_general.setVisibility(false)

            val message = when(type) {
                ScheduleType.GROUP -> getString(R.string.add_first_group)
                ScheduleType.TEACHER -> getString(R.string.add_first_teacher)
                ScheduleType.AUDITORY -> getString(R.string.add_first_auditory)
                else -> null
            }

            description_general_text.text = message
            layout_general_add.setOnClickListener { presenter.onAddClick() }
        } else {
            generalAdapter = CategoryItemAdapter(scheduleInfo, callback, callback)
            recycler_general.layoutManager = LinearLayoutManager(context)
            recycler_general.adapter = generalAdapter
        }
    }

    override fun openFacultyScreen(type: ScheduleType, tag: String) {
        (activity as? MainActivity)?.replaceFragment(FacultyListFragment.newInstance(type, tag))
    }

    override fun openScheduleScreen(baseSchedule: BaseModel) {
        baseSchedule.scheduleType?.let {
            (activity as? MainActivity)?.replaceFragment(ScheduleListFragment.newInstance(baseSchedule, it)) }
    }

    override fun notifyDataSetChanged() {
        generalAdapter.notifyDataSetChanged()
    }

    override fun requestChangePinToActivity(newPinned: BaseModel?) {
        (activity as? MainActivity)?.changePin(newPinned)
    }

}
