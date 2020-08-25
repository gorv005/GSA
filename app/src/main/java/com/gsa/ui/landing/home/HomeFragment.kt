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
import com.gsa.base.StoreProducts
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.callbacks.AdapterViewCompanyClickListener
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.cart.CartListResponse
import com.gsa.model.favorites.FavoriteListItem
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.CompanyListItem
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.home.categories.CategoryListItem
import com.gsa.model.productList.ProductListItem
import com.gsa.model.reatilter_list.RetailerlListItem
import com.gsa.ui.CategoryList.CategoryListActivity
import com.gsa.ui.cart.CartViewModel
import com.gsa.ui.companyCategoryList.CompanyCategoryListActivity
import com.gsa.ui.companyList.CompanyListActivity
import com.gsa.ui.featureList.FeatureListActivity
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.landing.favorites.FavoritesViewModel
import com.gsa.ui.landing.home.adapter.AdapterFeatureProduct
import com.gsa.ui.landing.home.adapter.AdapterHomeCategories
import com.gsa.ui.landing.home.adapter.AdapterHomeCompanies
import com.gsa.ui.productList.ProductListActivity
import com.gsa.ui.retailer_List.RetailerListActivity
import com.gsa.ui.search.SearchActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment<HomeViewModel>(HomeViewModel::class),
    AdapterViewClickListener<CategoryListItem>, AdapterViewCompanyClickListener<CompanyListItem>,
    AdapterViewFeatureProductClickListener<ProductListItem> {
    override fun onClickFeatureProductAdapterView(
        objectAtPosition: ProductListItem,
        viewType: Int,
        position: Int
    ) {
        fPos=position
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_QUANTITY_CHANGED -> {

                let {
                    updateData(objectAtPosition, -1)

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
            Config.AdapterClickViewTypes.CLICK_VIEW_DELETE_FAVORITES_PRODUCT -> {

                let {
                    updateFavorites(objectAtPosition, -1)

                }
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_ADD_FAVORITES_PRODUCT -> {

                let {
                    updateFavorites(objectAtPosition, 1)

                }
            }
        }
    }

    private fun updateFavorites(objectAtPosition: ProductListItem, status: Int) {
        if (NetworkUtil.isInternetAvailable(activity)) {
            if(status==1) {

                modelFavorites.addProductToFavorites("Add Favorite",model.getUserID()!!,objectAtPosition.id)
            }else{
                modelFavorites.removeProductToFavorites("Remove Favorite List",model.getUserID()!!,objectAtPosition.id)

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
                            ProductListActivity.getIntent(
                                it, objectAtPosition.id, ""
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
    val modelFavorites: FavoritesViewModel by viewModel()

    private var adapterCompanies: AdapterHomeCompanies? = null
    internal var categoriesList: ArrayList<CategoryListItem>? = null
    internal var companyList: ArrayList<CompanyListItem>? = null
    internal var featureProductList: ArrayList<ProductListItem>? = null
    var fPos:Int=0
    var q: Int=0
     var retailerlListItem: RetailerlListItem?=null

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

    private fun updateData(objectAtPosition: ProductListItem,status: Int) {

        if (NetworkUtil.isInternetAvailable(activity)) {
            if(status==1) {
                 q = objectAtPosition.CartItemQty + 1
            } else if (status === -1) {
                q = objectAtPosition.CartItemQty.toInt()
            } else{
                q = objectAtPosition.CartItemQty - 1

            }
            if (status === 0 && objectAtPosition.CartItemQty === 0) {

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

        if(model.getRoleID().equals("5")){
            rlChangeRetailor.visibility=View.VISIBLE
        }else{
            rlChangeRetailor.visibility=View.GONE

        }

        try {
            let {
                retailerlListItem = activity?.intent?.getParcelableExtra("passselectedretailer")!!
            }
        }catch (e: Exception){

        }
        if(retailerlListItem!=null){
            tvSelectedRetailer.text=retailerlListItem?.name
        }
        rlRetailor.setOnClickListener {
            startActivity(
                RetailerListActivity.getIntent(activity),
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
            )

        }
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
        tvSearch.setOnClickListener {
            activity?.let {
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    SearchActivity.getIntent(
                        it,"",""
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

            if(model.getIsSalesMan()){
                tvSelectedRetailer.text=model.getRetailerName()

            }else{
                rlChangeRetailor.visibility=View.GONE
            }
            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.welcome)+ " " +model.getUserName())
            (activity as LandingNavigationActivity).setBack(false)
            (activity as LandingNavigationActivity).setSync(true)
            (activity as LandingNavigationActivity).setNotification(true)


        }
    }

   public fun notified(){
        if(adapterFeatureProduct!=null){
            adapterFeatureProduct?.notifyDataSetChanged()
        }
    }
    public fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCategories("Category List", model.getUserID()!!, model.getRoleID()!!)
        }
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getCompanies("Company List", model.getUserID()!!, model.getRoleID()!!)
        }
        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getFeatureProduct("Product List", model.getUserID()!!, model.getRoleID()!!)
        }

        if (NetworkUtil.isInternetAvailable(activity)) {
            modelCart.cartList("Cart List", model.getUserID()!!, model.getRoleID()!!)
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
        modelCart.cartListModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })
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
        modelFavorites.addProductFromFavoritesModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            if(it!!.status){
                featureProductList?.get(fPos)?.is_favorites="1"
                StoreProducts.getInstance().addProduct(featureProductList?.get(fPos))

                showSnackbar(it.message, true)
            }else{
                showSnackbar(it.message, false)

            }
            adapterFeatureProduct?.notifyDataSetChanged()

        })
        modelFavorites.removeProductFromFavoritesModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            if(it!!.status){
                featureProductList?.get(fPos)?.is_favorites="0"
                StoreProducts.getInstance().addProduct(featureProductList?.get(fPos))
                showSnackbar(it.message, true)
            }else{
                showSnackbar(it.message, false)

            }
            adapterFeatureProduct?.notifyDataSetChanged()

        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun showData(data: CartListResponse?) {
        activity?.let {

            var cartValue:Int?=0
            if (data?.cartList?.size == 0) {
                cartValue=0
                model.saveCartValue(cartValue)

            }else{
                cartValue=data?.cartList?.size
                model.saveCartValue(cartValue)

            }
            (it as LandingNavigationActivity).setCounter(
                true,cartValue.toString()
            )
        }

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
        StoreProducts.getInstance().saveProducts(featureProductList)

        featureProductList?.let {
            adapterFeatureProduct?.submitList(it)
            ViewCompat.setNestedScrollingEnabled(rv_featuredProduct, false)

            adapterFeatureProduct?.notifyDataSetChanged()
        }
    }
    private fun showData(data: AddToCartResponse?) {
        if(data!!.status){
            activity?.let {
                var cartValue=model.getCartValue()
                if (q == 0) {
                    cartValue=cartValue?.minus(1)
                    model.saveCartValue(cartValue)

                }else   if (q == 1) {
                    cartValue=cartValue?.plus(1)
                    model.saveCartValue(cartValue)

                }
                (it as LandingNavigationActivity).setCounter(
                    true,cartValue.toString()
                )
            }

            featureProductList?.get(fPos)?.CartItemQty=q
            StoreProducts.getInstance().addProduct(featureProductList?.get(fPos))

        }
        featureProductList?.let {
            adapterFeatureProduct?.submitList(it)
            ViewCompat.setNestedScrollingEnabled(rv_featuredProduct, false)
            adapterFeatureProduct?.notifyDataSetChanged()
        }
    }

}
