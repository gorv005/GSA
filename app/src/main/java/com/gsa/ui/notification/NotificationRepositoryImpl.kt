package com.gsa.ui.notification


import com.gsa.managers.PreferenceManager
import com.gsa.model.notification.NotificationListResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class NotificationRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : NotificationRepository {
    override fun notificationList(
        service: String,
        user_id: String,
        role_id: String
    ): Single<NotificationListResponse> {
      return  restApi.getNotificationList(service,user_id, role_id)
    }



}