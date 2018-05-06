package com.khpi.classschedule.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.presentation.base.BasePresenter

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()
    }
}