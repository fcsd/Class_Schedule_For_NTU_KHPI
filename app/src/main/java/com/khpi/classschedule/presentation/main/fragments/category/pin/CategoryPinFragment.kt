package com.khpi.classschedule.presentation.main.fragments.category.pin


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter

import com.khpi.classschedule.R
import com.khpi.classschedule.data.models.BaseModel
import com.khpi.classschedule.presentation.base.BaseFragment
import com.khpi.classschedule.presentation.main.fragments.category.list.CategoryListPresenter
import kotlinx.android.synthetic.main.fragment_category_pin.*

class CategoryPinFragment : BaseFragment(), CategoryPinView {

    override var TAG = "CategoryItemFragment"

    //@formatter:off
    @InjectPresenter lateinit var presenter: CategoryPinPresenter
    //@formatter:on

    private var scheduleInfo: List<BaseModel>? = null
    private var listener: CategoryListPresenter? = null
    private lateinit var generalPinAdapter: CategoryPinAdapter

    companion object {
        fun newInstance(scheduleInfo: List<BaseModel>, listener: CategoryListPresenter): CategoryPinFragment = CategoryPinFragment().apply {
            this.scheduleInfo = scheduleInfo
            this.listener = listener
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewLoaded()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_pin, container, false)
    }

    override fun configureView() {
       presenter.setPinScheduleInfo(scheduleInfo)
    }

    override fun showPinScheduleInfo(scheduleInfo: MutableList<BaseModel>) {
        listener?.let {
            generalPinAdapter = CategoryPinAdapter(scheduleInfo, it)
            recycler_general_pin.layoutManager = LinearLayoutManager(context)
            recycler_general_pin.adapter = generalPinAdapter
        }
    }

    override fun notifyDataSetChanged() {
        generalPinAdapter.notifyDataSetChanged()
    }
}
