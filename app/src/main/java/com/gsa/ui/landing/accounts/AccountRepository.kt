package com.gsa.ui.landing.accounts

import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.user.UserResponsePayload
import io.reactivex.Single
import java.net.Inet4Address

interface AccountRepository {
    fun getUser(service :String, user_id: String) : Single<UserResponsePayload>
    fun updateProfile(service :String, user_id: String, role_id: String,name :String,
                      shopName: String, email: String, pincode : String, address: String,
                      stateId: String, cityId: String, gstNo: String, pancard_no : String,
                      aadharCard_no: String): Single<AddToCartResponse>


}