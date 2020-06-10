package com.gsa.ui.landing.home


import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.callbacks.AdapterViewCompanyClickListener
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.CompanyListItem
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.home.categories.CategoryListItem
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.landing.home.adapter.AdapterFeatureProduct
import com.gsa.ui.landing.home.adapter.AdapterHomeCategories
import com.gsa.ui.landing.home.adapter.AdapterHomeCompanies
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*

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
    }

    override fun onClickCompanyAdapterView(
        objectAtPosition: CompanyListItem,
        viewType: Int,
        position: Int
    ) {
    }

    override fun getLayoutId() = R.layout.fragment_home
    private var adapterCategories: AdapterHomeCategories? = null
    private var adapterFeatureProduct: AdapterFeatureProduct? = null

    private var adapterCompanies: AdapterHomeCompanies? = null
    internal var categoriesList: List<CategoryListItem>? = null
    internal var companyList: List<CompanyListItem>? = null
    internal var featureProductList: List<FeatureProductListItem>? = null

    override fun onClickAdapterView(
        objectAtPosition: CategoryListItem,
        viewType: Int,
        position: Int
    ) {
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

        var manager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        rv_categories.layoutManager = manager

        var manager1 = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
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
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    private fun showData(data: CategoriesListResponse?) {

        categoriesList = data?.categoryList
        categoriesList?.let {
            adapterCategories?.submitList(it)
            ViewCompat.setNestedScrollingEnabled(rv_categories, false)
            adapterCategories?.notifyDataSetChanged()
        }
    }

    private fun showData(data: CompaniesListResponse?) {
        companyList = data?.companyList
        companyList?.let {
            adapterCompanies?.submitList(it)
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
}
