package com.gsa.ui.companyList


import com.gsa.managers.PreferenceManager
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class CompanyListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CompanyListRepository {


    override fun getCompanies(
        service: String,
        user_id: String,
        role_id: String
    ): Single<CompaniesListResponse> {
        return restApi.companyList(service, user_id, role_id)
    }



}