package com.gsa.ui.productList

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.callbacks.AdapterViewCompanyClickListener
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.companyCategoryList.CompanyCategoryList
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.productList.ProductListItem
import com.gsa.model.productList.ProductListResponse
import com.gsa.ui.companyCategoryList.CompanyCategoryListActivity
import com.gsa.ui.companyCategoryList.adapter.AdapterCompanyCategories
import com.gsa.ui.companyList.CompanyListViewModel
import com.gsa.ui.landing.home.adapter.AdapterHomeCompanies
import com.gsa.ui.productList.adapter.AdapterProductList
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_company_category_list.*
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*

class ProductListActivity : BaseActivity<ProductListViewModel>(ProductListViewModel::class),
    AdapterViewFeatureProductClickListener<ProductListItem> {

    override fun layout(): Int = R.layout.activity_product_list

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private var adapterProductList: AdapterProductList? = null
    internal var productList: ArrayList<ProductListItem>? = null
    var company_id: String?=null
    var cat_id: String?=null

    override fun onClickFeatureProductAdapterView(
        objectAtPosition: ProductListItem,
        viewType: Int,
        position: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productList = ArrayList()

        var manager2 = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rv_products.layoutManager = manager2

        intent?.run {

            company_id = getStringExtra(KEY_COM_ID)
            cat_id = getStringExtra(KEY_CAT_ID)

        }
        let {
            adapterProductList = AdapterProductList(this, it)

        }
        rv_products.adapter = adapterProductList
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        subscribeLoading()
        subscribeUi()
        getData()
    }

    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.shop_by_category)

    }

    private fun getData() {


        if (NetworkUtil.isInternetAvailable(this)) {
            model.getProducts("Product List", model.getUserID()!!, model.getRoleID()!!,company_id!!,cat_id!!,"")
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

        model.productModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun showData(data: ProductListResponse?) {

        productList = data?.productList
        productList?.let {

            adapterProductList?.submitList(it)
            adapterProductList?.notifyDataSetChanged()
        }
    }

    companion object {

        const val KEY_COM_ID = "KEY_COM_ID"
        const val KEY_CAT_ID = "KEY_CAT_ID"

        fun getIntent(context: Context?, com_d: String?,cat_d: String?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ProductListActivity::class.java).putExtra(
                KEY_COM_ID,
                com_d
            ).putExtra(KEY_CAT_ID,cat_d)


        }
    }
}
