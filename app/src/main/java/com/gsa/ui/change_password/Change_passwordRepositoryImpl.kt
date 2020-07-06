package com.gsa.ui.change_password


import com.gsa.managers.PreferenceManager
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class Change_passwordRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : ChangePasswordRepository {
    override fun change_password(
        user_id: String,
        old_password: String,
        new_password: String
    ): Single<AddToCartResponse> {
        return restApi.changePassword(user_id, old_password, new_password)
    }






}