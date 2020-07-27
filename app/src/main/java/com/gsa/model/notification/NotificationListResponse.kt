package com.gsa.model.notification

import com.google.gson.annotations.SerializedName

data class NotificationListResponse(@SerializedName("NotificationList")
                                    val notificationList: ArrayList<NotificationListItem>?,
                                    @SerializedName("message")
                                    val message: String = "",
                                    @SerializedName("status")
                                    val status: Boolean = false)