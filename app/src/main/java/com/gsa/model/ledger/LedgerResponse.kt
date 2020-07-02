package com.gsa.model.ledger

import com.google.gson.annotations.SerializedName

data class LedgerResponse(@SerializedName("message")
                          val message: String = "",
                          @SerializedName("url")
                          val url: String = "",
                          @SerializedName("status")
                          val status: Boolean = false)