package com.gsa.ui.points


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.order.OrderListItem
import com.gsa.model.order.OrderListResponse
import com.gsa.model.points.PointListItem
import com.gsa.model.points.PointsResponse
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.order.OrderFragment
import com.gsa.ui.order.OrderViewModel
import com.gsa.ui.order.adapter.AdapterOrderList
import com.gsa.ui.points.adapter.AdapterPointsList
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.fragment_points.*

/**
 * A simple [Fragment] subclass.
 */
class PointsFragment : BaseFragment<PointsViewModel>(PointsViewModel::class),
    AdapterViewClickListener<PointListItem> {

    override fun getLayoutId() = R.layout.fragment_points
    companion object {

        fun getInstance(instance: Int): PointsFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = PointsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClickAdapterView(objectAtPosition: PointListItem, viewType: Int, position: Int) {
    }


    private var adapterPointsList: AdapterPointsList? = null
    internal var pointList: ArrayList<PointListItem>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var manager2 = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rv_points.layoutManager = manager2
        activity?.let {
            adapterPointsList = AdapterPointsList(this, it)
            rv_points.adapter = adapterPointsList

        }
        subscribeLoading()
        subscribeUi()
        getData()

    }
    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentPoints()) {

            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.my_points))
            (activity as LandingNavigationActivity).setBack(false)
        }
    }

    private fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getPoints("Point List", model.getUserID()!!, model.getRoleID()!!)
        }

    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.let {
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })


    }

    private fun subscribeUi() {
        model.pointsModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    private fun showData(data: PointsResponse?) {
        tvPoints.text=""+data?.totalPoints

        pointList = data?.pointList
        pointList?.let {

            adapterPointsList?.submitList(it)

            adapterPointsList?.notifyDataSetChanged()
        }
    }
}
