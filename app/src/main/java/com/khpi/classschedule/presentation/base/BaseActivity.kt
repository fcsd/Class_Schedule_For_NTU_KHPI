package com.khpi.classschedule.presentation.base

import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import android.view.ViewStub
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.khpi.classschedule.R
import com.khpi.classschedule.views.SnackbarMessenger
import kotlin.reflect.KClass

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    private var snackbarMessenger: SnackbarMessenger? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(R.layout.container_base)

        snackbarMessenger = SnackbarMessenger(findViewById(R.id.coordinatorLayout))

        val vs = findViewById<ViewStub>(R.id.vsContent)
        vs.layoutResource = layoutResID
        vs.inflate()
    }

    fun replaceFragment(@IdRes contentId: Int, fragment: BaseFragment, isNeedClearBackStack: Boolean = false) {

        if ((supportFragmentManager.findFragmentByTag(fragment.TAG) as? BaseFragment)?.isVisible == true) {
            return
        }

        if (isNeedClearBackStack) {
            clearBackStack()
        }

        supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out)
                .replace(contentId, fragment, fragment.TAG)
                .commit()
    }

    private fun clearBackStack() {
        val range = 0 until supportFragmentManager.backStackEntryCount
        for (i in range) {
            supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun showError(message: String) {
        snackbarMessenger?.showErrorSnackBar(message)
    }

    override fun showMessage(message: String) {
        snackbarMessenger?.showMessageSnackBar(message)
    }

    fun hideKeyboard() {
        val view = currentFocus
        view?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        (supportFragmentManager.fragments.firstOrNull() as? BaseView)?.onBackPressed() ?: goBack()
    }

    override fun overrideStartAnimation() {
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    override fun overrideBackAnimation() {
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    fun goBack() {
        this.hideKeyboard()
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
        overrideBackAnimation()
    }

    override fun notifyDataSetChanged() {
        // nothing to override
    }
}
