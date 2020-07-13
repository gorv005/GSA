package com.gsa.model.ledger

import com.google.gson.annotations.SerializedName

data class LedgerResponse(@SerializedName("message")
                          val message: String = "",
                          @SerializedName("ReportList")
                          val reportList: List<ReportListItem>?,
                          @SerializedName("status")
                          val status: Boolean = false)