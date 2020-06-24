package com.gsa.model.productList

import com.google.gson.annotations.SerializedName

data class ProductListResponse(@SerializedName("ProductList")
                               val productList: ArrayList<ProductListItem>?,
                               @SerializedName("message")
                               val message: String = "",
                               @SerializedName("status")
                               val status: Boolean = false)