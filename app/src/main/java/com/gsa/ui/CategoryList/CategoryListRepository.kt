package com.gsa.ui.CategoryList

import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import io.reactivex.Single

interface CategoryListRepository {
    fun getCategories(service :String, user_id: String, role_id: String) : Single<CategoriesListResponse>


}