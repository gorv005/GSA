package com.gsa.ui.landing.home.adapter

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
import com.gsa.model.home.CompanyListItem
import com.gsa.model.home.categories.CategoryListItem
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterHomeCategories(
    private val adapterViewClickListener: AdapterViewClickListener<CategoryListItem>?,
    val activity: Activity
) : ListAdapter<CategoryListItem, AdapterHomeCategories.ViewHolder>(
    AdapterHomeCategoriesCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterHomeCategories.ViewHolder {
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


        fun bind(allProducts: CategoryListItem, adapterViewClick: AdapterViewClickListener<CategoryListItem>?) {

            itemView.tv_item_name?.text = allProducts.catName
            ImageRequestManager.with(itemView.iv_item_image)
                .url(allProducts.image)
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