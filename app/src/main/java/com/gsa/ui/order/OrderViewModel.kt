package com.gsa.ui.order

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
import com.gsa.model.order.OrderListResponse
import com.gsa.utils.Config
import com.gsa.utils.Logger

import retrofit2.HttpException

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val pre: PreferenceManager,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val orderListModel = MutableLiveData<OrderListResponse>()
    val searchEvent = SingleLiveEvent<SearchEvent>()


    fun getOrders(service :String, user_id: String, role_id: String,retailer_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            orderRepository.getOrders(service,user_id,role_id,retailer_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    orderListModel.value = it
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

                            orderListModel.value =
                                Gson().fromJson(error, OrderListResponse::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }

    fun getOrders(service :String, user_id: String, role_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            orderRepository.getOrders(service,user_id,role_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    orderListModel.value = it
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

                            orderListModel.value =
                                Gson().fromJson(error, OrderListResponse::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }




    fun getRetailerID(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_RETAILTER_ID)
    }
    fun getIsSalesMan(): Boolean{
        return pre.getIsSalesMan()
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
}