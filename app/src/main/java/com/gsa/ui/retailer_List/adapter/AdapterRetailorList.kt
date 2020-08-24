package com.gsa.ui.retailer_List.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.gsa.R
import com.gsa.model.reatilter_list.RetailerlListItem
import com.gsa.ui.landing.LandingNavigationActivity
import kotlinx.android.synthetic.main.item_retailor.view.*
import java.util.*
import kotlin.collections.ArrayList


class AdapterRetailorList(private var retList: ArrayList<RetailerlListItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    var relFilterList = ArrayList<RetailerlListItem>()

    lateinit var mcontext: Context

    class CountryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    init {
        relFilterList = retList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val countryListView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_retailor, parent, false)
        val sch = CountryHolder(countryListView)
        mcontext = parent.context
        return sch
    }

    override fun getItemCount(): Int {
        return relFilterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text_retailor_name?.text = relFilterList.get(position).name
        holder.itemView.tv_shop_name?.text = relFilterList.get(position).shopName
        holder.itemView.tv_address?.text =
            relFilterList.get(position).cityName + " " + relFilterList.get(position).stateName
       holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, LandingNavigationActivity::class.java)
            intent.putExtra("passselectedretailer", relFilterList[position])
            mcontext.startActivity(intent)
         //   Log.d("Selected:", countryFilterList[position])
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    relFilterList = retList
                } else {
                    val resultList = ArrayList<RetailerlListItem>()
                    for (row in retList) {
                        if (row.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    relFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = relFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                relFilterList = results?.values as ArrayList<RetailerlListItem>
                notifyDataSetChanged()
            }

        }
    }

}