package com.gsa.ui.points


import com.gsa.managers.PreferenceManager
import com.gsa.model.points.PointsResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.network.AppRestApiFast
import io.reactivex.Single

class PointsRepositoryImpl(
    private val restApi: AppRestApiFast,
    private val pre: PreferenceManager
) : PointsRepository {
    override fun getPoints(
        service: String,
        user_id: String,
        role_id: String
    ): Single<PointsResponse> {
        return restApi.getPoints(service,user_id,role_id)
    }

}