package com.gsa.ui.featureList

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.base.StoreProducts
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.productList.ProductListItem
import com.gsa.ui.cart.CartActivity
import com.gsa.ui.cart.CartViewModel
import com.gsa.ui.companyList.CompanyListActivity
import com.gsa.ui.companyList.CompanyListViewModel
import com.gsa.ui.landing.home.adapter.AdapterFeatureProduct
import com.gsa.ui.landing.home.adapter.AdapterHomeCompanies
import com.gsa.ui.search.SearchActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_feature_list.*
import kotlinx.android.synthetic.main.activity_feature_list.rv_products
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeatureListActivity : BaseActivity<FeatureListViewModel>(FeatureListViewModel::class),
    AdapterViewFeatureProductClickListener<ProductListItem> {
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

    private var adapterFeatureProduct: AdapterFeatureProduct? = null
    internal var featureProductList: List<ProductListItem>? = null
    val modelCart: CartViewModel by viewModel()
    var fPos: Int = 0
    var q: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_list)
        featureProductList = ArrayList()
        var manager2 = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rv_products.layoutManager = manager2


        let {
            adapterFeatureProduct = AdapterFeatureProduct(this, it)

        }
        rv_products.adapter = adapterFeatureProduct
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
        tvProduct.setOnClickListener {

                let {
                    UiUtils.hideSoftKeyboard(it)
                    startActivity(
                        SearchActivity.getIntent(
                            it,"",""
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

        model.featureProductyModel.observe(this, Observer {
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


                var cartValue=model.getCartValue()
                if (q == 0) {
                    cartValue=cartValue?.minus(1)
                    model.saveCartValue(cartValue)

                }else   if (q == 1) {
                    cartValue=cartValue?.plus(1)
                    model.saveCartValue(cartValue)

                }
            ivCounter.setText(cartValue.toString())
            featureProductList?.get(fPos)?.CartItemQty = q


            StoreProducts.getInstance().addProduct(featureProductList?.get(fPos))

            featureProductList?.let {
                adapterFeatureProduct?.submitList(it)
                adapterFeatureProduct?.notifyDataSetChanged()
            }
        }
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun showData(data: FeatureProductResponse?) {
        featureProductList = data?.featureProductList
        StoreProducts.getInstance().saveProducts(featureProductList)
        featureProductList?.let {
            adapterFeatureProduct?.submitList(it)
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
