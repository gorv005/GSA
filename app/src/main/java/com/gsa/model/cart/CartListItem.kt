package com.gsa.model.cart

import com.google.gson.annotations.SerializedName

data class CartListItem(@SerializedName("ProductName")
                        val productName: String = "",
                        @SerializedName("user_id")
                        val userId: String = "",
                        @SerializedName("item_id")
                        val itemId: String = "",
                        @SerializedName("item_amount")
                        val itemAmount: String = "",
                        @SerializedName("id")
                        val id: String = "",
                        @SerializedName("created_date")
                        val createdDate: String = "",
                        @SerializedName("item_qty")
                        var itemQty: Int)