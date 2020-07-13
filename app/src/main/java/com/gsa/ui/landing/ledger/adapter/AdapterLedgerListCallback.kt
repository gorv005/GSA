package com.gsa.ui.landing.ledger.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.ledger.LedgerResponse
import com.gsa.model.ledger.ReportListItem


class AdapterLedgerListCallback : DiffUtil.ItemCallback<ReportListItem>() {

    override fun areItemsTheSame(oldItem: ReportListItem, newItem: ReportListItem) = oldItem.typeID == newItem.typeID

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: ReportListItem, newItem: ReportListItem): Boolean {
        return oldItem == newItem
    }
}