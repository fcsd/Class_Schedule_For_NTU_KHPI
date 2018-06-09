package com.khpi.classschedule.data.network

import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.BufferSearchModel
import com.khpi.classschedule.data.models.FullSchedule
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {

    @GET("FacultyList")
    fun getFacultyList(): Single<ArrayList<BaseModel>>

    @GET("{action}/{id}")
    fun getActionListById(
            @Path("action") action : String,
            @Path("id") facultyId : Int
    ): Single<ArrayList<BaseModel>>

    @GET("{action}/!{searchedText}")
    fun getSearchedList(
            @Path("action") action : String,
            @Path("searchedText") searchedText : String
    ): Single<ArrayList<BufferSearchModel>>

    @GET("{action}/{id}")
    fun getScheduleByWeekById(
            @Path("action") action : String,
            @Path("id") groupId : Int
    ): Single<FullSchedule>
}