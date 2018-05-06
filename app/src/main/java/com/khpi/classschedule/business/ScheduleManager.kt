package com.khpi.classschedule.business

import com.khpi.classschedule.data.models.BaseModel
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

    fun getGroupListByFacultyId(facultyId : Int,
                                onSuccess: (response: ArrayList<BaseModel>) -> Unit,
                                onFailure: (exception: String?) -> Unit) {

        requestApi.getGroupListByFacultyId(facultyId)
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