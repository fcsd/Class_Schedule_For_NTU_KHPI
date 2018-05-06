package com.khpi.classschedule.presentation.main.fragments.building.info

import com.khpi.classschedule.data.models.FullBuildingPair
import com.khpi.classschedule.presentation.base.BaseView

interface BuildingInfoView : BaseView {
    fun showBuildingInfo(buildingPairs: ArrayList<FullBuildingPair>, unitTitle: String, units: ArrayList<String>)
}