package com.gsa.model.notification

import com.google.gson.annotations.SerializedName

data class NotificationListItem(@SerializedName("c_date")
                                val cDate: String = "",
                                @SerializedName("notification_text")
                                val notificationText: String = "")