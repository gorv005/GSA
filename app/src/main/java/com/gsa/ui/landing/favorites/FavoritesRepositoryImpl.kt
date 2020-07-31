package com.gsa.ui.landing.favorites


import com.gsa.managers.PreferenceManager
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.favorites.FavoritesListResponse
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class FavoritesRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : FavoritesRepository {
    override fun getFavorites(
        service: String,
        user_id: String,
        role_id: String
    ): Single<FavoritesListResponse> {
       return restApi.getFavoritesList(service,user_id)
    }

    override fun addToFavorites(
        service: String,
        user_id: String,
        product_id: String
    ): Single<AddToCartResponse> {
        return restApi.addFavorites(service,user_id,product_id)
    }

    override fun deleteFavorites(
        service: String,
        user_id: String,
        product_id: String
    ): Single<AddToCartResponse> {
        return restApi.removeFavorites(service,user_id,product_id)
    }




}