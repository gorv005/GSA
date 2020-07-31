package com.gsa.ui.splash

import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.model.splash.VersionResponse
import io.reactivex.Single

interface SplashRepository {
    fun getVersion(service :String) : Single<VersionResponse>


}