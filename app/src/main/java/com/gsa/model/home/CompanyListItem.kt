package com.gsa.model.home

import com.google.gson.annotations.SerializedName

data class CompanyListItem(@SerializedName("company_phone")
                           val companyPhone: String = "",
                           @SerializedName("Status")
                           val status_1: String = "",
                           @SerializedName("image")
                           val image: String = "",
                           @SerializedName("company_state_id")
                           val companyStateId: String = "",
                           @SerializedName("modified_id")
                           val modifiedId: String = "",
                           @SerializedName("modified_ip")
                           val modifiedIp: String = "",
                           @SerializedName("company_email")
                           val companyEmail: String = "",
                           @SerializedName("City")
                           val city: String = "",
                           @SerializedName("modified_date")
                           val modifiedDate: String = "",
                           @SerializedName("company_address")
                           val companyAddress: String = "",
                           @SerializedName("Date")
                           val date: String = "",
                           @SerializedName("created_id")
                           val createdId: String = "",
                           @SerializedName("discount_1")
                           val discount_1: String = "",
                           @SerializedName("discount_2")
                           val discount_2: String = "",
                           @SerializedName("discount_3")
                           val discount_3: String = "",
                           @SerializedName("State")
                           val state: String = "",
                           @SerializedName("company_name")
                           val companyName: String = "",
                           @SerializedName("company_city_id")
                           val companyCityId: String = "",
                           @SerializedName("company_code")
                           val companyCode: String = "",
                           @SerializedName("id")
                           val id: String = "",
                           @SerializedName("created_date")
                           val createdDate: String = "",
                           @SerializedName("created_ip")
                           val createdIp: String = "",
                           @SerializedName("status")
                           val status: String = "")