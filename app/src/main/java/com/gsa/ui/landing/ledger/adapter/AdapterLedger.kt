package com.gsa.ui.landing.ledger.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gsa.R
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.ledger.ReportListItem
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_ledger.view.*

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
    companion object {

        var balance: Double = 0.0
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: ReportListItem, adapterViewClick: AdapterViewClickListener<ReportListItem>?) {

            itemView.tvDate_ledger?.text = allProducts.Date
            itemView.tvDescription_ledger?.text = allProducts.description

            if (allProducts.nType.equals("Debit", true)) {
                itemView.tvCredit_ledger.setText("" + 0.0)
                itemView.tvDebit_ledger.setText("" + allProducts.Amount)
                balance = balance + allProducts.Amount
                itemView.tvBalance_ledger.setText("" + balance)

            } else if (allProducts.nType.equals("Credit", true)) {
                itemView.tvDebit_ledger.setText("" + 0.0)
                itemView.tvCredit_ledger.setText("" + allProducts.Amount)
                balance = balance - allProducts.Amount
                itemView.tvBalance_ledger.setText("" + balance)

            }
            if (allProducts.description.equals("Cheque", true)) {
                itemView.tvDescription_ledger?.text = allProducts.description +" "+allProducts.Ref

            }
            itemView.setOnClickListener {
                adapterViewClick?.onClickAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_COMPANIES, adapterPosition
                )
            }

        }
    }

}