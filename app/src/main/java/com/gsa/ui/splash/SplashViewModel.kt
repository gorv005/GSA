package com.gsa.ui.splash

import android.os.Handler
import androidx.lifecycle.MutableLiveData

import com.gsa.base.AbstractViewModel

import com.gsa.managers.PreferenceManager


const val SPLASH_NEXT_HOME_ACTIVITY = 1
const val SPLASH_NEXT_ON_LOGIN_ACTIVITY = 2
class SplashViewModel (private val preferenceManager: PreferenceManager
) : AbstractViewModel() {

    val nextIntent = MutableLiveData<Int>()

    fun loadData() {
        Handler().postDelayed({

            if (!preferenceManager.isUserLoggedIn()) {
                nextIntent.postValue(SPLASH_NEXT_ON_LOGIN_ACTIVITY)
            }  else {
                nextIntent.postValue(SPLASH_NEXT_HOME_ACTIVITY)
            }
        }, 1500)
    }

}