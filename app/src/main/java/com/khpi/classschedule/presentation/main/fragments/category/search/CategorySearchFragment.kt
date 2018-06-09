package com.khpi.classschedule.presentation.main.fragments.category.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ImageView
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.schedule.list.ScheduleListFragment
import com.khpi.classschedule.utils.setVisibility
import com.khpi.classschedule.views.BaseAdapter
import kotlinx.android.synthetic.main.fragment_category_search.*
import kotlinx.android.synthetic.main.toolbar.*
import android.support.v4.view.MenuItemCompat.setOnActionExpandListener



class CategorySearchFragment : BaseFragment(), CategorySearchView {

    override var TAG = "CategorySearchFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: CategorySearchPresenter
    //@formatter:on

    private var type: ScheduleType? = null
    private lateinit var searchAdapter : BaseAdapter
    private lateinit var searchViewItem : MenuItem

    companion object {
        fun newInstance(type: ScheduleType): CategorySearchFragment = CategorySearchFragment().apply {
            this.type = type
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_category_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val message = when (type) {
            ScheduleType.GROUP -> resources.getString(R.string.search_description_group)
            ScheduleType.TEACHER -> resources.getString(R.string.search_description_teacher)
            ScheduleType.AUDITORY -> resources.getString(R.string.search_description_auditory)
            null -> ""
        }
        description_search_text.text = message
        setHasOptionsMenu(true)
        presenter.setType(type)
    }

    override fun hideOrShowRecyclerView(rvVisibility: Boolean, tvVisibility: Boolean) {
        recycler_search.setVisibility(rvVisibility)
        description_search_text.setVisibility(tvVisibility)
    }

    override fun configureRecyclerView(foundedGroup: MutableList<BaseModel>, callback: CategorySearchPresenter) {
        searchAdapter = BaseAdapter(foundedGroup, callback)
        recycler_search.layoutManager = LinearLayoutManager(context)
        recycler_search.adapter = searchAdapter
    }

    override fun onCreateOptionsMenu (menu: Menu, inflater : MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        searchViewItem = menu.findItem(R.id.action_search)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchViewItem.actionView as SearchView

        val hintMessage = when (type) {
            ScheduleType.GROUP -> resources.getString(R.string.search_groups)
            ScheduleType.TEACHER -> resources.getString(R.string.search_teacher)
            ScheduleType.AUDITORY -> resources.getString(R.string.search_auditory)
            null -> ""
        }

        searchView.queryHint = hintMessage
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

        searchViewItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                onBackPressed()
                return true
            }
        })

        searchView.setOnQueryTextListener(queryTextListener)
        searchViewItem.expandActionView()
    }

    override fun notifyDataSetChanged() {
        searchAdapter.notifyDataSetChanged()
    }

    override fun openScheduleScreen(baseSchedule: BaseModel) {
        baseSchedule.scheduleType?.let {
            (activity as? MainActivity)?.replaceFragment(ScheduleListFragment.newInstance(baseSchedule, it)) }
    }
}
