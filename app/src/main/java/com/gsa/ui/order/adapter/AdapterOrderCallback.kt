package com.gsa.ui.order.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.order.OrderListItem


class AdapterOrderCallback : DiffUtil.ItemCallback<OrderListItem>() {

    override fun areItemsTheSame(oldItem: OrderListItem, newItem: OrderListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: OrderListItem, newItem: OrderListItem): Boolean {
        return oldItem == newItem
    }
}