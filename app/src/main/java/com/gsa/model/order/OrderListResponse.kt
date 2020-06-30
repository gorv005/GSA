package com.gsa.model.order

import com.google.gson.annotations.SerializedName

data class OrderListResponse(@SerializedName("OrderList")
                             val orderList: ArrayList<OrderListItem>?,
                             @SerializedName("message")
                             val message: String = "",
                             @SerializedName("status")
                             val status: String = "")