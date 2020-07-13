package com.gsa.model.ledger

import com.google.gson.annotations.SerializedName

data class ReportListItem(@SerializedName("date")
                          val date: String = "",
                          @SerializedName("user_id")
                          val userId: String = "",
                          @SerializedName("user_name")
                          val userName: String = "",
                          @SerializedName("closing_balance")
                          val closingBalance: String = "",
                          @SerializedName("description")
                          val description: String = "",
                          @SerializedName("typeID")
                          val typeID: String = "",
                          @SerializedName("opening_balance")
                          val openingBalance: String = "",
                          @SerializedName("created_date")
                          val createdDate: String = "",
                          @SerializedName("type")
                          val type: String = "",
                          @SerializedName("debit")
                          val debit: String = "",
                          @SerializedName("credit")
                          val credit: String = "",
                          @SerializedName("remarks")
                          val remarks: String = "")