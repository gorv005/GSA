package com.gsa.ui.landing.favorites

import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.favorites.FavoritesListResponse
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import io.reactivex.Single

interface FavoritesRepository {
    fun getFavorites(service :String, user_id: String, role_id: String) : Single<FavoritesListResponse>
    fun addToFavorites(service :String, user_id: String, product_id: String): Single<AddToCartResponse>
    fun deleteFavorites(service :String, user_id: String, product_id: String): Single<AddToCartResponse>


}