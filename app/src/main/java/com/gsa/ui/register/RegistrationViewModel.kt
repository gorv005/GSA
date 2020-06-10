package com.gsa.ui.register

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.gsa.base.AbstractViewModel
import com.gsa.base.SingleLiveEvent
import com.gsa.common.CommonBoolean
import com.gsa.interfaces.SchedulerProvider
import com.gsa.model.SearchEvent
import com.gsa.model.city_list.CityListResponse
import com.gsa.model.login.UserList
import com.gsa.model.register.RegisterResponsePayload
import com.gsa.model.stateList.StateListResponse
import com.gsa.utils.Logger
import retrofit2.HttpException

class RegistrationViewModel(
    private val registerRepository: RegisterRepository,
    private val scheduler: SchedulerProvider
) :
    AbstractViewModel() {
    val registerData = MutableLiveData<RegisterResponsePayload>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val cityData = MutableLiveData<CityListResponse>()
    val stateData = MutableLiveData<StateListResponse>()


    fun register(
        service: String, phone: String,
        role_id: String,
        name: String,
        shop_name: String,
        email: String, pincode: String,
        address: String, state_id: String,
        city_id: String, gst_no: String,
        pancard_no: String, adharcard_no: String,
        password: String
    ) {
        searchEvent.value = SearchEvent(isLoading = true)



        launch {
            registerRepository.register(
                service, phone,
                role_id,
                name,
                shop_name,
                email, pincode,
                address, state_id,
                city_id, gst_no,
                pancard_no, adharcard_no,
                password
            )
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    registerData.value = it
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

                            registerData.value =
                                Gson().fromJson(error, RegisterResponsePayload::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

    fun stateList(service: String) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            registerRepository.stateList(service)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    stateData.value = it
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

                            stateData.value =
                                Gson().fromJson(error, StateListResponse::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

    fun cityList(service: String, state_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)
        launch {
            registerRepository.cityList(service, state_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    cityData.value = it
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

                            cityData.value =
                                Gson().fromJson(error, CityListResponse::class.java)
                            searchEvent.value =
                                SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    // searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })


        }
    }

    public fun saveUserId(token:String?, user_id: String) {

        registerRepository.saveUserId(token,user_id)


    }
}