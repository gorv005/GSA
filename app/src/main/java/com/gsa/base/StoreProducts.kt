package com.gsa.base

import android.content.Context
import com.gsa.model.productList.ProductListItem
import java.util.ArrayList
import java.util.HashMap

/**
 * Created by AB on 8/29/2018.
 */

class StoreProducts
{

    companion object {

        private var INSTANCE: StoreProducts? = null

        fun getInstance(): StoreProducts {
            if (INSTANCE == null)  // NOT thread safe!
                INSTANCE = StoreProducts()

            return INSTANCE!!
        }

    }
    private val context: Context? = null
    internal var products = HashMap<Int, ProductListItem>()

    val listOfProducts: List<ProductListItem>
        get() = ArrayList<ProductListItem>(products.values)

    fun saveProducts(productList: List<ProductListItem>?) {
        if (productList != null) {
            for (i in productList.indices) {
                products.put(productList[i].id.toInt(), productList[i])
            }
        }
    }

    fun getProduct(id: Int?): ProductListItem? {
        return products.get(id)
    }

    fun addProduct(allProduct: ProductListItem?) {
        if (allProduct != null) {
            products.put(allProduct?.id.toInt(), allProduct)
        }
    }

    fun clear() {
        INSTANCE = null
    }

}
