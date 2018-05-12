package com.khpi.classschedule.presentation.base

import com.arellomobile.mvp.MvpAppCompatFragment
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable



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

    override fun overrideStartAnimation() {
        (activity as? BaseView)?.overrideStartAnimation()
    }

    override fun overrideBackAnimation() {
        (activity as? BaseView)?.overrideStartAnimation()
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

}