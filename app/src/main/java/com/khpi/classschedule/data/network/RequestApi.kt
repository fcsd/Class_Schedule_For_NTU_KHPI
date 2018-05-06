package com.khpi.classschedule.data.network

import com.khpi.classschedule.data.models.BaseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {

    @GET("FacultyList")
    fun getFacultyList(): Single<ArrayList<BaseModel>>

    @GET("GroupByFacultyList/{fid}")
    fun getGroupListByFacultyId(
            @Path("fid") facultyId : Int
    ): Single<ArrayList<BaseModel>>
}