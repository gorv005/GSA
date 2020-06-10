package com.gsa.ui.register


import com.gsa.managers.PreferenceManager
import com.gsa.model.city_list.CityListResponse
import com.gsa.model.login.LoginRequest
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.login.UserList
import com.gsa.model.register.RegisterResponsePayload
import com.gsa.model.stateList.StateListResponse

import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class RegisterRepositoryImpl(private val restApi: AppRestApiFast, private val pre: PreferenceManager) : RegisterRepository {
    override fun saveUserId(token: String?, user_id: String) {
        pre.saveUserID(user_id)
    }

    override fun stateList(service: String): Single<StateListResponse> {
        return restApi.stateList(service)
    }

    override fun cityList(service: String,state_id: String): Single<CityListResponse> {
        return restApi.cityList(service,state_id)
    }

    override fun register(
        service: String,
        phone: String,
        role_id: String,
        name: String,
        shop_name: String,
        email: String,
        pincode: String,
        address: String,
        state_id: String,
        city_id: String,
        gst_no: String,
        pancard_no: String,
        adharcard_no: String,
        password: String
    ): Single<RegisterResponsePayload> {
       return restApi.register(service,
            phone,
            role_id,
            name,
            shop_name,
            email,
            pincode,
            address,
            state_id,
            city_id,
            gst_no,
            pancard_no,
            adharcard_no,
            password)
    }




 






}