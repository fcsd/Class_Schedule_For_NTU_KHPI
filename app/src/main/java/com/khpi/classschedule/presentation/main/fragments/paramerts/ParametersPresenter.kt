package com.khpi.classschedule.presentation.main.fragments.paramerts

import com.arellomobile.mvp.InjectViewState
import com.khpi.classschedule.Constants
import com.khpi.classschedule.data.config.SettingsRepository
import com.khpi.classschedule.data.models.TaskRemove
import com.khpi.classschedule.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class ParametersPresenter : BasePresenter<ParametersView>() {

    @Inject lateinit var settingsRepository: SettingsRepository

    init {
        injector().inject(this)
    }

    override fun onViewLoaded() {
        viewState.configureView()
    }

    fun loadDefaultValue() {
        val invert = settingsRepository.getPreferenceByKey(Constants.INVERT)
        val everydayUpdate = settingsRepository.getPreferenceByKey(Constants.EVERYDAY_UPDATE)
        val vibrate = settingsRepository.getPreferenceByKey(Constants.VIBRATE)
        val sound = settingsRepository.getPreferenceByKey(Constants.SOUND)
        val remove = TaskRemove.values()[settingsRepository.getRemovePosition()].title
        val prefix = settingsRepository.getUserPrefix()
        viewState.setPreferencesValue(invert, everydayUpdate, vibrate, sound, prefix)
        viewState.setRemoveValue(remove)
    }

    fun changeSwitchValue(key: String, value: Boolean) {
        settingsRepository.savePreferenceByKey(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    fun loadTaskAlert() {
        val titles = TaskRemove.values().map { it.title }.toTypedArray() as Array<CharSequence>
        viewState.showTaskAlert(titles)
    }

    fun saveAlertSelectedPosition(position: Int) {
        settingsRepository.saveRemovePosition(position)
        val remove = TaskRemove.values()[position].title
        viewState.setRemoveValue(remove)
    }

    fun openDeveloperPage(link: String) {
        viewState.openDeveloperPage(link)
    }

    fun onTypeChanged(prefix: String) {
        settingsRepository.saveUserPrefix(prefix)
    }
}