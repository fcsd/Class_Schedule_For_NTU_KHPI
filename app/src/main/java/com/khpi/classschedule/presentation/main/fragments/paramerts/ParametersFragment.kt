package com.khpi.classschedule.presentation.main.fragments.paramerts


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_parameters.*
import com.khpi.classschedule.Constants
import com.khpi.classschedule.BuildConfig
import com.khpi.classschedule.data.models.Developer
import android.content.Intent
import android.net.Uri


class ParametersFragment : BaseFragment(), ParametersView {

    override var TAG = "ParametersFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: ParametersPresenter
    //@formatter:on

    companion object {
        fun newInstance(): ParametersFragment = ParametersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parameters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {

        val ctx = context ?: return

        (activity as? MainActivity)?.setToolbarTitle(getString(R.string.settings))
        presenter.loadDefaultValue()

        settings_invert_switch.setOnCheckedChangeListener({ _, isChecked ->
            presenter.changeSwitchValue(Constants.INVERT, isChecked)
        })
        settings_update_switch.setOnCheckedChangeListener({ _, isChecked ->
            presenter.changeSwitchValue(Constants.EVERYDAY_UPDATE, isChecked)
        })
        settings_vibrate_switch.setOnCheckedChangeListener({ _, isChecked ->
            presenter.changeSwitchValue(Constants.VIBRATE, isChecked)
        })
        settings_sound_switch.setOnCheckedChangeListener({ _, isChecked ->
            presenter.changeSwitchValue(Constants.SOUND, isChecked)
        })
        layout_remove_task.setOnClickListener { presenter.loadTaskAlert() }

        settings_info_content.text = ctx.resources.getString(R.string.version_content, BuildConfig.VERSION_NAME)
        text_vkontakte.text = Developer.ALEXANDER_SERPOKRYLOW.developer
        text_instagram.text = Developer.TIMUR_ABBASOV.developer
        text_facebook.text = Developer.BERK_ARSLAN.developer

        text_vkontakte.setOnClickListener { presenter.openDeveloperPage(Developer.ALEXANDER_SERPOKRYLOW.link) }
        text_instagram.setOnClickListener { presenter.openDeveloperPage(Developer.TIMUR_ABBASOV.link) }
        text_facebook.setOnClickListener { presenter.openDeveloperPage(Developer.BERK_ARSLAN.link) }
    }

    override fun setPreferencesValue(invert: Boolean, everydayUpdate: Boolean, vibrate: Boolean, sound: Boolean) {
        settings_invert_switch.isChecked = invert
        settings_update_switch.isChecked = everydayUpdate
        settings_vibrate_switch.isChecked = vibrate
        settings_sound_switch.isChecked = sound
    }

    override fun setRemoveValue(remove: String) {
        settings_remove_content.text = remove
    }

    override fun showTaskAlert(titles: Array<CharSequence>) {
        val ctx = context ?: return
        AlertDialog.Builder(ctx)
                .setTitle(ctx.resources.getString(R.string.remove_task))
                .setItems(titles, { _, position -> presenter.saveAlertSelectedPosition(position) })
                .setNegativeButton(ctx.resources.getString(R.string.cancel), null)
                .show()
    }

    override fun openDeveloperPage(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }

}
