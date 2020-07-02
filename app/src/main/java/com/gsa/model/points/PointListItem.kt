package com.gsa.model.points

import com.google.gson.annotations.SerializedName

data class PointListItem(@SerializedName("use_point")
                         val usePoint: String = "",
                         @SerializedName("OrderNumber")
                         val orderNumber: String = "",
                         @SerializedName("id")
                         val id: String = "",
                         @SerializedName("total_point")
                         val totalPoint: String = "",
                         @SerializedName("Date")
                         val date: String = "")