package com.gsa.ui.landing.accounts


import com.gsa.managers.PreferenceManager
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.user.UserResponsePayload
import com.gsa.network.AppRestApiFast

import io.reactivex.Single

class AccountRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : AccountRepository {
    override fun getUser(service: String, user_id: String): Single<UserResponsePayload> {
        return  restApi.getUser(service,user_id)
    }

    override fun updateProfile(
        service: String,
        user_id: String,
        role_id: String,
        name: String,
        shopName: String,
        email: String,
        pincode: String,
        address: String,
        stateId: String,
        cityId: String,
        gstNo: String,
        pancard_no: String,
        aadharCard_no: String
    ): Single<AddToCartResponse> {
        return  restApi.updateUser(service,user_id,role_id,name,shopName,email,pincode,address,stateId,cityId,
            gstNo,pancard_no,aadharCard_no)
    }





}