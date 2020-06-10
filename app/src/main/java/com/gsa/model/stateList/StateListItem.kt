package com.gsa.model.stateList

import com.google.gson.annotations.SerializedName

data class StateListItem(@SerializedName("state_name")
                         val stateName: String = "",
                         @SerializedName("state_id")
                         val stateId: String = "")