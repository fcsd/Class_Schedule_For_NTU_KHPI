package com.khpi.classschedule.presentation.base

import com.arellomobile.mvp.MvpAppCompatFragment

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

    override fun showProgressDialog() {
        (activity as? BaseView)?.showProgressDialog()
    }

    override fun dismissProgressDialog() {
        (activity as? BaseView)?.dismissProgressDialog()
    }

    override fun notifyDataSetChanged() {
        (activity as? BaseView)?.notifyDataSetChanged()
    }

}