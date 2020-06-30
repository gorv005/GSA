package com.gsa.ui.productList

import android.app.ActivityOptions
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
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.cart.CartListItem
import com.gsa.model.companyCategoryList.CompanyCategoryList
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.productList.ProductListItem
import com.gsa.model.productList.ProductListResponse
import com.gsa.ui.cart.CartActivity
import com.gsa.ui.cart.CartViewModel
import com.gsa.ui.companyCategoryList.CompanyCategoryListActivity
import com.gsa.ui.companyCategoryList.adapter.AdapterCompanyCategories
import com.gsa.ui.companyList.CompanyListViewModel
import com.gsa.ui.landing.home.HomeViewModel
import com.gsa.ui.landing.home.adapter.AdapterHomeCompanies
import com.gsa.ui.productList.adapter.AdapterProductList
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_company_category_list.*
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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
    var fPos: Int = 0
    var q: Int = 0
    val modelCart: CartViewModel by viewModel()

    override fun onClickFeatureProductAdapterView(
        objectAtPosition: ProductListItem,
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


    private fun updateData(objectAtPosition: ProductListItem, status: Int) {

        if (NetworkUtil.isInternetAvailable(this)) {
            if (status == 1) {
                q = objectAtPosition.CartItemQty.toInt() + 1
            } else if(status===-1){
                q=objectAtPosition.CartItemQty.toInt()
            }
            else {
                q = objectAtPosition.CartItemQty.toInt() - 1

            }
            if (status === 0 && objectAtPosition.CartItemQty.toInt() === 0) {

            } else {
                modelCart.addToCart(
                    "Add Cart", model.getUserID()!!, model.getRoleID()!!,
                    objectAtPosition.id, "" + q, objectAtPosition.pMrp
                )
            }
        }

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

        modelCart.searchEvent.observe(this, Observer {
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
        modelCart.addToCartModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })
    }
    private fun showData(data: AddToCartResponse?) {
        if (data!!.status) {
            productList?.get(fPos)?.CartItemQty = q

            productList?.let {
                adapterProductList?.submitList(it)
                adapterProductList?.notifyDataSetChanged()
            }
        }
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
