package com.gsa.ui.landing.home


import com.gsa.managers.PreferenceManager
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class HomeRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : HomeRepository {
    override fun getFeatureProduct(
        service: String,
        user_id: String,
        role_id: String
    ): Single<FeatureProductResponse> {
        return restApi.featureProductList(service, user_id, role_id)
    }

    override fun getCompanies(
        service: String,
        user_id: String,
        role_id: String
    ): Single<CompaniesListResponse> {
        return restApi.companyList(service, user_id, role_id)
    }

    override fun getCategories(
        service: String,
        user_id: String,
        role_id: String
    ): Single<CategoriesListResponse> {
        return restApi.categoriesList(service, user_id, role_id)
    }


}