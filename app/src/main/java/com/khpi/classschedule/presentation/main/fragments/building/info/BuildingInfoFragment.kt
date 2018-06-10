package com.khpi.classschedule.presentation.main.fragments.building.info

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.FullBuildingPair
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_building_info.*

class BuildingInfoFragment : BaseFragment(), BuildingInfoView {

    override var TAG = "BuildingInfoFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: BuildingInfoPresenter
    //@formatter:on

    private lateinit var shortName: String

    companion object {
        fun newInstance(shortName: String): BuildingInfoFragment = BuildingInfoFragment().apply {
            this.shortName = shortName
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_building_info, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
        presenter.loadBuildingByShortName(shortName)
    }

    override fun configureView() {
        (activity as? MainActivity)?.setRightSecondNavigationIcon(null)
    }

    override fun showBuildingInfo(buildingPairs: ArrayList<FullBuildingPair>, units: Map<String, ArrayList<String>>) {
        val ctx = context ?: return
        val buildingInfoAdapter = BuildingInfoAdapter(buildingPairs, units)

        val glm = GridLayoutManager(ctx, 2)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int =
                    when (buildingInfoAdapter.getItemViewType(position)) {
                        buildingInfoAdapter.TYPE_PAIR -> 1
                        else -> 2
                    }
        }
        recycler_buildings_info.layoutManager = glm
        recycler_buildings_info.addItemDecoration(BuildingInfoDecorator(ctx, resources.getDimension(R.dimen._12sdp).toInt(), buildingPairs.size))
        recycler_buildings_info.adapter = buildingInfoAdapter
    }
}
