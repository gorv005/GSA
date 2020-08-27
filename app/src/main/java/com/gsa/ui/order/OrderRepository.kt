package com.gsa.ui.order

import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.order.OrderListResponse
import io.reactivex.Single

interface OrderRepository {
    fun getOrders(service :String, user_id: String, role_id: String) : Single<OrderListResponse>
    fun getOrders(service :String, user_id: String, role_id: String,retailer_id: String) : Single<OrderListResponse>


}