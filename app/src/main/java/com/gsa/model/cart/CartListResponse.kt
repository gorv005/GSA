package com.gsa.model.cart

import com.google.gson.annotations.SerializedName

data class CartListResponse(@SerializedName("CartList")
                            val cartList: ArrayList<CartListItem>?,
                            @SerializedName("message")
                            val message: String = "",
                            @SerializedName("status")
                            val status: Boolean = false)