package com.gsa.ui.cart

import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.cart.CartListItem
import com.gsa.model.cart.CartListResponse
import com.gsa.model.companyCategoryList.CompanyCategoryList
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import io.reactivex.Single

interface CartRepository {

    fun addToCart(service :String, user_id: String, role_id: String,
                  item_id: String,item_qty: String,item_amount: String) : Single<AddToCartResponse>

    fun cartList(service :String, user_id: String, role_id: String) : Single<CartListResponse>
    fun orderPlace(service :String, user_id: String, role_id: String) : Single<AddToCartResponse>

/*
    fun cartList(service :String, user_id: String, role_id: String) : Single<CartListResponse>
*/

}