package com.gsa.ui.companyCategoryList

import com.gsa.model.companyCategoryList.CompanyCategoryList
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.productList.ProductListResponse
import io.reactivex.Single

interface CategoryCompanyListRepository {
    fun getCompanyCategories(service :String, user_id: String, role_id: String,company_id: String) : Single<CompanyCategoryList>

    fun getProducts(service :String, user_id: String, role_id: String,company_id: String) : Single<ProductListResponse>

}