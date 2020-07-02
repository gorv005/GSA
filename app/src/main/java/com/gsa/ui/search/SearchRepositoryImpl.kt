package com.gsa.ui.search


import com.gsa.managers.PreferenceManager
import com.gsa.model.productList.ProductListResponse
import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class SearchRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : SearchRepository {
    override fun searchProduct(
        service: String,
        user_id: String,
        role_id: String,
        company_id: String,
        category_id: String,
        search_key: String
    ): Single<ProductListResponse> {
        return restApi.searchProduct(service, user_id, role_id, company_id, category_id,search_key)
    }


}