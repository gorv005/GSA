package com.gsa.model.feature_product

import com.google.gson.annotations.SerializedName
import com.gsa.model.productList.ProductListItem

data class FeatureProductResponse(@SerializedName("FeatureProductList")
                                  val featureProductList: ArrayList<ProductListItem>?,
                                  @SerializedName("message")
                                  val message: String = "",
                                  @SerializedName("status")
                                  val status: String = "")