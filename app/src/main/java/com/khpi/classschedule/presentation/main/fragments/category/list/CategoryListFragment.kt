package com.khpi.classschedule.presentation.main.fragments.category.list


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
import com.khpi.classschedule.presentation.main.fragments.category.item.CategoryItemFragment
import com.khpi.classschedule.views.BasePagerAdapter
import kotlinx.android.synthetic.main.fragment_category_list.*
import com.khpi.classschedule.data.models.ScheduleType


class CategoryListFragment : BaseFragment(), CategoryListView {

    override var TAG = "CategoryListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: CategoryListPresenter
    //@formatter:on

    companion object {
        fun newInstance(): CategoryListFragment = CategoryListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_category_list, container, false)

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

        val groups = CategoryItemFragment.newInstance(infoGroups, ScheduleType.GROUP)
        val teachers = CategoryItemFragment.newInstance(infoTeachers, ScheduleType.TEACHER)
        val auditories = CategoryItemFragment.newInstance(infoAuditories, ScheduleType.AUDITORY)

        adapter.addFragment(groups, getString(R.string.groups))
        adapter.addFragment(teachers, getString(R.string.teachers))
        adapter.addFragment(auditories, getString(R.string.auditories))

        general_view_pager.adapter = adapter
        general_tab.visibility = View.VISIBLE
        general_tab.setupWithViewPager(general_view_pager)
    }
}
