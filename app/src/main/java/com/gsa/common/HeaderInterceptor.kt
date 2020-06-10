package com.gsa.common

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.gsa.ui.login.LoginActivity
import com.gsa.base.BaseApplication
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.adapter.rxjava2.Result.response



class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code() === 401 ) {
           /* startActivity(
                getIntent(BaseApplication?.getInstance()?.applicationContext)
            )*/
           var  intent = Intent(BaseApplication?.getInstance()?.applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(BaseApplication?.getInstance()?.applicationContext,intent,null)
          //  return response
        }/*else   if (response.code() === 502) {
//            Toast.makeText(BaseApplication?.getInstance()?.applicationContext,"Server issue",Toast.LENGTH_LONG).show()

            var  intent = Intent(BaseApplication?.getInstance()?.applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(BaseApplication?.getInstance()?.applicationContext,intent,null)
        }*/

        return response

    }
    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, LoginActivity::class.java)

        }
    }
}