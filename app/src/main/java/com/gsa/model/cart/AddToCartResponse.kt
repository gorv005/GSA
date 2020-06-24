package com.gsa.model.cart

import com.google.gson.annotations.SerializedName

data class AddToCartResponse(@SerializedName("message")
                             val message: String = "",
                             @SerializedName("status")
                             val status: Boolean = false)