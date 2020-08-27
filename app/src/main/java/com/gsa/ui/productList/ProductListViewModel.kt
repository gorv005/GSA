package com.gsa.ui.productList

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gsa.base.AbstractViewModel
import com.gsa.base.SingleLiveEvent
import com.gsa.common.CommonBoolean
import com.gsa.interfaces.SchedulerProvider
import com.gsa.managers.PreferenceManager
import com.gsa.model.SearchEvent
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.productList.ProductListResponse
import com.gsa.utils.Config
import com.gsa.utils.Logger

import retrofit2.HttpException

class ProductListViewModel(
    private val productListRepository: ProductListRepository,
    private val pre: PreferenceManager,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val searchEvent = SingleLiveEvent<SearchEvent>()
    val productModel = MutableLiveData<ProductListResponse>()



    fun getProducts(service :String, user_id: String, role_id: String
                     ,company_id: String,category_id: String,search_key: String) {
        searchEvent.value = SearchEvent(isLoading = true)

        launch {
            productListRepository.getProducts(service,user_id,role_id,company_id,category_id,search_key)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    productModel.value = it
                    searchEvent.value =
                        SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)
                }, {

                    try {
                        Logger.Debug(msg = it.toString())
                        val error = it as HttpException
                        val errorBody = error?.response()?.errorBody()?.run {

                            val r = string()
                            Logger.Debug(msg = r)
                            val error = r.replaceRange(0, 0, "")
                                .replaceRange(r.length, r.length, "")
                            //  val json = Gson().toJson(error)

                            productModel.value =
                                Gson().fromJson(error, ProductListResponse::class.java)

                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }



   fun getUserID(): String?{
      return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_ID)
   }
    fun getRoleID(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_ROLE_ID)
    }
    fun getUserName(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_NAME)
    }
    fun getCartValue(): Int?{
        return pre.getIntPreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE)
    }
    fun saveCartValue(cartValue:Int?){
        return pre.savePreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE,cartValue,0)
    }

    fun getRetailerID(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_RETAILTER_ID)
    }
    fun getIsSalesMan(): Boolean{
        return pre.getIsSalesMan()
    }
}