package com.gsa.model.login

data class LoginRequest(val user_name: String = "",
                       val password: String = "",
                       val device_type: String = "",
                       val gcm_id: String = "")