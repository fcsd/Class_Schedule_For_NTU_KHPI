package com.khpi.classschedule.business

import com.khpi.classschedule.data.models.FullBuilding
import com.khpi.classschedule.data.models.FullBuildingPair
import com.khpi.classschedule.data.models.ShortBuilding

class BuildingManager {

    private var shortBuildings = listOf<ShortBuilding>()
    private var fullBuildings = listOf<FullBuilding>()

    init {
        shortBuildings = generateShortBuildingsInfo()
        fullBuildings = generateFullBuildingsInfo()
    }

    private fun generateShortBuildingsInfo() : ArrayList<ShortBuilding> {
        val shortBuildings = arrayListOf<ShortBuilding>()
        shortBuildings.add(ShortBuilding("Адміністративний корпус", "АК", 50.001024, 36.250337))
        return shortBuildings
    }

    private fun generateFullBuildingsInfo() : ArrayList<FullBuilding> {

        val fullBuildings = arrayListOf<FullBuilding>()
        fullBuildings.add(FullBuilding("АК", arrayListOf(
                FullBuildingPair("Скорочена назва", "АК"),
                FullBuildingPair("Кількість поверхів", "3")),
                "Відділи та підрозділи",
                arrayListOf("Бухгалтерія (2 пов., к.1)","Канцелярія (2 пов., к.10,12)", "Відділ кадрів (3 пов., к.18)",
                        "Відділ коммандировок (2 пов., к.13)", "Відділ коммандировок (2 пов., к.13)", "Перший відділ (3 пов., к.15,15а)",
                        "Стипіндіальний відділ (2 пов., к.2,3)", "Типографія (1 пов.)", "Фінансо-плановий відділ (3 пов., к.23-25)",
                        "Юридичний відділ (2 пов.)", "Відділ договірної практики (2 пов.)", "МІПО (3 пов., к.305)", "Підготовчі курси (3 пов., к.302)")))

        return fullBuildings
    }

    fun getAllShortBuildings() : MutableList<ShortBuilding> = shortBuildings.toMutableList()

    fun getShortBuildingByShortName(shortName: String) : ShortBuilding? {
        shortBuildings.filter { it.shortName == shortName }.forEach { return it }
        return null
    }

    fun getFullBuildingByShortName(shortName: String) : FullBuilding? {
        fullBuildings.filter { it.shortName == shortName }.forEach { return it }
        return null
    }
}