package com.gsa.ui.landing.home


import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.callbacks.AdapterViewCompanyClickListener
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.CompanyListItem
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.home.categories.CategoryListItem
import com.gsa.ui.CategoryList.CategoryListActivity
import com.gsa.ui.cart.CartViewModel
import com.gsa.ui.companyCategoryList.CompanyCategoryListActivity
import com.gsa.ui.companyList.CompanyListActivity
import com.gsa.ui.featureList.FeatureListActivity
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.landing.home.adapter.AdapterFeatureProduct
import com.gsa.ui.landing.home.adapter.AdapterHomeCategories
import com.gsa.ui.landing.home.adapter.AdapterHomeCompanies
import com.gsa.ui.productList.ProductListActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class),
    AdapterViewClickListener<CategoryListItem>, AdapterViewCompanyClickListener<CompanyListItem>,
    AdapterViewFeatureProductClickListener<FeatureProductListItem> {
    override fun onClickFeatureProductAdapterView(
        objectAtPosition: FeatureProductListItem,
        viewType: Int,
        position: Int
    ) {
        fPos=position
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_FEATURE_PRODUCT -> {

                let {

                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_MINUS_PRODUCT -> {

                let {
                    updateData(objectAtPosition,0)
                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_PLUS_PRODUCT -> {

                let {
                    updateData(objectAtPosition,1)

                }
            }
        }
    }


    override fun onClickCompanyAdapterView(
        objectAtPosition: CompanyListItem,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_COMPANIES -> {

                let {
                    activity?.let {
                        UiUtils.hideSoftKeyboard(it)
                        startActivity(
                            CompanyCategoryListActivity.getIntent(
                                it, objectAtPosition.id
                            ),
                            ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                        )
                    }

                }
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_home
    private var adapterCategories: AdapterHomeCategories? = null
    private var adapterFeatureProduct: AdapterFeatureProduct? = null
    val modelCart: CartViewModel by viewModel()

    private var adapterCompanies: AdapterHomeCompanies? = null
    internal var categoriesList: ArrayList<CategoryListItem>? = null
    internal var companyList: ArrayList<CompanyListItem>? = null
    internal var featureProductList: List<FeatureProductListItem>? = null
    var fPos:Int=0
    var q: Int=0
    override fun onClickAdapterView(
        objectAtPosition: CategoryListItem,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY -> {

                let {

                    activity?.let {
                        UiUtils.hideSoftKeyboard(it)
                        startActivity(
                            ProductListActivity.getIntent(
                                it, "", objectAtPosition.id
                            ),
                            ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                        )
                    }
                }
            }
        }
    }

    private fun updateData(objectAtPosition: FeatureProductListItem,status: Int) {

        if (NetworkUtil.isInternetAvailable(activity)) {
            if(status==1) {
                 q = objectAtPosition.CartItemQty + 1
            }
            else{
                 q = objectAtPosition.CartItemQty - 1

            }
            if(status==-1 && objectAtPosition.CartItemQty==0) {

            }
           else {
                modelCart.addToCart(
                    "Add Cart", model.getUserID()!!, model.getRoleID()!!,
                    objectAtPosition.id, "" + q, objectAtPosition.pMrp
                )
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoriesList = ArrayList()
        companyList = ArrayList()
        featureProductList = ArrayList()

    }

    companion object {

        fun getInstance(instance: Int): HomeFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = GridLayoutManager(context, 4)

        rv_categories.layoutManager = manager

        val manager1 = GridLayoutManager(context, 4)

        rv_companies.layoutManager = manager1


        var manager2 = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rv_featuredProduct.layoutManager = manager2



        activity?.let {
            adapterCompanies = AdapterHomeCompanies(this, it)

        }
        rv_companies.adapter = adapterCompanies

        activity?.let {
            adapterCategories = AdapterHomeCategories(this, it)

        }
        rv_categories.adapter = adapterCategories

        activity?.let {
            adapterFeatureProduct = AdapterFeatureProduct(this, it)

        }

        tvSeeAllCompany.setOnClickListener {

            activity?.let {
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    CompanyListActivity.getIntent(
                        it
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                )
            }
        }
        tvSeeAllCategories.setOnClickListener {

            activity?.let {
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    CategoryListActivity.getIntent(
                        it
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                )
            }
        }
        tvSeeProducts.setOnClickListener {

            activity?.let {
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    FeatureListActivity.getIntent(
                        it
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                )
            }
        }

        rv_featuredProduct.adapter = adapterFeatureProduct
        subscribeLoading()
        subscribeUi()
        getData()
    }

    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentHome()) {

            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.welcome)+ " " +model.getUserName())
            (activity as LandingNavigationActivity).setBack(false)
        }
    }

    private fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCategories("Category List", model.getUserID()!!, model.getRoleID()!!)
        }
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCompanies("Company List", model.getUserID()!!, model.getRoleID()!!)
        }
        if (NetworkUtil.isInternetAvailable(activity)) {
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
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })

        modelCart.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.let {
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })

    }

    private fun subscribeUi() {
        model.categoryModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
        model.companyModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })
        model.featureProductyModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })
        modelCart.addToCartModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    private fun showData(data: CategoriesListResponse?) {

        categoriesList = data?.categoryList
        categoriesList?.let {
            if(it.size>4){
                it.subList(4,it.size).clear()
                adapterCategories?.submitList(it)

            }else {
                adapterCategories?.submitList(it)
            }
            ViewCompat.setNestedScrollingEnabled(rv_categories, false)
            adapterCategories?.notifyDataSetChanged()
        }
    }

    private fun showData(data: CompaniesListResponse?) {
        companyList = data?.companyList
        companyList?.let {
            if(it.size>4){
                it.subList(4,it.size).clear()
                adapterCompanies?.submitList(it)

            }else {
                adapterCompanies?.submitList(it)
            }
            ViewCompat.setNestedScrollingEnabled(rv_companies, false)

            adapterCompanies?.notifyDataSetChanged()
        }
    }

    private fun showData(data: FeatureProductResponse?) {
        featureProductList = data?.featureProductList
        featureProductList?.let {
            adapterFeatureProduct?.submitList(it)
            ViewCompat.setNestedScrollingEnabled(rv_featuredProduct, false)

            adapterFeatureProduct?.notifyDataSetChanged()
        }
    }
    private fun showData(data: AddToCartResponse?) {
        if(data!!.status){
            featureProductList?.get(fPos)?.CartItemQty=q
        }
        featureProductList?.let {
            adapterFeatureProduct?.submitList(it)
            ViewCompat.setNestedScrollingEnabled(rv_featuredProduct, false)
            adapterFeatureProduct?.notifyDataSetChanged()
        }
    }

}
