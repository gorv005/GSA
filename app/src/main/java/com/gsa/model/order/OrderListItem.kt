package com.gsa.model.order

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

data class OrderListItem(@SerializedName("order_status")
                         val orderStatus: String?,
                         @SerializedName("amount")
                         val amount: String?,
                         @SerializedName("c_date")
                         val cDate: String?,
                         @SerializedName("order_number")
                         val orderNumber: String?,
                         @SerializedName("invoice_no")
                         val invoiceNo: String?,
                         @SerializedName("ItemList")
                         val itemList: List<ItemListItem>?,
                         @SerializedName("id")
                         val id: String?,
                         @SerializedName("approved_date")
                         val approvedDate: String?,
                         @SerializedName("status")
                         val status: String?,
                         @SerializedName("invoice_date")
                         val invoiceDate: String?,
                         @SerializedName("sale_amount")
                         val saleAmount: String?,
                         @SerializedName("invoice_amount")
                         val invoice_amount: String?): Parcelable