package com.gsa.ui.notification


import com.gsa.model.notification.NotificationListResponse
import com.gsa.model.productList.ProductListResponse
import io.reactivex.Single

interface NotificationRepository {
    fun notificationList(service :String, user_id: String, role_id: String) : Single<NotificationListResponse>

}