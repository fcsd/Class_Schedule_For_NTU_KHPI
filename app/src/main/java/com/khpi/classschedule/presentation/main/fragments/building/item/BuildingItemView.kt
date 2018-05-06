package com.khpi.classschedule.presentation.main.fragments.building.item

import com.khpi.classschedule.data.models.ShortBuilding
import com.khpi.classschedule.presentation.base.BaseView


interface BuildingItemView : BaseView {
    fun onBuildingLoaded(building: ShortBuilding)
}