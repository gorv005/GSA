package com.gsa.ui.register


import com.gsa.model.city_list.CityListResponse
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.login.UserList
import com.gsa.model.register.RegisterResponsePayload
import com.gsa.model.stateList.StateListResponse
import io.reactivex.Single

interface RegisterRepository {
    fun register(
        service: String, phone: String,
        role_id: String,
        name: String,
        shop_name: String,
        email: String, pincode: String,
        address: String, state_id: String,
        city_id: String, gst_no: String,
        pancard_no: String, adharcard_no: String,
        password: String
    ): Single<RegisterResponsePayload>

    fun stateList(
        service: String
    ): Single<StateListResponse>

    fun cityList(
        service: String,state_id:String
    ): Single<CityListResponse>

    fun saveUserId(token: String?,userId: String)

}