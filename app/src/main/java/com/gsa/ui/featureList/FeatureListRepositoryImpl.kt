package com.gsa.ui.featureList


import com.gsa.managers.PreferenceManager
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class FeatureListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : FeatureListRepository {
    override fun getFeatureProduct(
        service: String,
        user_id: String,
        role_id: String
    ): Single<FeatureProductResponse> {
        return restApi.featureProductList(service, user_id, role_id)
    }


}