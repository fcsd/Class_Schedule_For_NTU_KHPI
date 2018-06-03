package com.khpi.classschedule.presentation.main.fragments.schedule.list

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.Schedule
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.data.models.Screen
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.category.list.CategoryListFragment
import com.khpi.classschedule.presentation.main.fragments.schedule.item.ScheduleItemFragment
import com.khpi.classschedule.views.BasePagerAdapter
import kotlinx.android.synthetic.main.fragment_schedule_list.*

class ScheduleListFragment : BaseFragment(), ScheduleListView {

    override var TAG = "ScheduleListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: ScheduleListPresenter
    //@formatter:on

    private var type: ScheduleType? = null
    private var group: BaseModel? = null

    companion object {
        fun newInstance(group: BaseModel, type: ScheduleType): ScheduleListFragment = ScheduleListFragment().apply {
            this.group = group
            this.type = type
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_schedule_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? MainActivity)?.removeToolbarTitleFunctionForSchedule()
        (activity as? MainActivity)?.setRightFirstNavigationIcon(null)
    }

    override fun configureView() {
        setHasOptionsMenu(true)
        val groupName = this.group?.title ?: return
        (activity as? MainActivity)?.requestVisibleViews(Screen.SCHEDULE)
        (activity as? MainActivity)?.setToolbarTitleForSchedule(groupName)
        group?.let { presenter.loadScheduleById(it, type) }
    }

    override fun showToolbarIcons() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setRightFirstClickListener { presenter.changeCurrentWeek() }
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_dots))
        (activity as? MainActivity)?.setToolbarTitleClickListener { presenter.loadAllSchedules() }
        (activity as? MainActivity)?.setRightSecondClickListener { presenter.openCategoryScreen() }
    }

    override fun showSchedule(schedule: Schedule, currentWeek: Int, currentTab: Int) {

        val type = this.type ?: return
        val ctx = context ?: return

        when(currentWeek) {
            1 -> (activity as? MainActivity)?.setRightFirstNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_first_week))
            2 -> (activity as? MainActivity)?.setRightFirstNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_second_week))
        }

        val adapter = BasePagerAdapter(childFragmentManager)
        val monday = ScheduleItemFragment.newInstance(schedule.monday, type)
        val tuesday = ScheduleItemFragment.newInstance(schedule.tuesday, type)
        val wednesday = ScheduleItemFragment.newInstance(schedule.wednesday, type)
        val thursday = ScheduleItemFragment.newInstance(schedule.thursday, type)
        val friday = ScheduleItemFragment.newInstance(schedule.friday, type)

        adapter.addFragment(monday, getString(R.string.monday))
        adapter.addFragment(tuesday, getString(R.string.tuesday))
        adapter.addFragment(wednesday, getString(R.string.wednesday))
        adapter.addFragment(thursday, getString(R.string.thursday))
        adapter.addFragment(friday, getString(R.string.friday))

        schedule_view_pager.adapter = adapter
        schedule_view_pager.currentItem = currentTab
        schedule_tab.visibility = View.VISIBLE
        schedule_tab.setupWithViewPager(schedule_view_pager)

        schedule_tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                presenter.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) { }

            override fun onTabReselected(tab: TabLayout.Tab) { }
        })
    }

    override fun showSchedulesPopup(schedules: MutableList<BaseModel>) {

        val view = (activity as? MainActivity)?.getToolbarTitleScheduleView() ?: return
        val popupMenu = PopupMenu(context, view)
        schedules.forEachIndexed { index, schedule ->
            popupMenu.menu.add(Menu.NONE, index, Menu.NONE, schedule.title)
        }

        popupMenu.setOnMenuItemClickListener { item ->
            presenter.changeCurrentGroup(item.itemId)
            false
        }
        popupMenu.show()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun changeToolbarTitle(title: String) {
        (activity as? MainActivity)?.setToolbarTitleForSchedule(title)
    }

    override fun changeScheduleType(info: BaseModel?) {
        group = info
        type = info?.scheduleType
    }

    override fun openCategoryScreen() {
        (activity as? MainActivity)?.replaceFragment(CategoryListFragment.newInstance())
    }

    override fun requestChangePinToActivity(newPinned: BaseModel?) {
        (activity as? MainActivity)?.changePin(newPinned)
    }
}
