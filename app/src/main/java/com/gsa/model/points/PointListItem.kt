package com.gsa.model.points

import com.google.gson.annotations.SerializedName

data class PointListItem(@SerializedName("redeem_point")
                         val redeem_point: String = "",
                         @SerializedName("p_status")
                         val p_status: String = "",
                         @SerializedName("id")
                         val id: String = "",
                         @SerializedName("status")
                         val status: String = "",
                         @SerializedName("Date")
                         val date: String = "")