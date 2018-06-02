package com.khpi.classschedule.presentation.main.fragments.first

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.khpi.classschedule.Constants

import com.khpi.classschedule.R
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : BaseFragment(), FirstView {

    override var TAG = "FirstFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: FirstPresenter
    //@formatter:on

    companion object {
        fun newInstance(): FirstFragment = FirstFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun configureView() {
        val ctx = context ?: return
        (activity as? MainActivity)?.setNavigationVisibility(false)
        (activity as? MainActivity)?.setToolbarTitle(getString(R.string.choose_youself))
        (activity as? MainActivity)?.setRightSecondNavigationIcon(ContextCompat.getDrawable(ctx, R.drawable.ic_apply))
        (activity as? MainActivity)?.setRightSecondClickListener {
            when (radio_type.checkedRadioButtonId) {
                R.id.radio_student -> presenter.confirmType(Constants.GROUP_PREFIX)
                R.id.radio_teacher -> presenter.confirmType(Constants.TEACHER_PREFIX)
            }
        }
    }

    override fun openCategoryScreen() {
        (activity as? MainActivity)?.setNavigationVisibility(true)
        (activity as? MainActivity)?.openCategory()
    }
}
