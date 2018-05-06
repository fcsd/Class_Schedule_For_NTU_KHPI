package com.khpi.classschedule.presentation.main.fragments.building.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.ShortBuilding
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import com.khpi.classschedule.presentation.main.fragments.building.info.BuildingInfoFragment
import com.khpi.classschedule.presentation.main.fragments.building.map.BuildingMapFragment
import com.khpi.classschedule.views.BasePagerAdapter
import kotlinx.android.synthetic.main.fragment_building_item.*

class BuildingItemFragment : BaseFragment(), BuildingItemView {

    override var TAG = "BuildingItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: BuildingItemPresenter
    //@formatter:on

    private lateinit var shortName: String

    companion object {
        fun newInstance(shortName: String): BuildingItemFragment = BuildingItemFragment().apply {
            this.shortName = shortName
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_building_item, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
        presenter.loadBuildingByShortName(shortName)
    }

    override fun configureView() {
        //nothing to override
    }

    override fun onBuildingLoaded(building: ShortBuilding) {
        (activity as? MainActivity)?.setToolbarTitle(building.fullName)
        setupViewPager(building)
    }

    private fun setupViewPager(building: ShortBuilding) {

        val adapter = BasePagerAdapter(childFragmentManager)
        val buildingInfoFragment = BuildingInfoFragment.newInstance(building.shortName)
        val buildingMapFragment = BuildingMapFragment.newInstance(building.latitude, building.longitude, building.fullName)

        adapter.addFragment(buildingMapFragment, getString(R.string.buildings_map))
        adapter.addFragment(buildingInfoFragment, getString(R.string.buildings_description))
        building_view_pager.adapter = adapter

        building_tab.setupWithViewPager(building_view_pager)
    }

}
