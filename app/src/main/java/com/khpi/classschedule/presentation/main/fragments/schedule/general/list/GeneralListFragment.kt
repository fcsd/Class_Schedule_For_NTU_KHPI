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
import android.support.v4.view.ViewPager
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

    private var visibleTab = 0
    lateinit var adapter: BasePagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_general_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_add_new_item))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.onAddClicked(visibleTab) }

        (activity as? MainActivity)?.setToolbarTitle(resources.getString(R.string.my_groups))
        presenter.loadSchedules()
    }

    override fun showSavedSchedulesInfo(infoGroups: MutableList<BaseModel>,
                                        infoTeachers: MutableList<BaseModel>,
                                        infoAuditories: MutableList<BaseModel>) {

        adapter = BasePagerAdapter(childFragmentManager)

        val groups = GeneralItemFragment.newInstance(infoGroups)
        val teachers = GeneralItemFragment.newInstance(infoTeachers)
        val auditories = GeneralItemFragment.newInstance(infoAuditories)

        adapter.addFragment(groups, getString(R.string.groups))
        adapter.addFragment(teachers, getString(R.string.teachers))
        adapter.addFragment(auditories, getString(R.string.auditories))

        general_view_pager.adapter = adapter
        general_tab.visibility = View.VISIBLE
        general_tab.setupWithViewPager(general_view_pager)

        general_view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position : Int, offset : Float, offsetPixels: Int) {
                visibleTab = position
            }

            override fun onPageSelected(i: Int) { }

            override fun onPageScrollStateChanged(i: Int) { }
        })
    }

    override fun openFacultyScreen(type: ScheduleType) {
        (activity as? MainActivity)?.replaceFragment(FacultyListFragment.newInstance(type))
    }

}
