package com.gsa.ui.landing.home.adapter

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
import com.gsa.util.UiUtils
import com.gsa.utils.Config
import kotlinx.android.synthetic.main.item_feature_product.view.*
import kotlinx.android.synthetic.main.item_product.view.*

class AdapterFeatureProduct(
    private val adapterViewClickListener: AdapterViewFeatureProductClickListener<FeatureProductListItem>?,
    val activity: Activity
) : ListAdapter<FeatureProductListItem, AdapterFeatureProduct.ViewHolder>(
    AdapterFeatureProductCallback()
)
{
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): AdapterFeatureProduct.ViewHolder {
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


        fun bind(allProducts: FeatureProductListItem, adapterViewClick: AdapterViewFeatureProductClickListener<FeatureProductListItem>?) {

            itemView.text_part_no?.text = allProducts.productName
            itemView.text_mrp?.text = allProducts.pMrp
            itemView.tvQuantity.setText(""+allProducts.CartItemQty)
            itemView.text_company_name.text=allProducts.companyName

            itemView.tvQuantity.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    UiUtils.hideSoftKeyboard(activity)

                    allProducts.CartItemQty=itemView.tvQuantity.text.toString().toInt()
                    adapterViewClick?.onClickFeatureProductAdapterView(
                        allProducts,
                        Config.AdapterClickViewTypes.CLICK_VIEW_QUANTITY_CHANGED, adapterPosition
                    )
                    true
                } else {
                    false
                }
            }


            itemView.rlMinus.setOnClickListener {
                adapterViewClick?.onClickFeatureProductAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_MINUS_PRODUCT, adapterPosition
                )
            }
            itemView.rlPlus.setOnClickListener {
                adapterViewClick?.onClickFeatureProductAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_PLUS_PRODUCT, adapterPosition
                )
            }
            itemView.setOnClickListener {
                adapterViewClick?.onClickFeatureProductAdapterView(
                    allProducts,
                    Config.AdapterClickViewTypes.CLICK_VIEW_FEATURE_PRODUCT, adapterPosition
                )
            }
        }
    }

}