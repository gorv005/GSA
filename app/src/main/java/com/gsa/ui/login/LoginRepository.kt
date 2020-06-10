package com.gsa.ui.login


import com.gsa.model.login.LoginRequest
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.login.UserList
import io.reactivex.Single

interface LoginRepository {
    fun loginResponse(data: LoginRequest) : Single<LoginResponsePayload>
    fun saveUserData(token: String?,data: UserList,isRemember :Boolean)

}