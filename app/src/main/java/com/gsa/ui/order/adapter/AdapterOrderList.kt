package com.gsa.ui.order.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            if (allProducts.status.equals("Approved Order", true)) {
              //  itemView.text_status_name.setTextColor(Color.GREEN)
                itemView.setBackgroundColor(Color.GREEN)
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