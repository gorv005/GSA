package com.gsa.ui.retailer_List

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gsa.base.AbstractViewModel
import com.gsa.base.SingleLiveEvent
import com.gsa.interfaces.SchedulerProvider
import com.gsa.managers.PreferenceManager
import com.gsa.model.SearchEvent
import com.gsa.model.reatilter_list.RetailterListResponse
import com.gsa.utils.Config
import com.gsa.utils.Logger
import retrofit2.HttpException


class RetailterListViewModel(
    private val retailerListRepository: RetailerListRepository,
    private val pre: PreferenceManager,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val searchEvent = SingleLiveEvent<SearchEvent>()
    val RetailorListModel = MutableLiveData<RetailterListResponse>()


    fun getRetailorList(
        service: String
    ) {
        //     searchEvent.value = SearchEvent(isLoading = true)

        launch {
            retailerListRepository.retailterList(service)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    RetailorListModel.value = it
                    /* searchEvent.value =
                         SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)*/
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

                            RetailorListModel.value =
                                Gson().fromJson(error, RetailterListResponse::class.java)

                            /*  searchEvent.value =
                                  SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)*/

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }


    fun getUserID(): String? {
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_ID)
    }

    fun getRoleID(): String? {
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_ROLE_ID)
    }

    fun getUserName(): String? {
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_NAME)
    }

    fun getCartValue(): Int? {
        return pre.getIntPreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE)
    }

    fun saveCartValue(cartValue: Int?) {
        return pre.savePreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE, cartValue, 0)
    }
}