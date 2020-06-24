package com.gsa.ui.cart.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.cart.CartListItem
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem


class AdapterCartCallback : DiffUtil.ItemCallback<CartListItem>() {

    override fun areItemsTheSame(oldItem: CartListItem, newItem: CartListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: CartListItem, newItem: CartListItem): Boolean {
        return oldItem == newItem
    }
}