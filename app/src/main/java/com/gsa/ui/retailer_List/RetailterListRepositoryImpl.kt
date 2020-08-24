package com.gsa.ui.retailer_List


import com.gsa.managers.PreferenceManager
import com.gsa.model.reatilter_list.RetailterListResponse
import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class RetailterListRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : RetailerListRepository {


    override fun retailterList(service: String): Single<RetailterListResponse> {
        return restApi.getRetailterList(service)
    }


}