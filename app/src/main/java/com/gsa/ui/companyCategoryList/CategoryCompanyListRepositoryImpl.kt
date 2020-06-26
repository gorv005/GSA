package com.gsa.ui.companyCategoryList


import com.gsa.managers.PreferenceManager
import com.gsa.model.companyCategoryList.CompanyCategoryList
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class CategoryCompanyListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CategoryCompanyListRepository {
    override fun getProducts(
        service: String,
        user_id: String,
        role_id: String,
        company_id: String
    ): Single<ProductListResponse> {
        return restApi.productList1(service, user_id, role_id,company_id)
    }

    override fun getCompanyCategories(
        service: String,
        user_id: String,
        role_id: String,
        company_id: String
    ): Single<CompanyCategoryList> {
        return restApi.companyCategoriesList(service, user_id, role_id,company_id)
    }






}