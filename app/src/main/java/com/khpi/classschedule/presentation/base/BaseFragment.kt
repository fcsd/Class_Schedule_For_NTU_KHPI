package com.khpi.classschedule.presentation.base

import com.arellomobile.mvp.MvpAppCompatFragment
import com.khpi.classschedule.data.models.Task

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    abstract var TAG: String

    override fun onBackPressed() {
        (activity as? BaseActivity)?.goBack()
    }

    override fun showError(message: String) {
        (activity as? BaseView)?.showError(message)
    }

    override fun showMessage(message: String) {
        (activity as? BaseView)?.showMessage(message)
    }

    override fun setCustomProgressBarVisibility(visibility: Boolean) {
        (activity as? BaseView)?.setCustomProgressBarVisibility(visibility)
    }

    override fun notifyDataSetChanged() {
        (activity as? BaseView)?.notifyDataSetChanged()
    }

    override fun overrideStartAnimation() {
        (activity as? BaseView)?.overrideStartAnimation()
    }

    override fun overrideBackAnimation() {
        (activity as? BaseView)?.overrideStartAnimation()
    }

    override fun disableTaskNotification(task: Task) {
        (activity as? BaseView)?.disableTaskNotification(task)
    }
}