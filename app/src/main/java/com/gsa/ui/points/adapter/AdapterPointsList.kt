package com.gsa.ui.points.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils
import com.gsa.R
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.callbacks.AdapterViewCompanyClickListener
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.managers.ImageRequestManager
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.points.PointListItem
import com.gsa.model.productList.ProductListItem
import com.gsa.util.UiUtils
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_feature_product.view.*
import kotlinx.android.synthetic.main.item_points.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterPointsList(
    private val adapterViewClickListener: AdapterViewClickListener<PointListItem>?,
    val activity: Activity
) : ListAdapter<PointListItem, AdapterPointsList.ViewHolder>(
    AdapterPointsListCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterPointsList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_points, parent, false)
        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: PointListItem, adapterViewClick: AdapterViewClickListener<PointListItem>?) {

            itemView.text_order_no?.text = allProducts.orderNumber
            itemView.text_date_name?.text = allProducts.date
            itemView.text_points_name.setText(""+allProducts.totalPoint)





            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_FEATURE_PRODUCT, adapterPosition
                )
            }
        }
    }

}