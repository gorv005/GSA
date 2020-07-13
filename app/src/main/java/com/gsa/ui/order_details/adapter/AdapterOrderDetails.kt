package com.gsa.ui.order_details.adapter

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
import com.gsa.model.order.ItemListItem
import com.gsa.model.order.OrderListItem
import com.gsa.ui.order.adapter.AdapterOrderCallback
import com.gsa.util.UiUtils
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_order_details.view.*

class AdapterOrderDetails(
    private val adapterViewClickListener: AdapterViewClickListener<ItemListItem>?,
    val activity: Activity
) : ListAdapter<ItemListItem, AdapterOrderDetails.ViewHolder>(
    AdapterOrderDetailsCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterOrderDetails.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_order_details, parent, false)

      /*  val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        itemView.layoutParams = RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT)
*/

        return ViewHolder(itemView, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), adapterViewClickListener,position)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: ItemListItem, adapterViewClick: AdapterViewClickListener<ItemListItem>?, position: Int) {

            var p=position+1
            itemView.text_sr_no?.text = ""+p
            itemView.text_part_no?.text = allProducts.itemName
            itemView.text_amount_label_list.setText(""+allProducts.itemAmount)
            itemView.text_amount_label_list.visibility=View.GONE
            itemView.text_quantity.setText(""+allProducts.itemQty)




            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, adapterPosition
                )
            }
        }
    }

}