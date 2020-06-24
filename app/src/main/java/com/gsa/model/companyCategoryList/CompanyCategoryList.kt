package com.gsa.model.companyCategoryList

import com.google.gson.annotations.SerializedName

data class CompanyCategoryList(@SerializedName("CategoryList")
                               val categoryList: ArrayList<CompanyCategoryListItem>?,
                               @SerializedName("message")
                               val message: String = "",
                               @SerializedName("status")
                               val status: Boolean = false)