package com.gsa.ui.companyCategoryList.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils
import com.gsa.R
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.managers.ImageRequestManager
import com.gsa.model.companyCategoryList.CompanyCategoryListItem
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterCompanyCategories(
    private val adapterViewClickListener: AdapterViewClickListener<CompanyCategoryListItem>?,
    val activity: Activity
) : ListAdapter<CompanyCategoryListItem, AdapterCompanyCategories.ViewHolder>(
    AdapterCompanyCategoriesCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): AdapterCompanyCategories.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_product, parent, false)

        /*  val displayMetrics = DisplayMetrics()
          activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
          val width = displayMetrics.widthPixels

          itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
  */

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(
            allProducts: CompanyCategoryListItem,
            adapterViewClick: AdapterViewClickListener<CompanyCategoryListItem>?
        ) {

            itemView.tv_item_name?.text = allProducts.categoryName
            ImageRequestManager.with(itemView.iv_item_image)
                .url(allProducts.categoryImage)
                .setScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build()
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_CATEGORY, adapterPosition
                )
            }
        }
    }

}