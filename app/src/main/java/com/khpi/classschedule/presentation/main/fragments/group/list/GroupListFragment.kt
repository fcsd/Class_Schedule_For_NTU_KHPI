package com.khpi.classschedule.presentation.main.fragments.group.list


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.group.item.GroupItemFragment
import com.khpi.classschedule.views.BasePagerAdapter
import kotlinx.android.synthetic.main.fragment_group_list.*
import kotlinx.android.synthetic.main.fragment_schedule_list.*

class GroupListFragment : BaseFragment(), GroupListView {

    override var TAG = "GroupListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: GroupListPresenter
    //@formatter:on

    private var faculty: BaseModel? = null
    private var type: ScheduleType? = null

    companion object {
        fun newInstance(faculty: BaseModel, type: ScheduleType): GroupListFragment = GroupListFragment().apply {
            this.faculty = faculty
            this.type = type
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_group_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val facultyTitle = this.faculty?.title ?: return
        val facultyId = this.faculty?.id ?: return
        (activity as? MainActivity)?.setToolbarTitle(facultyTitle)
        (activity as? MainActivity)?.setRightSecondNavigationIcon(null)

        presenter.setType(type)
        presenter.loadGroupListById(facultyId)

        group_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                presenter.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) { }

            override fun onTabReselected(tab: TabLayout.Tab) { }
        })
    }

    override fun showGroupsByCourse(groupsByFirstCourse: List<BaseModel>,
                                    groupsBySecondCourse: List<BaseModel>,
                                    groupsByThirdCourse: List<BaseModel>,
                                    groupsByFourthCourse: List<BaseModel>,
                                    groupsByFifthCourse: List<BaseModel>,
                                    groupsBySixthCourse: List<BaseModel>,
                                    type: ScheduleType,
                                    currentTab: Int) {

        val adapter = BasePagerAdapter(childFragmentManager)

        val groupsByFirstCourseFragment = GroupItemFragment.newInstance(groupsByFirstCourse, type)
        val groupsBySecondCourseFragment = GroupItemFragment.newInstance(groupsBySecondCourse, type)
        val groupsByThirdCourseFragment = GroupItemFragment.newInstance(groupsByThirdCourse, type)
        val groupsByFourthCourseFragment = GroupItemFragment.newInstance(groupsByFourthCourse, type)
        val groupsByFifthCourseFragment = GroupItemFragment.newInstance(groupsByFifthCourse, type)
        val groupsBySixthCourseFragment = GroupItemFragment.newInstance(groupsBySixthCourse, type)

        adapter.addFragment(groupsByFirstCourseFragment, getString(R.string.first_course))
        adapter.addFragment(groupsBySecondCourseFragment, getString(R.string.second_course))
        adapter.addFragment(groupsByThirdCourseFragment, getString(R.string.third_course))
        adapter.addFragment(groupsByFourthCourseFragment, getString(R.string.fourth_course))
        adapter.addFragment(groupsByFifthCourseFragment, getString(R.string.fifth_course))
        adapter.addFragment(groupsBySixthCourseFragment, getString(R.string.sixth_course))

        group_view_pager.adapter = adapter
        group_view_pager.currentItem = currentTab
        group_tab.visibility = View.VISIBLE
        group_tab.setupWithViewPager(group_view_pager)
    }
}