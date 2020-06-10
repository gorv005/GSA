package com.gsa.model.login

import com.google.gson.annotations.SerializedName

data class UserList(@SerializedName("pincode")
                    val pincode: String = "",
                    @SerializedName("address")
                    val address: String = "",
                    @SerializedName("pancard_no")
                    val pancardNo: String = "",
                    @SerializedName("StateName")
                    val stateName: String = "",
                    @SerializedName("adharcard_no")
                    val adharcardNo: String = "",
                    @SerializedName("CityName")
                    val cityName: String = "",
                    @SerializedName("shop_name")
                    val shopName: String = "",
                    @SerializedName("UserStatus")
                    val userStatus: String = "",
                    @SerializedName("RoleName")
                    val roleName: String = "",
                    @SerializedName("user_code")
                    val userCode: String = "",
                    @SerializedName("user_id")
                    val userId: String = "",
                    @SerializedName("role_id")
                    val roleId: String = "",
                    @SerializedName("gst_no")
                    val gstNo: String = "",
                    @SerializedName("name")
                    val name: String = "",
                    @SerializedName("state_id")
                    val stateId: String = "",
                    @SerializedName("created_date")
                    val createdDate: String = "",
                    @SerializedName("is_user_login")
                    val isUserLogin: Boolean = false,
                    @SerializedName("email")
                    val email: String = "",
                    @SerializedName("city_id")
                    val cityId: String = "",
                    @SerializedName("status")
                    val status: String = "")