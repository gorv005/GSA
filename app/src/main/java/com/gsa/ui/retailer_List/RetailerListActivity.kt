package com.gsa.ui.retailer_List

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.reatilter_list.RetailerlListItem
import com.gsa.model.reatilter_list.RetailterListResponse
import com.gsa.ui.retailer_List.adapter.AdapterRetailor
import com.gsa.ui.retailer_List.adapter.AdapterRetailorList
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_retailer_list.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class RetailerListActivity : BaseActivity<RetailterListViewModel>(RetailterListViewModel::class) {


    override fun layout(): Int = R.layout.activity_retailer_list


    override fun tag(): String {
        TODO("Not yet implemented")
    }

    override fun title(): String {
        TODO("Not yet implemented")
    }

    override fun titleColor(): Int {
        TODO("Not yet implemented")
    }

    private var adapterRetailor: AdapterRetailorList? = null
    internal var retList: ArrayList<RetailerlListItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retList = ArrayList()
        var manager2 = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rv_Retailter_List.layoutManager = manager2

        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        ret_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterRetailor?.filter?.filter(newText)
                return false
            }

        })
        subscribeLoading()
        subscribeUi()
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

    fun getData() {
        if (NetworkUtil.isInternetAvailable(this@RetailerListActivity)) {
            model.getRetailorList(
                "Retailer List"
            )
        }

    }

    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.retailor_list)
        rlCart.visibility = View.GONE
        fl_left_img_container.visibility = View.GONE
        rlSync.visibility = View.VISIBLE

    }

    private fun subscribeUi() {

        model.RetailorListModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
    }

    private fun showData(data: RetailterListResponse?) {
        if (data!!.status) {
            retList = data?.retailerlList

            retList?.let {
                if (it.size > 0) {

                    let {
                        adapterRetailor = AdapterRetailorList(retList!!)

                    }
                    rv_Retailter_List.adapter = adapterRetailor
                   /* adapterRetailor?.submitList(it)
                    adapterRetailor?.notifyDataSetChanged()*/
                    rlNoData.visibility = View.GONE

                } else {
                    rlNoData.visibility = View.VISIBLE
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

            return Intent(context, RetailerListActivity::class.java)


        }
    }

/*    override fun onClickAdapterView(
        objectAtPosition: RetailerlListItem,
        viewType: Int,
        position: Int
    ) {
        TODO("Not yet implemented")
    }*/
}