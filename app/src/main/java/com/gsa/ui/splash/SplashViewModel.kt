package com.gsa.ui.splash

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson

import com.gsa.base.AbstractViewModel
import com.gsa.base.SingleLiveEvent
import com.gsa.common.CommonBoolean
import com.gsa.interfaces.SchedulerProvider

import com.gsa.managers.PreferenceManager
import com.gsa.model.SearchEvent
import com.gsa.model.productList.ProductListResponse
import com.gsa.model.splash.VersionResponse
import com.gsa.ui.productList.ProductListRepository
import com.gsa.utils.Logger
import retrofit2.HttpException


const val SPLASH_NEXT_HOME_ACTIVITY = 1
const val SPLASH_NEXT_ON_LOGIN_ACTIVITY = 2
class SplashViewModel (private val splashRepository: SplashRepository
                       ,private val preferenceManager: PreferenceManager,
                       private val scheduler: SchedulerProvider

) : AbstractViewModel() {

    val nextIntent = MutableLiveData<Int>()
    val searchEvent = SingleLiveEvent<SearchEvent>()
    val versionModel = MutableLiveData<VersionResponse>()
    fun loadData() {
        Handler().postDelayed({

            if (!preferenceManager.isUserLoggedIn()) {
                nextIntent.postValue(SPLASH_NEXT_ON_LOGIN_ACTIVITY)
            }  else {
                nextIntent.postValue(SPLASH_NEXT_HOME_ACTIVITY)
            }
        }, 1500)
    }

    fun getVersion(service :String) {
             searchEvent.value = SearchEvent(isLoading = true)

        launch {
            splashRepository.getVersion(service)
                .subscribeOn(scheduler.io())
                .observeOn(scheduler.ui())
                .subscribe({
                    Logger.Debug(msg = it.toString())
                    versionModel.value = it
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

                            versionModel.value =
                                Gson().fromJson(error, VersionResponse::class.java)

                             searchEvent.value =
                                  SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }// searchEvent.value = SearchEvent(isLoading = CommonBoolean.FALSE, isSuccess = false)
                })
        }
    }

}