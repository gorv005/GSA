package com.gsa.ui.landing.accounts

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gsa.base.AbstractViewModel
import com.gsa.base.SingleLiveEvent
import com.gsa.common.CommonBoolean
import com.gsa.interfaces.SchedulerProvider
import com.gsa.managers.PreferenceManager
import com.gsa.model.SearchEvent
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.feature_product.FeatureProductResponse
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.login.UserList
import com.gsa.model.user.UserResponsePayload
import com.gsa.utils.Config
import com.gsa.utils.Logger

import retrofit2.HttpException

class AccountViewModel(
    private val accountRepository: AccountRepository,
    private val pre: PreferenceManager,
    private val scheduler: SchedulerProvider
) : AbstractViewModel() {


    val userModel = MutableLiveData<UserResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val updateUserModel = MutableLiveData<AddToCartResponse>()

    fun getUser(service :String, user_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            accountRepository.getUser(service,user_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    userModel.value = it
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

                            userModel.value =
                                Gson().fromJson(error, UserResponsePayload::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }

    fun updateUser(service: String,
                   user_id: String,
                   role_id: String,
                   name: String,
                   shopName: String,
                   email: String,
                   pincode: String,
                   address: String,
                   stateId: String,
                   cityId: String,
                   gstNo: String,
                   pancard_no: String,
                   aadharCard_no: String) {
        launch {
            searchEvent.value = SearchEvent(isLoading = true)

            accountRepository.updateProfile(service,user_id,role_id,name,shopName,email,pincode,address,stateId,cityId,
                gstNo,pancard_no,aadharCard_no)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    updateUserModel.value = it
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

                            updateUserModel.value =
                                Gson().fromJson(error, AddToCartResponse::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)
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
    fun saveUserDetails(user : UserList){
        pre.saveUserData(user)

    }
}