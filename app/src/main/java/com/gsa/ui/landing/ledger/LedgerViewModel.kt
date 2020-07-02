package com.gsa.ui.landing.ledger

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
import com.gsa.model.ledger.LedgerResponse

import com.gsa.ui.login.LoginRepository
import com.gsa.utils.Config
import com.gsa.utils.Logger
import retrofit2.HttpException

class LedgerViewModel(private val ledgerRepository: LedgerRepository,
                      private val pre: PreferenceManager,
                      private val scheduler: SchedulerProvider) :
    AbstractViewModel() {
    val ledgerData = MutableLiveData<LedgerResponse>()
    val searchEvent = SingleLiveEvent<SearchEvent>()

    fun getLedger(service :String, user_id: String, role_id: String) {
        searchEvent.value = SearchEvent(isLoading = true)


        launch {
            ledgerRepository.getLedger(service,user_id,role_id)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    ledgerData.value = it
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

                            ledgerData.value =
                                Gson().fromJson(error, LedgerResponse::class.java)
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