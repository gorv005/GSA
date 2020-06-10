package com.gsa.model.home.categories

import com.google.gson.annotations.SerializedName

data class CategoryListItem(@SerializedName("image")
                            val image: String = "",
                            @SerializedName("c_date")
                            val cDate: String = "",
                            @SerializedName("cat_name")
                            val catName: String = "",
                            @SerializedName("id")
                            val id: String = "")