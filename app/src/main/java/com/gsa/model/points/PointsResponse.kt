package com.gsa.model.points

import com.google.gson.annotations.SerializedName

data class PointsResponse(@SerializedName("PointList")
                          val pointList: ArrayList<PointListItem>?,
                          @SerializedName("message")
                          val message: String = "",
                          @SerializedName("TotalPoints")
                          val totalPoints: String = "",
                          @SerializedName("status")
                          val status: Boolean = false,
                          @SerializedName("RedeemPoints")
                          val redeemPoints: String = "",
                          @SerializedName("UsePoints")
                          val UsePoints: String = "",
                          @SerializedName("BalancePoints")
                          val BalancePoints: String = "")