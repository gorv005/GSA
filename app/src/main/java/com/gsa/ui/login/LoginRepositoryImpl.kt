package com.gsa.ui.login


import com.gsa.managers.PreferenceManager
import com.gsa.model.login.LoginRequest
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.login.UserList

import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class LoginRepositoryImpl(private val restApi: AppRestApiFast,private val pre: PreferenceManager) : LoginRepository {
    override fun saveUserData(token: String?,data: UserList,isRemember :Boolean) {
        pre.loginUser(token,data,isRemember)
    }


    override fun loginResponse(data: LoginRequest): Single<LoginResponsePayload> {
        return restApi.login(data.user_name,data.password,data.device_type,data.gcm_id)
    }






}