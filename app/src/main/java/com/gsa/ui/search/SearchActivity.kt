package com.gsa.ui.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.productList.ProductListItem
import com.gsa.model.productList.ProductListResponse
import com.gsa.ui.cart.CartViewModel
import com.gsa.ui.productList.ProductListActivity
import com.gsa.ui.productList.ProductListViewModel
import com.gsa.ui.productList.adapter.AdapterProductList
import com.gsa.util.UiUtils
import com.gsa.utils.*
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.app_custom_tool_bar_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchActivity : BaseActivity<SearchViewModel>(SearchViewModel::class),
    AdapterViewFeatureProductClickListener<ProductListItem> {
    override fun layout(): Int = R.layout.activity_search


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
                if(model.getIsSalesMan()){
                    modelCart.addToCart(
                        "Add Cart", model.getUserID()!!, model.getRoleID()!!,
                        objectAtPosition.id, "" + q, objectAtPosition.pMrp,model.getRetailerID()!!
                    )
                }else {
                    modelCart.addToCart(
                        "Add Cart", model.getUserID()!!, model.getRoleID()!!,
                        objectAtPosition.id, "" + q, objectAtPosition.pMrp
                    )
                }
            }
        }

    }

    private var adapterProductList: AdapterProductList? = null
    internal var productList: ArrayList<ProductListItem>? = null
    var company_id: String?=null
    var cat_id: String?=null
    var fPos: Int = 0
    var q: Int = 0
    val modelCart: CartViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productList = ArrayList()

        var manager2 = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rv_products.layoutManager = manager2

        intent?.run {

            company_id = getStringExtra(ProductListActivity.KEY_COM_ID)
            cat_id = getStringExtra(ProductListActivity.KEY_CAT_ID)

        }
        let {
            adapterProductList = AdapterProductList(this, it)

        }
        rv_products.adapter = adapterProductList
        fl_left_img_container_search.setOnClickListener {
            onBackPressed()
        }
        sv_product.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.length>=2) {
                    if (NetworkUtil.isInternetAvailable(this@SearchActivity)) {
                        model.getProducts(
                            "Product List",
                            model.getUserID()!!,
                            model.getRoleID()!!,
                            company_id!!,
                            cat_id!!,
                            newText
                        )
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

        })
        subscribeLoading()
        subscribeUi()
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
/*
    private fun setUpSearchObservable() {
        RxSearchObservable.fromView(sv_product)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter(Predicate<String>() {


            })
            .distinctUntilChanged()
            .switchMap(object : Function<String, ObservableSource<String>>() {
                fun apply(query: String): ObservableSource<String> {
                    return dataFromNetwork(query)
                        .doOnError({ throwable ->
                            // handle error
                        })
                        // continue emission in case of error also
                        .onErrorReturn({ throwable -> "" })
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<String>() {
                fun accept(result: String) {
                    textViewResult.setText(result)
                }
            })
    }
*/

    companion object {

        const val KEY_COM_ID = "KEY_COM_ID"
        const val KEY_CAT_ID = "KEY_CAT_ID"

        fun getIntent(context: Context?, com_d: String?, cat_d: String?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, SearchActivity::class.java).putExtra(
                KEY_COM_ID,
                com_d
            ).putExtra(KEY_CAT_ID,cat_d)


        }
    }
}
