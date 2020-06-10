package com.gsa.ui.landing.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem


class AdapterFeatureProductCallback : DiffUtil.ItemCallback<FeatureProductListItem>() {

    override fun areItemsTheSame(oldItem: FeatureProductListItem, newItem: FeatureProductListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: FeatureProductListItem, newItem: FeatureProductListItem): Boolean {
        return oldItem == newItem
    }
}