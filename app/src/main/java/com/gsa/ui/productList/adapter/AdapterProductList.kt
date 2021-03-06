package com.gsa.ui.productList.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.drawable.ScalingUtils
import com.gsa.R
import com.gsa.base.StoreProducts
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.callbacks.AdapterViewCompanyClickListener
import com.gsa.callbacks.AdapterViewFeatureProductClickListener
import com.gsa.managers.ImageRequestManager
import com.gsa.model.feature_product.FeatureProductListItem
import com.gsa.model.home.CompanyListItem
import com.gsa.model.productList.ProductListItem
import com.gsa.util.UiUtils
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
        var allProduct =
            StoreProducts.getInstance().getProduct(getItem(position)?.id?.toInt())
        if (allProduct == null) {
            allProduct = getItem(position)
        }
        holder.bind(allProduct!!, adapterViewClickListener)
    }
    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {


        fun bind(allProducts: ProductListItem, adapterViewClick: AdapterViewFeatureProductClickListener<ProductListItem>?) {

            itemView.text_part_no?.text = allProducts.productName
            itemView.text_mrp?.text = allProducts.pDescription
            itemView.tvQuantity.setText(""+allProducts.CartItemQty)
            itemView.text_company_name.text=allProducts.companyName
            if(allProducts.is_favorites.equals("1")){
                itemView.iv_favorites.setImageResource(R.drawable.ic_favorite_black_24dp)
            }else{
                itemView.iv_favorites.setImageResource(R.drawable.ic_favorite_border_black_24dp)

            }
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
            itemView.rl_favorites.setOnClickListener {
                if(allProducts.is_favorites.equals("1")){
                    itemView.iv_favorites.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                    adapterViewClick?.onClickFeatureProductAdapterView(
                        allProducts,
                        Config.AdapterClickViewTypes.CLICK_VIEW_DELETE_FAVORITES_PRODUCT, adapterPosition
                    )

                }else{
                    itemView.iv_favorites.setImageResource(R.drawable.ic_favorite_black_24dp)
                    adapterViewClick?.onClickFeatureProductAdapterView(
                        allProducts,
                        Config.AdapterClickViewTypes.CLICK_VIEW_ADD_FAVORITES_PRODUCT, adapterPosition
                    )

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