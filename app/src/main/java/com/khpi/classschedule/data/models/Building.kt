package com.khpi.classschedule.data.models

data class FullBuilding(
        val shortName: String,
        val data: ArrayList<FullBuildingPair>,
        val unitTitle: String,
        val units: ArrayList<String>
)

data class FullBuildingPair(
        val title: String,
        val value: String
)

data class ShortBuilding(
        val fullName: String,
        val shortName: String,
        val latitude: Double,
        val longitude: Double
)