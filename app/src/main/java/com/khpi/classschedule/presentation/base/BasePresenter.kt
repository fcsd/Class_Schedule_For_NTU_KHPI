package com.khpi.classschedule.presentation.base

import com.arellomobile.mvp.MvpPresenter
import com.khpi.classschedule.App
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.models.ScheduleItem
import com.khpi.classschedule.data.models.ScheduleType
import com.khpi.classschedule.di.AppComponent

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    protected fun injector(): AppComponent = App.dagger

    abstract fun onViewLoaded()

    private fun getScheduleTimeByKey(key: String): String = when (key) {
        "Para1" -> "08.30 - 10.05"
        "Para2" -> "10.25 - 12.00"
        "Para3" -> "12.35 - 14.10"
        "Para4" -> "14.30 - 16.05"
        "Para5" -> "16.25 - 18.00"
        else -> "18.10 - 19.45"
    }

    private fun getScheduleCoupleByKey(key: String): Int = when (key) {
        "Para1" -> 1
        "Para2" -> 2
        "Para3" -> 3
        "Para4" -> 4
        "Para5" -> 5
        else -> 6
    }

    protected fun getPrefixByType(type: ScheduleType): String = when (type) {
        ScheduleType.GROUP -> Constants.GROUP_PREFIX
        ScheduleType.TEACHER -> Constants.TEACHER_PREFIX
        ScheduleType.AUDITORY -> Constants.AUDITORY_PREFIX
    }

    protected fun getMessageByType(type: ScheduleType): String = when (type) {
        ScheduleType.GROUP -> "групи"
        ScheduleType.TEACHER -> "викладача"
        ScheduleType.AUDITORY -> "аудиторії"
    }

    protected fun setNormalFormForSchedule(day: HashMap<String, ScheduleItem>): List<ScheduleItem> {
        for ((key, schedule) in day) {
            schedule.time = getScheduleTimeByKey(key)
            schedule.couple = getScheduleCoupleByKey(key)
        }

        val withoutEmptySchedule = ArrayList<ScheduleItem>()

        day.values.forEach { couple ->
            if (!couple.name.isNullOrEmpty()) {
                withoutEmptySchedule.add(couple)
            }
        }

        return withoutEmptySchedule.sortedBy { it.couple }
    }
}