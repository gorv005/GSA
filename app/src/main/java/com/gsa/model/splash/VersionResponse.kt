package com.gsa.model.splash

import com.google.gson.annotations.SerializedName

data class VersionResponse(@SerializedName("message")
                           val message: String = "",
                           @SerializedName("VersionList")
                           val versionList: VersionList,
                           @SerializedName("status")
                           val status: Boolean = false)