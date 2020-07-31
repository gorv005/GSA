package com.gsa.model.favorites

import com.google.gson.annotations.SerializedName
import com.gsa.model.productList.ProductListItem

data class FavoritesListResponse(@SerializedName("FavoriteList")
                                 val favoriteList: ArrayList<ProductListItem>?,
                                 @SerializedName("message")
                                 val message: String = "",
                                 @SerializedName("status")
                                 val status: Boolean = false)