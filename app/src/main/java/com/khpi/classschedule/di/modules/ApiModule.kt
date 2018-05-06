package com.khpi.classschedule.di.modules

import com.khpi.classschedule.data.network.RequestApi
import com.khpi.classschedule.business.ScheduleManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Alexander Serpokrylow on 10.01.2018.
 */

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideRequestApi(retrofit: Retrofit): RequestApi =
            retrofit.create(RequestApi::class.java)

    @Provides
    @Singleton
    internal fun provideUserRequest(requestApi: RequestApi): ScheduleManager = ScheduleManager(requestApi)

}