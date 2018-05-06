package com.khpi.classschedule.presentation.main.fragments.group.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.group.item.GroupItemFragment
import com.khpi.classschedule.views.BasePagerAdapter
import kotlinx.android.synthetic.main.fragment_group_list.*

class GroupListFragment : BaseFragment(), GroupListView {

    override var TAG = "GroupListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: GroupListPresenter
    //@formatter:on

    private var faculty: BaseModel? = null

    companion object {
        fun newInstance(faculty: BaseModel): GroupListFragment = GroupListFragment().apply {
            this.faculty = faculty
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
        presenter.loadGroupListByFacultyId(facultyId)
    }

    override fun showGroupsByCourse(groupsByFirstCourse: List<BaseModel>,
                                    groupsBySecondCourse: List<BaseModel>,
                                    groupsByThirdCourse: List<BaseModel>,
                                    groupsByFourthCourse: List<BaseModel>,
                                    groupsByFifthCourse: List<BaseModel>,
                                    groupsBySixthCourse: List<BaseModel>) {

        val adapter = BasePagerAdapter(childFragmentManager)

        val groupsByFirstCourseFragment = GroupItemFragment.newInstance(groupsByFirstCourse)
        val groupsBySecondCourseFragment = GroupItemFragment.newInstance(groupsBySecondCourse)
        val groupsByThirdCourseFragment = GroupItemFragment.newInstance(groupsByThirdCourse)
        val groupsByFourthCourseFragment = GroupItemFragment.newInstance(groupsByFourthCourse)
        val groupsByFifthCourseFragment = GroupItemFragment.newInstance(groupsByFifthCourse)
        val groupsBySixthCourseFragment = GroupItemFragment.newInstance(groupsBySixthCourse)

        adapter.addFragment(groupsByFirstCourseFragment, getString(R.string.first_course))
        adapter.addFragment(groupsBySecondCourseFragment, getString(R.string.second_course))
        adapter.addFragment(groupsByThirdCourseFragment, getString(R.string.third_course))
        adapter.addFragment(groupsByFourthCourseFragment, getString(R.string.fourth_course))
        adapter.addFragment(groupsByFifthCourseFragment, getString(R.string.fifth_course))
        adapter.addFragment(groupsBySixthCourseFragment, getString(R.string.sixth_course))

        group_view_pager.adapter = adapter
        group_tab.visibility = View.VISIBLE
        group_tab.setupWithViewPager(group_view_pager)
    }
}