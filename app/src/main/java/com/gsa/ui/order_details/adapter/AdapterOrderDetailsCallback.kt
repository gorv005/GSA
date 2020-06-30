package com.gsa.ui.order_details.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.order.ItemListItem
import com.gsa.model.order.OrderListItem


class AdapterOrderDetailsCallback : DiffUtil.ItemCallback<ItemListItem>() {

    override fun areItemsTheSame(oldItem: ItemListItem, newItem: ItemListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ItemListItem, newItem: ItemListItem): Boolean {
        return oldItem == newItem
    }
}