package com.gsa.model.ledger

import com.google.gson.annotations.SerializedName

data class ReportListItem(@SerializedName("Date")
                          val Date: String = "",
                          @SerializedName("user_id")
                          val userId: String = "",
                          @SerializedName("Ref")
                          val Ref: String = "",
                          @SerializedName("Description")
                          val description: String = "",
                          @SerializedName("nType")
                          val nType: String = "",
                          @SerializedName("Amount")
                          val Amount: Double =0.0)