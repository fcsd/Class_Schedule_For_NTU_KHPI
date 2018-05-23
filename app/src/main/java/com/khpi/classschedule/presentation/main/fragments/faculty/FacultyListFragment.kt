package com.khpi.classschedule.presentation.main.fragments.faculty

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.Constants

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.group.list.GroupListFragment
import com.khpi.classschedule.presentation.main.fragments.schedule.list.ScheduleListFragment
import com.khpi.classschedule.views.BaseAdapter
import kotlinx.android.synthetic.main.fragment_faculty_list.*

class FacultyListFragment : BaseFragment(), FacultyListView {

    override var TAG = ""

    //@formatter:off
    @InjectPresenter lateinit var presenter: FacultyListPresenter
    //@formatter:on

    private var type: ScheduleType? = null
    private var id: Int? = null
    private var title: String? = null
    private lateinit var facultyAdapter : BaseAdapter
    private lateinit var searchViewItem : MenuItem

    companion object {
        fun newInstance(type: ScheduleType, tag: String, id: Int? = null, title: String? = null)
                : FacultyListFragment = FacultyListFragment().apply {
            this.type = type
            this.id = id
            this.title = title
            TAG = tag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_faculty_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        when (type) {
            ScheduleType.GROUP -> (activity as? MainActivity)?.setToolbarTitle(getString(R.string.faculty))
            ScheduleType.TEACHER -> {
                when (tag) {
                    Constants.FACULTY_FRAGMENT -> (activity as? MainActivity)?.setToolbarTitle(getString(R.string.faculty))
                    Constants.DEPARTMENT_FRAGMENT -> (activity as? MainActivity)?.setToolbarTitle(getString(R.string.department))
                    Constants.TEACHER_FRAGMENT -> title?.let { (activity as? MainActivity)?.setToolbarTitle(it) }
                }
            }
            ScheduleType.AUDITORY -> throw NotImplementedError()
        }

        (activity as? MainActivity)?.setRightSecondNavigationIcon(null)
        setHasOptionsMenu(true)
        presenter.manageActionByTypeAndTag(type, TAG, id)
    }

    override fun onFacultyLoaded(faculties: MutableList<BaseModel>, callback: FacultyListPresenter) {
        facultyAdapter = BaseAdapter(faculties, callback)
        recycler_facutlies.layoutManager = LinearLayoutManager(context)
        recycler_facutlies.adapter = facultyAdapter
    }

    override fun openGroupScreen(model: BaseModel) {
        type?.let { (activity as? MainActivity)?.replaceFragment(GroupListFragment.newInstance(model, it)) }
    }

    override fun reopenCurrentScreen(model: BaseModel, newFragmentTag: String) {
        val type = type ?: return
        (activity as? MainActivity)?.replaceFragment(FacultyListFragment.newInstance(type, newFragmentTag, model.id, model.title))
    }

    override fun openScheduleScreen(model: BaseModel) {
        type?.let { (activity as? MainActivity)?.replaceFragment(ScheduleListFragment.newInstance(model, it)) }
    }

    override fun onCreateOptionsMenu (menu: Menu, inflater : MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        searchViewItem = menu.findItem(R.id.action_search)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchViewItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_faculties)
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
        facultyAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (searchViewItem.isActionViewExpanded) {
            searchViewItem.collapseActionView()
        } else {
            super.onBackPressed()
        }
    }

}