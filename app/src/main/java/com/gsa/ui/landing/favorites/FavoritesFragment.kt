package com.gsa.ui.landing.favorites


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.base.StoreProducts
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.favorites.FavoriteListItem
import com.gsa.model.favorites.FavoritesListResponse
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.productList.ProductListItem
import com.gsa.ui.cart.CartViewModel
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.landing.favorites.adapter.AdapterFavoritesList
import com.gsa.ui.landing.home.HomeFragment
import com.gsa.ui.landing.home.HomeViewModel
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class FavoritesFragment: BaseFragment<FavoritesViewModel>(FavoritesViewModel::class),
    AdapterViewFeatureProductClickListener<ProductListItem> {
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

    override fun getLayoutId() = R.layout.fragment_favorites
    val modelCart: CartViewModel by viewModel()

    var fPos:Int=0
    var q: Int=0
    private var adapterFavoritesList: AdapterFavoritesList? = null
    internal var favListList: ArrayList<ProductListItem>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var manager2 = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rv_favorites.layoutManager = manager2
        activity?.let {
            adapterFavoritesList = AdapterFavoritesList(this, it)
            rv_favorites.adapter = adapterFavoritesList

        }
        subscribeLoading()
        subscribeUi()
        getData()
    }

    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentFavorites()) {

            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.my_favorites))
            (activity as LandingNavigationActivity).setBack(false)
            (activity as LandingNavigationActivity).setSync(true)
            (activity as LandingNavigationActivity).setNotification(true)

        }
    }

    public fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getFavorites("Favorite List", model.getUserID()!!, model.getRoleID()!!)
        }

    }
    private fun updateFavorites(objectAtPosition: ProductListItem,status: Int) {
        if (NetworkUtil.isInternetAvailable(activity)) {
            if(status==1) {

                model.addProductToFavorites("Add Favorite",model.getUserID()!!,objectAtPosition.id)
            }else{
                model.removeProductToFavorites("Remove Favorite List",model.getUserID()!!,objectAtPosition.id)

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
        model.favoritesListModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
        model.addProductFromFavoritesModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            if(it!!.status){
                showSnackbar(it.message, true)

               /* favListList?.removeAt(fPos)
                showSnackbar(it.message, true)
                adapterFavoritesList?.notifyDataSetChanged()*/
            }else{
                showSnackbar(it.message, false)

            }

        })
        model.removeProductFromFavoritesModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            if(it!!.status){
                favListList?.get(fPos)?.is_favorites = "0"

                StoreProducts.getInstance().addProduct(favListList?.get(fPos))

                favListList?.removeAt(fPos)
                showSnackbar(it.message, true)
            }else{
                showSnackbar(it.message, false)

            }
            favListList?.let {
                if(it.size==0){
                    rlNoData.visibility=View.VISIBLE

                }else{
                    rlNoData.visibility=View.GONE

                }
            }
            adapterFavoritesList?.notifyDataSetChanged()

        })

        modelCart.addToCartModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })

    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun showData(data: AddToCartResponse?) {
        if(data!!.status) {


            activity?.let {
                var cartValue=model.getCartValue()
                if (q == 0) {
                    cartValue=cartValue?.minus(1)
                    model.saveCartValue(cartValue)

                }else{
                    cartValue=cartValue?.plus(1)
                    model.saveCartValue(cartValue)

                }
                (it as LandingNavigationActivity).setCounter(
                    true,cartValue.toString()
                )
            }
            favListList?.get(fPos)?.CartItemQty = q
            StoreProducts.getInstance().addProduct(favListList?.get(fPos))

            favListList?.let {
                adapterFavoritesList?.submitList(it)
                adapterFavoritesList?.notifyDataSetChanged()
            }
        }
    }

    private fun showData(data: FavoritesListResponse?) {

        favListList = data?.favoriteList
        StoreProducts.getInstance().saveProducts(favListList)
        favListList?.let {

            adapterFavoritesList?.submitList(it)

            adapterFavoritesList?.notifyDataSetChanged()
        }
        favListList?.let {
            if(it.size==0){
                rlNoData.visibility=View.VISIBLE

            }else{
                rlNoData.visibility=View.GONE

            }

        }
    }
    companion object {

        fun getInstance(instance: Int): FavoritesFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = FavoritesFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}
