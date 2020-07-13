package com.gsa.ui.order_details

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.order.ItemListItem
import com.gsa.model.order.OrderListItem
import com.gsa.ui.cart.CartActivity
import com.gsa.ui.order_details.adapter.AdapterOrderDetails
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*

class OrderDetailsActivity : AppCompatActivity(), AdapterViewClickListener<ItemListItem> {
    override fun onClickAdapterView(objectAtPosition: ItemListItem, viewType: Int, position: Int) {
    //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var adapterOrderDetails: AdapterOrderDetails? = null
    lateinit var orderListItem: OrderListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        intent?.run {
            orderListItem = getParcelableExtra(KEY_DATA)

        }

        var manager2 = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rv_orders_details.layoutManager = manager2
        let {
            adapterOrderDetails = AdapterOrderDetails(this, it)
            rv_orders_details.adapter = adapterOrderDetails
        }
        orderListItem?.itemList?.let {
            adapterOrderDetails?.submitList(it)
            ViewCompat.setNestedScrollingEnabled(rv_orders_details, false)

            adapterOrderDetails?.notifyDataSetChanged()
        }

        text_order_no?.text = orderListItem.orderNumber
        text_date_name?.text = orderListItem.cDate
        text_amount.setText("" + orderListItem.amount)
        text_status_name.text = orderListItem.status

        if(orderListItem.status.equals("New Order",true)){
            llOrderPaymet.visibility=View.GONE
            ll_amount_no.visibility=View.GONE
        }else {
            llOrderPaymet.visibility=View.VISIBLE
            text_total?.setText("" + orderListItem.amount)
            text_discount?.setText("" + 0.0)
            text_grand_total.setText("" + orderListItem.saleAmount)
            text_dispatched_amount.text = orderListItem.amount
        }
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        rlCart.setOnClickListener {

            let {
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    CartActivity.getIntent(
                        it
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                )
            }
        }

    }

    override fun onResume() {
        super.onResume()
        rlCart.visibility= View.GONE
        tv_tool_title.text=AndroidUtils.getString(R.string.order_details)
    }
    companion object {
        const val KEY_DATA = "KEY__DATA"

        fun getIntent(context: Context?, item: OrderListItem?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, OrderDetailsActivity::class.java).putExtra(
                KEY_DATA,
                item
            )

        }
    }

}
