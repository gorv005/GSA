package com.gsa.model.city_list

import com.google.gson.annotations.SerializedName

data class CityListItem(@SerializedName("city_name")
                        val cityName: String = "",
                        @SerializedName("state_id")
                        val stateId: String = "",
                        @SerializedName("city_id")
                        val cityId: String = "")