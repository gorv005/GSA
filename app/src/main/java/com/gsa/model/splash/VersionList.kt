package com.gsa.model.splash

import com.google.gson.annotations.SerializedName

data class VersionList(@SerializedName("version_name")
                       val versionName: String = "",
                       @SerializedName("version_code")
                       val versionCode: String = "",
                       @SerializedName("playstore_url")
                       val playstoreUrl: String = "")