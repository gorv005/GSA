package com.gsa.ui.splash


import com.gsa.managers.PreferenceManager
import com.gsa.model.productList.ProductListResponse
import com.gsa.model.splash.VersionResponse
import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class SplashRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : SplashRepository {


    override fun getVersion(service: String): Single<VersionResponse> {
        return restApi.getVersion(service)
    }


}