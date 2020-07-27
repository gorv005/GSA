package com.gsa.ui.notification.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.notification.NotificationListItem


class AdapterNotificationCallback : DiffUtil.ItemCallback<NotificationListItem>() {

    override fun areItemsTheSame(oldItem: NotificationListItem, newItem: NotificationListItem) = oldItem.cDate == newItem.cDate

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: NotificationListItem, newItem: NotificationListItem): Boolean {
        return oldItem == newItem
    }
}