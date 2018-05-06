package com.khpi.classschedule.presentation.main.fragments.building.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.ShortBuilding
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.building.item.BuildingItemFragment
import kotlinx.android.synthetic.main.fragment_building_list.*

class BuildingListFragment : BaseFragment(), BuildingListView {

    override var TAG = "BuildingListFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: BuildingListPresenter
    //@formatter:on

    companion object {
        fun newInstance(): BuildingListFragment = BuildingListFragment()
    }

    private lateinit var buildingAdapter : BuildingListAdapter
    private lateinit var searchViewItem : MenuItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_building_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        (activity as? MainActivity)?.setToolbarTitle(getString(R.string.buildings_list_title))
        (activity as? MainActivity)?.setRightSecondNavigationIcon(null)
        setHasOptionsMenu(true)
    }

    override fun onBuildingsLoaded(buildings: MutableList<ShortBuilding>) {
        buildingAdapter = BuildingListAdapter(buildings, presenter)
        recycler_buildings.layoutManager = LinearLayoutManager(context)
        recycler_buildings.adapter = buildingAdapter
    }

    override fun notifyDataSetChanged() {
        buildingAdapter.notifyDataSetChanged()
    }

    override fun openBuildingScreen(shortName: String) {
        (activity as MainActivity).replaceFragment(BuildingItemFragment.newInstance(shortName))
    }

    override fun onCreateOptionsMenu (menu: Menu, inflater : MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        searchViewItem = menu.findItem(R.id.action_search)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchViewItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_buildings)
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

    override fun onBackPressed() {
        if (searchViewItem.isActionViewExpanded) {
            searchViewItem.collapseActionView()
        } else {
            super.onBackPressed()
        }
    }

}
