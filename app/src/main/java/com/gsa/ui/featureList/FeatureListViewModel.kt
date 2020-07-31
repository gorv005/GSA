package com.gsa.ui.featureList

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
import com.gsa.utils.Config
import com.gsa.utils.Logger

import retrofit2.HttpException

class FeatureListViewModel(
    private val featureListRepository: FeatureListRepository,
    private val pre: PreferenceManager,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val searchEvent = SingleLiveEvent<SearchEvent>()
    val featureProductyModel = MutableLiveData<FeatureProductResponse>()

    fun getFeatureProduct(service :String, user_id: String, role_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)

        launch {
            featureListRepository.getFeatureProduct(service,user_id,role_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    featureProductyModel.value = it

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

                            featureProductyModel.value =
                                Gson().fromJson(error, FeatureProductResponse::class.java)
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
}