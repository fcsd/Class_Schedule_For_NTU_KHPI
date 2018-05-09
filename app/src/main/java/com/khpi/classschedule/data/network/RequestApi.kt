package com.khpi.classschedule.data.network

import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.data.models.BaseSchedule
import com.khpi.classschedule.data.models.FullSchedule
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestApi {

    @GET("FacultyList")
    fun getFacultyList(): Single<ArrayList<BaseModel>>

    @GET("GroupByFacultyList/{fid}")
    fun getGroupListById(
            @Path("fid") facultyId : Int
    ): Single<ArrayList<BaseSchedule>>

    @GET("{week}/{gid}")
    fun getScheduleByWeekById(
            @Path("week") week : String,
            @Path("gid") groupId : Int
    ): Single<FullSchedule>

}