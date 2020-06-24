package com.gsa.ui.productList

import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.productList.ProductListResponse
import io.reactivex.Single

interface ProductListRepository {
    fun getProducts(service :String, user_id: String, role_id: String,
                    company_id: String,category_id: String,search_key: String) : Single<ProductListResponse>


}