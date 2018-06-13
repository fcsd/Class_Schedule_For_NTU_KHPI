package com.khpi.classschedule.business

import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.BufferSearchModel
import com.khpi.classschedule.data.models.FullSchedule
import com.khpi.classschedule.data.network.RequestApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ScheduleManager(private val requestApi: RequestApi) {

    fun getFacultyList(action: String,
                       onSuccess: (response: ArrayList<BaseModel>) -> Unit,
                       onFailure: (exception: String?) -> Unit) {

        requestApi.getActionList(action)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onSuccess(it) },
                        { throwable ->
                            val exception = (throwable as? Exception)?.localizedMessage
                            onFailure(exception)
                        })
    }

    fun getActionListById(action: String,
                         facultyId : Int,
                         onSuccess: (response: ArrayList<BaseModel>) -> Unit,
                         onFailure: (exception: String?) -> Unit) {

        requestApi.getActionListById(action, facultyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onSuccess(it) },
                        { throwable ->
                            val exception = (throwable as? Exception)?.localizedMessage
                            onFailure(exception)
                        })
    }

    fun getScheduleByWeekById(action: String,
                              groupId : Int,
                              onSuccess: (response: FullSchedule) -> Unit,
                              onFailure: (exception: String?) -> Unit) {

        requestApi.getScheduleByWeekById(action, groupId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { onSuccess(it) },
                        { throwable ->
                            val exception = (throwable as? Exception)?.localizedMessage
                            onFailure(exception)
                        })
    }

    fun getSearchedList(action: String,
                        searchedText: String,
                        onSuccess: (response: ArrayList<BufferSearchModel>) -> Unit,
                        onFailure: (exception: String?) -> Unit) {

        requestApi.getSearchedList(action, searchedText)
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