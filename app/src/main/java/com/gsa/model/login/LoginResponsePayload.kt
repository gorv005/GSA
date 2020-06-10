package com.gsa.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponsePayload(@SerializedName("user_list")
                                val userList: UserList,
                                @SerializedName("message")
                                val message: String = "",
                                @SerializedName("status")
                                val status: Boolean = false)