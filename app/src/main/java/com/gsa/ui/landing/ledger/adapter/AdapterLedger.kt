package com.gsa.ui.landing.ledger.adapter

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
import com.gsa.model.ledger.ReportListItem
import com.gsa.util.UiUtils
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_feature_product.view.*
import kotlinx.android.synthetic.main.item_ledger.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterLedger(
    private val adapterViewClickListener: AdapterViewClickListener<ReportListItem>?,
    val activity: Activity
) : ListAdapter<ReportListItem, AdapterLedger.ViewHolder>(
    AdapterLedgerListCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterLedger.ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.item_ledger, parent, false)

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


        fun bind(allProducts: ReportListItem, adapterViewClick: AdapterViewClickListener<ReportListItem>?) {

            itemView.tvDate_ledger?.text = allProducts.date
            itemView.tvDescription_ledger?.text = allProducts.description
            itemView.tvCredit_ledger.setText(""+allProducts.credit)
            itemView.tvDebit_ledger.setText(""+allProducts.debit)
            itemView.tvBalance_ledger.setText(""+allProducts.closingBalance)

        }
    }

}