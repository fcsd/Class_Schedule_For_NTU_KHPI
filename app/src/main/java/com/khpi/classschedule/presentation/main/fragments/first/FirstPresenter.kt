package com.khpi.classschedule.presentation.main.fragments.first

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class FirstPresenter : BasePresenter<FirstView>() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun confirmType(prefix: String) {
        settingsRepository.saveUserPrefix(prefix)
        viewState.openCategoryScreen()
    }
}