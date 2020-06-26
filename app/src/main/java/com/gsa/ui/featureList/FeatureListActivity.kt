package com.gsa.ui.featureList

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.ui.companyList.CompanyListActivity
import com.gsa.ui.companyList.CompanyListViewModel
import com.gsa.ui.landing.home.adapter.AdapterFeatureProduct
import com.gsa.ui.landing.home.adapter.AdapterHomeCompanies
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_feature_list.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*

class FeatureListActivity : BaseActivity<FeatureListViewModel>(FeatureListViewModel::class),
    AdapterViewFeatureProductClickListener<FeatureProductListItem> {
    override fun layout(): Int = R.layout.activity_feature_list


    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClickFeatureProductAdapterView(
        objectAtPosition: FeatureProductListItem,
        viewType: Int,
        position: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private var adapterFeatureProduct: AdapterFeatureProduct? = null
    internal var featureProductList: List<FeatureProductListItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_list)
        featureProductList = ArrayList()
        val manager1 = GridLayoutManager(this, 4)
        rv_products.layoutManager = manager1


        let {
            adapterFeatureProduct = AdapterFeatureProduct(this, it)

        }
        rv_products.adapter = adapterFeatureProduct
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        subscribeLoading()
        subscribeUi()
        getData()
    }

    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.shop_by_product)
    }
    private fun getData() {


        if (NetworkUtil.isInternetAvailable(this)) {
            model.getFeatureProduct("Product List", model.getUserID()!!, model.getRoleID()!!)
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

        model.featureProductyModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })

    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun showData(data: FeatureProductResponse?) {
        featureProductList = data?.featureProductList
        featureProductList?.let {
            adapterFeatureProduct?.submitList(it)
            ViewCompat.setNestedScrollingEnabled(rv_featuredProduct, false)

            adapterFeatureProduct?.notifyDataSetChanged()
        }
    }
    companion object {


        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, FeatureListActivity::class.java)


        }
    }
}
