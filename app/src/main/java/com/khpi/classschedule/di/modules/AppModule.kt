package com.khpi.classschedule.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.khpi.classschedule.App
import com.khpi.classschedule.business.BuildingManager
import com.khpi.classschedule.data.config.ScheduleRepository
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.data.config.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Provides
    @Singleton
    internal fun provideContext(): Context = this.application

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
                .create()
    }

    @Provides
    @Singleton
    internal fun provideScheduleRepository(context: Context, gson: Gson): ScheduleRepository = ScheduleRepository(context, gson)

    @Provides
    @Singleton
    internal fun provideTaskRepository(context: Context, gson: Gson): TaskRepository = TaskRepository(context, gson)

    @Provides
    @Singleton
    internal fun provideSettingsRepository(context: Context): SettingsRepository = SettingsRepository(context)

    @Provides
    @Singleton
    internal fun provideBuildingManager(): BuildingManager = BuildingManager()
}
