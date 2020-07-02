package com.gsa.ui.search


import com.gsa.model.productList.ProductListResponse
import io.reactivex.Single

interface SearchRepository {
    fun searchProduct(service :String, user_id: String, role_id: String,
                      company_id: String,category_id: String,search_key: String) : Single<ProductListResponse>

}