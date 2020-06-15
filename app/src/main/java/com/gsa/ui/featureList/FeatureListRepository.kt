package com.gsa.ui.featureList

import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import io.reactivex.Single

interface FeatureListRepository {
    fun getFeatureProduct(service :String, user_id: String, role_id: String): Single<FeatureProductResponse>


}