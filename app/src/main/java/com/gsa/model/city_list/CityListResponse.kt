package com.gsa.model.city_list

import com.google.gson.annotations.SerializedName

data class CityListResponse(@SerializedName("CityList")
                            val cityList: ArrayList<CityListItem>?,
                            @SerializedName("message")
                            val message: String = "",
                            @SerializedName("status")
                            val status: String = "")