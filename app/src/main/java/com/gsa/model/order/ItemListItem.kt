package com.gsa.model.order

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemListItem(@SerializedName("item_id")
                        val itemId: String?,
                        @SerializedName("total_amount")
                        val totalAmount: String?,
                        @SerializedName("sale_qty")
                        val saleQty: String?,
                        @SerializedName("discount_amount")
                        val discountAmount: String?,
                        @SerializedName("sale_discount")
                        val saleDiscount: String?,
                        @SerializedName("item_name")
                        val itemName: String?,
                        @SerializedName("item_amount")
                        val itemAmount: String?,
                        @SerializedName("id")
                        val id: String?,
                        @SerializedName("order_id")
                        val orderId: String?,
                        @SerializedName("item_qty")
                        val itemQty: String?,
                        @SerializedName("sale_total")
                        val saleTotal: String?): Parcelable