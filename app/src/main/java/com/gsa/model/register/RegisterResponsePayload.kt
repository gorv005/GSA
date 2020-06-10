package com.gsa.model.register

import com.google.gson.annotations.SerializedName

data class RegisterResponsePayload(@SerializedName("user_id")
                                   val userId: Int = 0,
                                   @SerializedName("message")
                                   val message: String = "",
                                   @SerializedName("status")
                                   val status: Boolean = false)