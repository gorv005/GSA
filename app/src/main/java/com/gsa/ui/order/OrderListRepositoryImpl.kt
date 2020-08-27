package com.gsa.ui.order


import com.gsa.managers.PreferenceManager
import com.gsa.model.order.OrderListResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class OrderListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : OrderRepository {
    override fun getOrders(
        service: String,
        user_id: String,
        role_id: String
    ): Single<OrderListResponse> {
        return restApi.orderList(service, user_id, role_id)
    }

    override fun getOrders(
        service: String,
        user_id: String,
        role_id: String,
        retailer_id: String
    ): Single<OrderListResponse> {
        return restApi.orderList(service, user_id, role_id,retailer_id)
    }

}