package com.gsa.ui.companyCategoryList

import android.app.ActivityOptions
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
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.companyCategoryList.CompanyCategoryList
import com.gsa.model.companyCategoryList.CompanyCategoryListItem
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.home.categories.CategoryListItem
import com.gsa.model.productList.ProductListItem
import com.gsa.model.productList.ProductListResponse
import com.gsa.ui.CategoryList.CategoryListActivity
import com.gsa.ui.CategoryList.CategoryListViewModel
import com.gsa.ui.cart.CartActivity
import com.gsa.ui.companyCategoryList.adapter.AdapterCompanyCategories
import com.gsa.ui.landing.home.adapter.AdapterHomeCategories
import com.gsa.ui.productList.ProductListActivity
import com.gsa.ui.productList.adapter.AdapterProductList
import com.gsa.ui.search.SearchActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_company_category_list.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*

class CompanyCategoryListActivity : BaseActivity<CompanyCategoryListViewModel>(CompanyCategoryListViewModel::class),
    AdapterViewClickListener<CompanyCategoryListItem> {
    override fun layout(): Int = R.layout.activity_company_category_list

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    internal var categoryList: ArrayList<CompanyCategoryListItem>? = null
    private var adapterCompanyCategories: AdapterCompanyCategories? = null
    var company_id: String?=null
    internal var productList: ArrayList<ProductListItem>? = null
    private var adapterProductList: AdapterProductList? = null

    override fun onClickAdapterView(
        objectAtPosition: CompanyCategoryListItem,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY -> {

                let {

                    let {
                        UiUtils.hideSoftKeyboard(it)
                        startActivity(
                            ProductListActivity.getIntent(
                                it,company_id,objectAtPosition.categoryId
                            ),
                            ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                        )
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryList = ArrayList()

        val manager1 = GridLayoutManager(this, 4)
        rv_company_categories.layoutManager = manager1

        intent?.run {

            company_id = getStringExtra(KEY_COM_ID)
        }
        let {
            adapterCompanyCategories = AdapterCompanyCategories(this, it)

        }
        rv_company_categories.adapter = adapterCompanyCategories
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
        tvCategory.setOnClickListener {
            let {
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    SearchActivity.getIntent(
                        it,company_id,""
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                )
            }
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
        tv_tool_title.text = AndroidUtils.getString(R.string.shop_by_product)
        rlSync.visibility= View.VISIBLE
        if (!model.getCartValue().toString()!!.equals("0")
            &&!model.getCartValue().toString()!!.equals("")) {
            ivCounter.visibility = View.VISIBLE
            ivCounter.setText(
                model.getCartValue().toString()
            )


        } else {
            ivCounter.visibility = View.INVISIBLE

        }
    }

    private fun getData() {


        if (NetworkUtil.isInternetAvailable(this)) {
            model.getProducts("Product List", model.getUserID()!!, model.getRoleID()!!,company_id!!)
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

        model.categoryModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
        model.productModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun showData(data: CompanyCategoryList?) {

        categoryList = data?.categoryList
        categoryList?.let {

            adapterCompanyCategories?.submitList(it)
            adapterCompanyCategories?.notifyDataSetChanged()
        }
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

        fun getIntent(context: Context?,cat_d: String?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, CompanyCategoryListActivity::class.java).putExtra(
                KEY_COM_ID,
                cat_d
            )


        }
    }
}
