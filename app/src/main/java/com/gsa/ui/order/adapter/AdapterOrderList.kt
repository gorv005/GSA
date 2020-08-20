package com.gsa.ui.order.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gsa.R
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.order.OrderListItem
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_orders.view.*

class AdapterOrderList(
    private val adapterViewClickListener: AdapterViewClickListener<OrderListItem>?,
    val activity: Activity
) : ListAdapter<OrderListItem, AdapterOrderList.ViewHolder>(
    AdapterOrderCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterOrderList.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_orders, parent, false)

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


        fun bind(allProducts: OrderListItem, adapterViewClick: AdapterViewClickListener<OrderListItem>?) {

            itemView.text_order_no?.text = allProducts.orderNumber
            itemView.text_date_name?.text = allProducts.cDate
            if (allProducts.status.equals("New Order", true)) {
                itemView.ll_amount_no.visibility = View.GONE
            }
            itemView.text_amount.setText("" + allProducts.invoice_amount)
            itemView.text_status_name.text=allProducts.status
            if (allProducts.orderStatus.equals("1", true)) {//approved order
          //      itemView.text_status_name.setBackgroundColor(Color.parseColor("#32B475"))
               // itemView.setBackgroundColor(Color.GREEN)
                itemView.text_status_name.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.green_1));

            }
            else if (allProducts.orderStatus.equals("3", true)) {//cancel order
               // itemView.text_status_name.setBackgroundColor(Color.parseColor("#FF0000"))
                // llOrder.setBackgroundColor(Color.GREEN)
                itemView.text_status_name.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.red_1));

            }
            else if (allProducts.orderStatus.equals("0", true)) {//new order
              //  itemView.text_status_name.setBackgroundColor(Color.parseColor("#FF4500"))
                // llOrder.setBackgroundColor(Color.GREEN)
                itemView.text_status_name.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.orange_1));

            }
            else if (allProducts.orderStatus.equals("2", true)) {//fully paid
            //    itemView.text_status_name.setBackgroundColor(Color.parseColor("#00BCD4"))
                // llOrder.setBackgroundColor(Color.GREEN)
                itemView.text_status_name.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.colorPrimary));

            }
            else if (allProducts.orderStatus.equals("4", true)) {//partially paid
           //     itemView.text_status_name.setBackgroundColor(Color.parseColor("#8B0000"))
                // llOrder.setBackgroundColor(Color.GREEN)
                itemView.text_status_name.setBackgroundTintList(ContextCompat.getColorStateList(activity, R.color.brown));

            }
            itemView.text_status_name.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, adapterPosition
                )
            }

            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT, adapterPosition
                )
            }
        }
    }

}