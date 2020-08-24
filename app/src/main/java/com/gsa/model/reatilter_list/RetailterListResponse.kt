package com.gsa.model.reatilter_list


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RetailterListResponse(@SerializedName("message")
                                 val message: String ?,
                                 @SerializedName("RetailerlList")
                                 val retailerlList: ArrayList<RetailerlListItem>?,
                                 @SerializedName("status")
                                 val status: Boolean = false)

@Parcelize
data class RetailerlListItem(@SerializedName("RoleName")
                             val roleName: String ?,
                             @SerializedName("user_code")
                             val userCode: String?,
                             @SerializedName("user_id")
                             val userId: String ?,
                             @SerializedName("role_id")
                             val roleId: String ?,
                             @SerializedName("StateName")
                             val stateName: String ?,
                             @SerializedName("name")
                             val name: String ="",
                             @SerializedName("CityName")
                             val cityName: String ?,
                             @SerializedName("shop_name")
                             val shopName: String ?): Parcelable


