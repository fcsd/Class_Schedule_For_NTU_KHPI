package com.khpi.classschedule.business

import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.FullSchedule
import com.khpi.classschedule.data.network.RequestApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ScheduleManager(private val requestApi: RequestApi) {

    fun getFacultyList(onSuccess: (response: ArrayList<BaseModel>) -> Unit,
                       onFailure: (exception: String?) -> Unit) {

        requestApi.getFacultyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onSuccess(it) },
                        { throwable ->
                            val exception = (throwable as? Exception)?.localizedMessage
                            onFailure(exception)
                        })
    }

    fun getGroupListById(facultyId : Int,
                         onSuccess: (response: ArrayList<BaseModel>) -> Unit,
                         onFailure: (exception: String?) -> Unit) {

        requestApi.getGroupListById(facultyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onSuccess(it) },
                        { throwable ->
                            val exception = (throwable as? Exception)?.localizedMessage
                            onFailure(exception)
                        })
    }

    fun getScheduleByWeekById(week: String,
                              groupId : Int,
                              onSuccess: (response: FullSchedule) -> Unit,
                              onFailure: (exception: String?) -> Unit) {

        requestApi.getScheduleByWeekById(week, groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onSuccess(it) },
                        { throwable ->
                            val exception = (throwable as? Exception)?.localizedMessage
                            onFailure(exception)
                        })
    }
}