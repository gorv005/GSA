package com.gsa.ui.landing.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.home.CompanyListItem
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.home.categories.CategoryListItem


class AdapterHomeCategoriesCallback : DiffUtil.ItemCallback<CategoryListItem>() {

    override fun areItemsTheSame(oldItem: CategoryListItem, newItem: CategoryListItem) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: CategoryListItem, newItem: CategoryListItem): Boolean {
        return oldItem == newItem
    }
}