package com.gsa.model.feature_product

import com.google.gson.annotations.SerializedName

data class FeatureProductResponse(@SerializedName("FeatureProductList")
                                  val featureProductList: List<FeatureProductListItem>?,
                                  @SerializedName("message")
                                  val message: String = "",
                                  @SerializedName("status")
                                  val status: String = "")