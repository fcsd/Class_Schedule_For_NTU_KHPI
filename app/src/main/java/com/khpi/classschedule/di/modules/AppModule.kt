package com.khpi.classschedule.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.khpi.classschedule.App
import com.khpi.classschedule.business.BuildingManager
import com.khpi.classschedule.data.config.MemoryRepository
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
    internal fun provideMainConfig(context: Context): MemoryRepository = MemoryRepository(context)

    @Provides
    @Singleton
    internal fun provideBuildingManager(): BuildingManager = BuildingManager()
}
