package com.gsa.ui.notification.adapter

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
import com.gsa.model.notification.NotificationListItem
import com.gsa.util.UiUtils
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_feature_product.view.*
import kotlinx.android.synthetic.main.item_notification.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterNotification(
    private val adapterViewClickListener: AdapterViewClickListener<NotificationListItem>?,
    val activity: Activity
) : ListAdapter<NotificationListItem, AdapterNotification.ViewHolder>(
    AdapterNotificationCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterNotification.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_notification, parent, false)

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


        fun bind(allProducts: NotificationListItem, adapterViewClick: AdapterViewClickListener<NotificationListItem>?) {

            itemView.text_notification_data?.text = allProducts.notificationText
            itemView.text_date?.text = allProducts.cDate

        }
    }

}