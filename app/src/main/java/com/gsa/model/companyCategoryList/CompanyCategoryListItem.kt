package com.gsa.model.companyCategoryList

import com.google.gson.annotations.SerializedName

data class CompanyCategoryListItem(@SerializedName("category_id")
                            val categoryId: String = "",
                                   @SerializedName("CategoryImage")
                            val categoryImage: String = "",
                                   @SerializedName("CategoryName")
                            val categoryName: String = "")