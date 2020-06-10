package com.gsa.ui.splash

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.login.LoginActivity

class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class) {
    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun layout(): Int = R.layout.activity_splash


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(layout())

        subscribeUi()

        model.loadData()
    }
    private fun subscribeUi() {
        model.nextIntent.observe(this, Observer {
            startActivity(
                when (it) {
                    SPLASH_NEXT_HOME_ACTIVITY -> {

                        LandingNavigationActivity.getIntent(this,1)
                    }
                    SPLASH_NEXT_ON_LOGIN_ACTIVITY -> {

                        LoginActivity.getIntent(this)
                    }
                    else -> LoginActivity.getIntent(this)
                }
           ,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle() )

            finish()
        })
    }

}
