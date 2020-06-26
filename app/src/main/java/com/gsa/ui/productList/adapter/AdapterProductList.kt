package com.gsa.ui.productList.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.gsa.model.productList.ProductListItem
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_feature_product.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterProductList(
    private val adapterViewClickListener: AdapterViewFeatureProductClickListener<ProductListItem>?,
    val activity: Activity
) : ListAdapter<ProductListItem, AdapterProductList.ViewHolder>(
    AdapterProductListCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterProductList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_feature_product, parent, false)

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


        fun bind(allProducts: ProductListItem, adapterViewClick: AdapterViewFeatureProductClickListener<ProductListItem>?) {

            itemView.text_part_no?.text = allProducts.pMrp
            itemView.text_mrp?.text = allProducts.userPrice
            itemView.tvQuantity.setText(""+allProducts.CartItemQty)
            itemView.text_company_name.text=allProducts.companyName

            itemView.setOnClickListener {
                adapterViewClick?.onClickFeatureProductAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_FEATURE_PRODUCT, adapterPosition
                )
            }
        }
    }

}