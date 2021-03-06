package com.gsa.model.stateList

import com.google.gson.annotations.SerializedName

data class StateListResponse(@SerializedName("StateList")
                             val stateList: ArrayList<StateListItem>?,
                             @SerializedName("message")
                             val message: String = "",
                             @SerializedName("status")
                             val status: String = "")