package com.gsa.ui.retailer_List.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.notification.NotificationListItem
import com.gsa.model.reatilter_list.RetailerlListItem


class AdapterRetailorCallback : DiffUtil.ItemCallback<RetailerlListItem>() {

    override fun areItemsTheSame(oldItem: RetailerlListItem, newItem: RetailerlListItem) = oldItem.roleId == newItem.roleId

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: RetailerlListItem, newItem: RetailerlListItem): Boolean {
        return oldItem == newItem
    }
}