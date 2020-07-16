package com.gsa.ui.order


import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.order.OrderListItem
import com.gsa.model.order.OrderListResponse
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.landing.home.HomeFragment
import com.gsa.ui.order.adapter.AdapterOrderList
import com.gsa.ui.order_details.OrderDetailsActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_order.*

/**
 * A simple [Fragment] subclass.
 */
class OrderFragment : BaseFragment<OrderViewModel>(OrderViewModel::class),
    AdapterViewClickListener<OrderListItem> {
    override fun getLayoutId() = R.layout.fragment_order

    companion object {

        fun getInstance(instance: Int): OrderFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = OrderFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClickAdapterView(objectAtPosition: OrderListItem, viewType: Int, position: Int) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT -> {

                activity?.let {
                    UiUtils.hideSoftKeyboard(it)
                    startActivity(
                        OrderDetailsActivity.getIntent(
                            it,
                            objectAtPosition
                        ),
                        ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                    )
                }
            }
        }
    }

    private var adapterOrderList: AdapterOrderList? = null
    internal var orderListList: ArrayList<OrderListItem>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var manager2 = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rv_orders.layoutManager = manager2
        activity?.let {
            adapterOrderList = AdapterOrderList(this, it)
            rv_orders.adapter = adapterOrderList

        }
        tvShopMore.setOnClickListener {
            activity?.let {
                startActivity(LandingNavigationActivity.getIntent(it, 1))
            }
        }
        subscribeLoading()
        subscribeUi()
        getData()
    }

    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentOrders()) {

            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.my_orders))
            (activity as LandingNavigationActivity).setBack(false)
            (activity as LandingNavigationActivity).setSync(true)

        }
    }

    public fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getOrders("Order List", model.getUserID()!!, model.getRoleID()!!)
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
        model.orderListModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    private fun showData(data: OrderListResponse?) {

        orderListList = data?.orderList
        orderListList?.let {

            adapterOrderList?.submitList(it)

            adapterOrderList?.notifyDataSetChanged()
        }
        orderListList?.let {
            if(it.size==0){
                rlNoData.visibility=View.VISIBLE

            }

        }
    }
}




