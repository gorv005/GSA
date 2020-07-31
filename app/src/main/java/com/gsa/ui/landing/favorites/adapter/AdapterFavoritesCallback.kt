package com.gsa.ui.landing.favorites.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.favorites.FavoriteListItem
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.order.OrderListItem
import com.gsa.model.productList.ProductListItem


class AdapterFavoritesCallback : DiffUtil.ItemCallback<ProductListItem>() {

    override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
        return oldItem == newItem
    }
}