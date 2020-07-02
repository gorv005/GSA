package com.gsa.ui.points.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.points.PointListItem
import com.gsa.model.points.PointsResponse
import com.gsa.model.productList.ProductListItem


class AdapterPointsListCallback : DiffUtil.ItemCallback<PointListItem>() {

    override fun areItemsTheSame(oldItem: PointListItem, newItem: PointListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: PointListItem, newItem: PointListItem): Boolean {
        return oldItem == newItem
    }
}