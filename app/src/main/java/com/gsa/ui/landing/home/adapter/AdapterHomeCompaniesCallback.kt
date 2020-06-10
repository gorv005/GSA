package com.gsa.ui.landing.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.home.CompanyListItem


class AdapterHomeCompaniesCallback : DiffUtil.ItemCallback<CompanyListItem>() {

    override fun areItemsTheSame(oldItem: CompanyListItem, newItem: CompanyListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: CompanyListItem, newItem: CompanyListItem): Boolean {
        return oldItem == newItem
    }
}