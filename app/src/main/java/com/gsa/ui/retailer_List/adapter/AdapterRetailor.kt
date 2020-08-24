package com.gsa.ui.retailer_List.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Filter
import android.widget.Filterable
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
import com.gsa.model.notification.NotificationListItem
import com.gsa.model.reatilter_list.RetailerlListItem
import com.gsa.util.UiUtils
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_feature_product.view.*
import kotlinx.android.synthetic.main.item_notification.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_retailor.view.*

class AdapterRetailor(private var retList: ArrayList<RetailerlListItem>,
    private val adapterViewClickListener: AdapterViewClickListener<RetailerlListItem>?,
    val activity: Activity
) : ListAdapter<RetailerlListItem, AdapterRetailor.ViewHolder>(
    AdapterRetailorCallback()
), Filterable
{

    var retFilterList = ArrayList<RetailerlListItem>()
    init {
        retFilterList = retList
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterRetailor.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_retailor, parent, false)

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


        fun bind(allProducts: RetailerlListItem, adapterViewClick: AdapterViewClickListener<RetailerlListItem>?) {

            itemView.text_retailor_name?.text = allProducts.name
            itemView.tv_shop_name?.text = allProducts.shopName
            itemView.tv_address?.text = allProducts.cityName +" "+allProducts.stateName

        }
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}