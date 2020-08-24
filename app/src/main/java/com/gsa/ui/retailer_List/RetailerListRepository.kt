package com.gsa.ui.retailer_List


import com.gsa.model.notification.NotificationListResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.model.reatilter_list.RetailterListResponse
import io.reactivex.Single

interface RetailerListRepository {
    fun retailterList(service :String) : Single<RetailterListResponse>

}