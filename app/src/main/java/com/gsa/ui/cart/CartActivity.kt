package com.gsa.ui.cart

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.cart.CartListItem
import com.gsa.model.cart.CartListResponse
import com.gsa.ui.cart.adapter.AdapterCartProduct
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.dialog_custom.view.*

class CartActivity : BaseActivity<CartViewModel>(CartViewModel::class),
    AdapterViewFeatureProductClickListener<CartListItem> {
    override fun onClickFeatureProductAdapterView(
        objectAtPosition: CartListItem,
        viewType: Int,
        position: Int
    ) {
        fPos = position
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_QUANTITY_CHANGED -> {

                let {
                    updateData(objectAtPosition, -1)

                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_MINUS_PRODUCT -> {

                let {
                    updateData(objectAtPosition, 0)
                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_PLUS_PRODUCT -> {

                let {
                    updateData(objectAtPosition, 1)

                }
            }
        }
    }

    private fun updateData(objectAtPosition:CartListItem , status: Int) {

        if (NetworkUtil.isInternetAvailable(this)) {
            if (status == 1) {
                q = objectAtPosition.itemQty.toInt() + 1
            } else if(status===-1){
                q=objectAtPosition.itemQty.toInt()
            }
            else {
                q = objectAtPosition.itemQty.toInt() - 1

            }
            if (status === 0 && objectAtPosition.itemQty.toInt() === 0) {

            } else {
                model.addToCart(
                    "Add Cart", model.getUserID()!!, model.getRoleID()!!,
                    objectAtPosition.itemId, "" + q, objectAtPosition.itemAmount
                )
            }
        }

    }

    override fun layout(): Int = R.layout.activity_cart


    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var adapterFeatureProduct: AdapterCartProduct? = null
    internal var cartList: ArrayList<CartListItem>? = null
    var fPos: Int = 0
    var q: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartList = ArrayList()

        var manager2 = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rv_cart.layoutManager = manager2
        let {
            adapterFeatureProduct = AdapterCartProduct(this, it)
            rv_cart.adapter = adapterFeatureProduct

        }
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        rlBuyNow.setOnClickListener {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.orderPlace("Add Order", model.getUserID()!!, model.getRoleID()!!)
            }

        }
        tvShopMore.setOnClickListener {
            startActivity(LandingNavigationActivity.getIntent(this, 1))

        }
        rlSync.setOnClickListener {
            getData()

        }
        subscribeLoading()
        subscribeUi()
        getData()
    }

    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.cart)
        iv_cart.visibility=View.GONE
        rlSync.visibility=View.VISIBLE

    }


    private fun getData() {

        if (NetworkUtil.isInternetAvailable(this)) {
            model.cartList("Cart List", model.getUserID()!!, model.getRoleID()!!)
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
                UiUtils.showInternetDialog(this, R.string.something_went_wrong)
            }
        })


    }

    private fun subscribeUi() {
        model.cartListModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })

        model.addToCartModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })

        model.orderPlaceModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if(it.status) {
                customAlertDialog(it.message)
            }
            else{
                showSnackbar(
                    it.message,
                    false
                )
            }
        })
    }

    private fun showData(data: CartListResponse?) {

        cartList = data?.cartList
        setData()
        cartList?.let {
            adapterFeatureProduct?.submitList(it)

            adapterFeatureProduct?.notifyDataSetChanged()
        }
    }

    private fun showData(data: AddToCartResponse?) {
        if (data!!.status) {
            cartList?.get(fPos)?.itemQty = q
        }
        if(q==0){
            cartList?.removeAt(fPos)
        }
        setData()
        cartList?.let {
            adapterFeatureProduct?.submitList(it)
            adapterFeatureProduct?.notifyDataSetChanged()
        }
    }


    fun setData() {

        var i: Int = 0
        var r: Double = 0.0
        var h: Double
        while (i < cartList!!.size) {
            h = (cartList!!.get(i).itemAmount).toDouble() * (cartList!!.get(i).itemQty)
            r = r + h
            i++
        }

        cartList?.let {
            if(it.size>0){
                llPaylow.visibility=View.GONE
                llCheckout.visibility=View.VISIBLE
            }else{
                rlNoData.visibility=View.VISIBLE
                llCheckout.visibility=View.GONE

            }

        }

        txt_charges.text = r.toString()
        txt_promotion.text = "0.0"
        txt_grand_total.text = r.toString()
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    companion object {


        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, CartActivity::class.java)


        }
    }


    fun customAlertDialog(m: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@CartActivity)
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = inflater.inflate(R.layout.dialog_custom, null, false)

        builder.setView(customView)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        customView.tvMessage.text = m
        customView.tvTitle.text = AndroidUtils.getString(R.string.order_placed)
        customView.tvSuccess.text = AndroidUtils.getString(R.string.goto_orders)

        customView.tvSuccess.setOnClickListener({
            alertDialog.dismiss()
            startActivity(LandingNavigationActivity.getIntent(this, 2))

            // gotoLogin()
        })


    }


}
