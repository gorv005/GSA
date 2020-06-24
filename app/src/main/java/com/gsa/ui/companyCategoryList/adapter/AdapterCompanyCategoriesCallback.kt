package com.gsa.ui.companyCategoryList.adapter

import androidx.recyclerview.widget.DiffUtil
import com.gsa.model.companyCategoryList.CompanyCategoryListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.home.categories.CategoryListItem


class AdapterCompanyCategoriesCallback : DiffUtil.ItemCallback<CompanyCategoryListItem>() {

    override fun areItemsTheSame(oldItem: CompanyCategoryListItem, newItem: CompanyCategoryListItem) = oldItem.categoryId == newItem.categoryId

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: CompanyCategoryListItem, newItem: CompanyCategoryListItem): Boolean {
        return oldItem == newItem
    }
}