package com.gsa.ui.notification

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.notification.NotificationListItem
import com.gsa.model.notification.NotificationListResponse
import com.gsa.model.productList.ProductListItem
import com.gsa.model.productList.ProductListResponse
import com.gsa.ui.cart.CartActivity
import com.gsa.ui.notification.adapter.AdapterNotification
import com.gsa.ui.search.SearchActivity
import com.gsa.ui.search.SearchViewModel
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class NotificationActivity : BaseActivity<NotificationViewModel>(NotificationViewModel::class),
    AdapterViewClickListener<NotificationListItem> {
    override fun layout(): Int = R.layout.activity_notification


    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClickAdapterView(
        objectAtPosition: NotificationListItem,
        viewType: Int,
        position: Int
    ) {

    }
    private var adapterNotification: AdapterNotification? = null
    internal var notificationList: ArrayList<NotificationListItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationList = ArrayList()
        var manager2 = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rv_Notification.layoutManager = manager2
        let {
            adapterNotification = AdapterNotification(this, it)

        }
        rv_Notification.adapter = adapterNotification
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }

        subscribeLoading()
        subscribeUi()


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
        rlSync.setOnClickListener {
            getData()

        }
        getData()
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

    fun getData(){
        if (NetworkUtil.isInternetAvailable(this@NotificationActivity)) {
            model.getNotification(
                "Notification List",
                model.getUserID()!!,
                model.getRoleID()!!
            )
        }

    }
    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.notifications)
        iv_cart.visibility= View.VISIBLE
        rlSync.visibility=View.VISIBLE

    }

    private fun subscribeUi() {

        model.NotificationModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
    }
    private fun showData(data: NotificationListResponse?) {
        if (data!!.status) {
           notificationList=data?.notificationList

            notificationList?.let {
                if(it.size>0) {
                    adapterNotification?.submitList(it)
                    adapterNotification?.notifyDataSetChanged()
                    rlNoData.visibility=View.GONE

                }
                else{
                    rlNoData.visibility=View.VISIBLE
                }
            }
        }
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, NotificationActivity::class.java)


        }
    }
}
