package com.khpi.classschedule

import android.app.Application
import com.khpi.classschedule.di.AppComponent
import com.khpi.classschedule.di.DaggerAppComponent
import com.khpi.classschedule.di.modules.AppModule

class App : Application() {

    companion object {
        lateinit var dagger: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        dagger = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}