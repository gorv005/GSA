package com.gsa.model.feature_product

import com.google.gson.annotations.SerializedName

data class FeatureProductListItem(@SerializedName("p_unit")
                                  val pUnit: String = "",
                                  @SerializedName("company_id")
                                  val companyId: String = "",
                                  @SerializedName("ProductName")
                                  val productName: String = "",
                                  @SerializedName("p_oem_no")
                                  val pOemNo: String = "",
                                  @SerializedName("p_mrp")
                                  val pMrp: String = "",
                                  @SerializedName("p_purchase_price")
                                  val pPurchasePrice: String = "",
                                  @SerializedName("p_op_stock")
                                  val pOpStock: String = "",
                                  @SerializedName("is_feature")
                                  var isFeature: String = "",
                                  @SerializedName("CompanyName")
                                  val companyName: String = "",
                                  @SerializedName("p_tax_cat")
                                  val pTaxCat: String = "",
                                  @SerializedName("p_net_1")
                                  val pNet_1: String = "",
                                  @SerializedName("p_net_2")
                                  val pNet_2: String = "",
                                  @SerializedName("userPrice")
                                  val userPrice: String = "",
                                  @SerializedName("p_net_3")
                                  val pNet_3: String = "",
                                  @SerializedName("p_description")
                                  val pDescription: String = "",
                                  @SerializedName("CartItemQty")
                                  var CartItemQty: Int=0,
                                  @SerializedName("is_favorites")
                                  var is_favorites: Boolean =false,
                                  @SerializedName("id")
                                  val id: String = "")