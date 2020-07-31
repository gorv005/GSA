package com.gsa.model.favorites

import com.google.gson.annotations.SerializedName

data class FavoriteListItem(@SerializedName("p_unit")
                            val pUnit: String = "",
                            @SerializedName("company_id")
                            val companyId: String = "",
                            @SerializedName("ProductName")
                            val productName: String = "",
                            @SerializedName("p_oem_no")
                            val pOemNo: String = "",
                            @SerializedName("CartItemQty")
                            var cartItemQty: Int = 0,
                            @SerializedName("p_mrp")
                            val pMrp: String = "",
                            @SerializedName("p_purchase_price")
                            val pPurchasePrice: String = "",
                            @SerializedName("p_op_stock")
                            val pOpStock: String = "",
                            @SerializedName("is_feature")
                            val isFeature: String = "",
                            @SerializedName("is_favorites")
                            val is_favorites: Boolean =false,
                            @SerializedName("CompanyName")
                            val companyName: String = "",
                            @SerializedName("p_tax_cat")
                            val pTaxCat: String = "",
                            @SerializedName("p_net_1")
                            val pNet1: String = "",
                            @SerializedName("p_net_2")
                            val pNet2: String = "",
                            @SerializedName("userPrice")
                            val userPrice: String = "",
                            @SerializedName("p_net_3")
                            val pNet3: String = "",
                            @SerializedName("p_description")
                            val pDescription: String = "",
                            @SerializedName("CategoryName")
                            val categoryName: String = "",
                            @SerializedName("id")
                            val id: String = "")