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
import com.khpi.classschedule.presentation.main.fragments.category.pin.CategoryPinFragment

class CategoryListFragment : BaseFragment(), CategoryListView {

    override var TAG = "CategoryListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: CategoryListPresenter
    //@formatter:on

    companion object {
        fun newInstance(): CategoryListFragment = CategoryListFragment()
    }

    var groupPinFragment: CategoryPinFragment? = null
    var teacherPinFragment: CategoryPinFragment? = null
    var auditotyPinFragment: CategoryPinFragment? = null

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
        (activity as? MainActivity)?.setToolbarTitle(resources.getString(R.string.category))
        changeToolbarSecondButtonForShow()
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
        general_tab.setupWithViewPager(general_view_pager)
    }

    override fun showPinSchedulesInfo(infoGroups: MutableList<BaseModel>,
                                      infoTeachers: MutableList<BaseModel>,
                                      infoAuditories: MutableList<BaseModel>,
                                      listener: CategoryListPresenter) {

        val adapter = BasePagerAdapter(childFragmentManager)

        groupPinFragment = CategoryPinFragment.newInstance(infoGroups, listener)
        teacherPinFragment = CategoryPinFragment.newInstance(infoTeachers, listener)
        auditotyPinFragment = CategoryPinFragment.newInstance(infoAuditories, listener)

        groupPinFragment?.let { adapter.addFragment(it, getString(R.string.groups)) }
        teacherPinFragment?.let { adapter.addFragment(it, getString(R.string.teachers)) }
        auditotyPinFragment?.let { adapter.addFragment(it, getString(R.string.auditories)) }

        general_view_pager.adapter = adapter
        general_view_pager.offscreenPageLimit = 2
        general_tab.setupWithViewPager(general_view_pager)
    }

    override fun changeToolbarSecondButtonForShow() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_pin))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.loadPinSchedulesInfo() }
    }

    override fun changeToolbarSecondButtonForPin() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_apply))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.confirmPinGroup() }
    }

    override fun notifyDataSetChanged() {
        groupPinFragment?.notifyDataSetChanged()
        teacherPinFragment?.notifyDataSetChanged()
        auditotyPinFragment?.notifyDataSetChanged()
    }
}
