package com.gsa.ui.CategoryList


import com.gsa.managers.PreferenceManager
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class CategoryListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : CategoryListRepository {
    override fun getCategories(
        service: String,
        user_id: String,
        role_id: String
    ): Single<CategoriesListResponse> {
        return restApi.categoriesList(service, user_id, role_id)
    }





}