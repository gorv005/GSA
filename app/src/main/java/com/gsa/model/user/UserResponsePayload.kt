package com.gsa.model.user

import com.google.gson.annotations.SerializedName

data class UserResponsePayload(@SerializedName("user_list")
                               val userList: UserList,
                               @SerializedName("message")
                               val message: String = "",
                               @SerializedName("status")
                               val status: Boolean = false)