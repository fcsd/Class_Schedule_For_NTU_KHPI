package com.khpi.classschedule.presentation.main.fragments.category.list

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.data.models.Screen
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.category.item.CategoryItemFragment
import com.khpi.classschedule.presentation.main.fragments.category.pin.CategoryPinFragment
import com.khpi.classschedule.presentation.main.fragments.category.search.CategorySearchFragment
import com.khpi.classschedule.views.BasePagerAdapter
import kotlinx.android.synthetic.main.fragment_category_list.*

class CategoryListFragment : BaseFragment(), CategoryListView {

    override var TAG = "CategoryListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: CategoryListPresenter
    //@formatter:on

    private var currentTab = 0

    companion object {
        fun newInstance(currentTab: Int = 0): CategoryListFragment = CategoryListFragment().apply {
            this.currentTab = currentTab
        }
    }

    private var groupPinFragment: CategoryPinFragment? = null
    private var teacherPinFragment: CategoryPinFragment? = null
    private var auditotyPinFragment: CategoryPinFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_category_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.setRightFirstNavigationIcon(null)
        (activity as? MainActivity)?.setRightSecondNavigationIcon(null)
    }

    override fun configureView() {
        (activity as? MainActivity)?.requestVisibleViews(Screen.SCHEDULE)
        (activity as? MainActivity)?.setToolbarTitle(resources.getString(R.string.category))
        setHasOptionsMenu(true)
        changeToolbarSecondButtonForShow()
        presenter.setCurrentItem(currentTab, true)
        presenter.loadSchedules()

        general_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                presenter.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) { }

            override fun onTabReselected(tab: TabLayout.Tab) { }
        })
    }

    override fun showSavedSchedulesInfo(infoGroups: MutableList<BaseModel>,
                                        infoTeachers: MutableList<BaseModel>,
                                        infoAuditories: MutableList<BaseModel>,
                                        currentTab: Int,
                                        listener: CategoryListPresenter) {

        if (infoGroups.size + infoTeachers.size + infoAuditories.size < 2) {
            (activity as? MainActivity)?.setRightFirstNavigationIcon(null)
        }

        val adapter = BasePagerAdapter(childFragmentManager)

        val groups = CategoryItemFragment.newInstance(infoGroups, ScheduleType.GROUP, listener)
        val teachers = CategoryItemFragment.newInstance(infoTeachers, ScheduleType.TEACHER, listener)
        val auditories = CategoryItemFragment.newInstance(infoAuditories, ScheduleType.AUDITORY, listener)

        adapter.addFragment(groups, getString(R.string.groups))
        adapter.addFragment(teachers, getString(R.string.teachers))
        adapter.addFragment(auditories, getString(R.string.auditories))

        general_view_pager.adapter = adapter
        general_view_pager.currentItem = currentTab
        general_tab.setupWithViewPager(general_view_pager)
    }

    override fun showPinSchedulesInfo(infoGroups: MutableList<BaseModel>,
                                      infoTeachers: MutableList<BaseModel>,
                                      infoAuditories: MutableList<BaseModel>,
                                      currentTab: Int,
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
        general_view_pager.currentItem = currentTab
        general_tab.setupWithViewPager(general_view_pager)
    }

    override fun changeToolbarSecondButtonForShow() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_search))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.openSearchScreen() }
        (activity as? MainActivity)?.setRightFirstNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_pin))
        (activity as? MainActivity)?.setRightFirstClickListener { presenter.loadPinSchedulesInfo() }
    }

    override fun changeToolbarSecondButtonForPin() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setRightFirstNavigationIcon(null)
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_apply))
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.confirmPinGroup() }
    }

    override fun notifyDataSetChanged() {
        groupPinFragment?.notifyDataSetChanged()
        teacherPinFragment?.notifyDataSetChanged()
        auditotyPinFragment?.notifyDataSetChanged()
    }

    override fun requestChangePinToActivity(newInfo: BaseModel) {
        (activity as? MainActivity)?.changePin(newInfo)
    }

    override fun openSearchScreen(type: ScheduleType) {
        (activity as? MainActivity)?.replaceFragment(CategorySearchFragment.newInstance(type), isAnimate = false)
    }
}
