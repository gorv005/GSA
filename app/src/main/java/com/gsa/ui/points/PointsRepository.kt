package com.gsa.ui.points

import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.points.PointsResponse
import com.gsa.model.productList.ProductListResponse
import io.reactivex.Single

interface PointsRepository {
    fun getPoints(service :String, user_id: String, role_id: String
                    ) : Single<PointsResponse>

}