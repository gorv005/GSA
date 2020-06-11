package com.gsa.model.home.categories

import com.google.gson.annotations.SerializedName

data class CategoriesListResponse(@SerializedName("CategoryList")
                                  val categoryList: ArrayList<CategoryListItem>?,
                                  @SerializedName("message")
                                  val message: String = "",
                                  @SerializedName("status")
                                  val status: String = "")