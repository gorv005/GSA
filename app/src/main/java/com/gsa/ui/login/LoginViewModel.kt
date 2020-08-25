package com.gsa.ui.login

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gsa.model.SearchEvent
import com.gsa.model.login.LoginRequest
import com.gsa.model.login.LoginResponsePayload
import com.gsa.model.login.UserList
import com.gsa.base.AbstractViewModel
import com.gsa.base.SingleLiveEvent
import com.gsa.common.CommonBoolean
import com.gsa.interfaces.SchedulerProvider
import com.gsa.managers.PreferenceManager

import com.gsa.ui.login.LoginRepository
import com.gsa.utils.Config
import com.gsa.utils.Logger
import retrofit2.HttpException

class LoginViewModel(private val loginRepository: LoginRepository, private val scheduler: SchedulerProvider,private val pre: PreferenceManager) :
    AbstractViewModel() {
    val loginData = MutableLiveData<LoginResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()

    fun login(mobile: String, password: String,os_type: String,gcm_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)

        val user = LoginRequest(mobile,password,os_type,
            gcm_id)

        launch {
            loginRepository.loginResponse(user)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    loginData.value = it
                    searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = true)

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

                            loginData.value =
                                Gson().fromJson(error, LoginResponsePayload::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }
                    }
                    catch (e :Exception){
                        e.printStackTrace()
                    }
                   // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

   public fun saveUserDetail(token:String?, user: UserList, isRemember :Boolean) {

        loginRepository.saveUserData(token,user,isRemember)


    }

    public fun saveisSalesMan(b:Boolean) {

        pre.savePreference(Config.SharedPreferences.IS_SALESMAN_LOGIN,b)


    }
}