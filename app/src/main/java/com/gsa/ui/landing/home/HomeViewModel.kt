package com.gsa.ui.landing.home

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

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val pre: PreferenceManager,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val categoryModel = MutableLiveData<CategoriesListResponse>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val companyModel = MutableLiveData<CompaniesListResponse>()
    val featureProductyModel = MutableLiveData<FeatureProductResponse>()

    fun getCategories(service :String, user_id: String, role_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            homeRepository.getCategories(service,user_id,role_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    categoryModel.value = it
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

                            categoryModel.value =
                                Gson().fromJson(error, CategoriesListResponse::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }

    fun getFeatureProduct(service :String, user_id: String, role_id: String) {
        launch {
            homeRepository.getFeatureProduct(service,user_id,role_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    featureProductyModel.value = it


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
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }

    fun getCompanies(service :String, user_id: String, role_id: String) {
        launch {
            homeRepository.getCompanies(service,user_id,role_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    companyModel.value = it

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

                            companyModel.value =
                                Gson().fromJson(error, CompaniesListResponse::class.java)


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

    fun getRetailerID(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_RETAILTER_ID)
    }
    fun getRoleID(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_ROLE_ID)
    }
    fun getUserName(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_USER_NAME)
    }
    fun getRetailerName(): String?{
        return pre.getStringPreference(Config.SharedPreferences.PROPERTY_RETAILTER_NAME)
    }
    fun getCartValue(): Int?{
        return pre.getIntPreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE)
    }
    fun saveCartValue(cartValue:Int?){
        return pre.savePreference(Config.SharedPreferences.PROPERTY_IS_CART_VALUE,cartValue,0)
    }
    fun getIsSalesMan(): Boolean{
        return pre.getIsSalesMan()
    }
    fun saveCartValue():String?{
        return pre.getRoleId()
    }
}