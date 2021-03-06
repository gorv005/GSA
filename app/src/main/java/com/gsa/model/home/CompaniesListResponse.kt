package com.gsa.model.home

import com.google.gson.annotations.SerializedName

data class CompaniesListResponse(@SerializedName("CompanyList")
                                 val companyList: ArrayList<CompanyListItem>?,
                                 @SerializedName("message")
                                 val message: String = "",
                                 @SerializedName("status")
                                 val status: Boolean = false)