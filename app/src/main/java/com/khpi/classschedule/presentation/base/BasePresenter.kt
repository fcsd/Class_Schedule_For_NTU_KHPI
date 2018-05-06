package com.khpi.classschedule.presentation.base

import com.arellomobile.mvp.MvpPresenter
import com.khpi.classschedule.App
import com.khpi.classschedule.di.AppComponent


/**
 * Created by Alexander Serpokrylow on 26.12.2017.
 */

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    protected fun injector(): AppComponent = App.dagger

    abstract fun onViewLoaded()

}