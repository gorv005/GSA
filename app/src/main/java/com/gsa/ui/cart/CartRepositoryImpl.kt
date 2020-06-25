package com.gsa.ui.cart


import com.gsa.managers.PreferenceManager
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.cart.CartListResponse
import com.gsa.model.companyCategoryList.CompanyCategoryList
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class CartRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CartRepository {
    override fun orderPlace(
        service: String,
        user_id: String,
        role_id: String
    ): Single<AddToCartResponse> {
        return restApi.orderPlace(service, user_id, role_id)
    }

    override fun cartList(
        service: String,
        user_id: String,
        role_id: String
    ): Single<CartListResponse> {
        return restApi.cartList(service, user_id, role_id)
    }

    override fun addToCart(
        service: String,
        user_id: String,
        role_id: String,
        item_id: String,
        item_qty: String,
        item_amount: String
    ): Single<AddToCartResponse> {
        return restApi.addToCart(service, user_id, role_id,item_id,item_qty,item_amount)
    }







}