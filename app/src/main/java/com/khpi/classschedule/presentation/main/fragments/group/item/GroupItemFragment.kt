package com.khpi.classschedule.presentation.main.fragments.group.item


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.schedule.show.list.ScheduleListFragment
import com.khpi.classschedule.views.BaseAdapter
import kotlinx.android.synthetic.main.fragment_group_item.*

class GroupItemFragment : BaseFragment(), GroupItemView {

    override var TAG = "GroupItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: GroupItemPresenter
    //@formatter:on

    private var groups = listOf<BaseModel>()


    private var type: ScheduleType? = null
    private lateinit var groupAdapter : BaseAdapter
    private lateinit var searchViewItem : MenuItem

    companion object {
        fun newInstance(groups: List<BaseModel>, type: ScheduleType): GroupItemFragment = GroupItemFragment().apply {
            this.groups = groups
            this.type = type
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_group_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        setHasOptionsMenu(true)
        presenter.prepareToShowGroups(groups)
    }

    override fun showGroups(groups: MutableList<BaseModel>, callback: GroupItemPresenter) {
        groupAdapter = BaseAdapter(groups, callback)
        recycler_group.layoutManager = LinearLayoutManager(context)
        recycler_group.adapter = groupAdapter
    }

    override fun onCreateOptionsMenu (menu: Menu, inflater : MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        searchViewItem = menu.findItem(R.id.action_search)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchViewItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_groups)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setIconifiedByDefault(false)

        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                presenter.onSearchEntered(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewItem.collapseActionView()
                return false
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)
    }

    override fun notifyDataSetChanged() {
        groupAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (searchViewItem.isActionViewExpanded) {
            searchViewItem.collapseActionView()
        } else {
            super.onBackPressed()
        }
    }

    override fun openScheduleScreen(group: BaseModel) {
        type?.let { (activity as? MainActivity)?.replaceFragment(ScheduleListFragment.newInstance(group, it)) }
    }
}
