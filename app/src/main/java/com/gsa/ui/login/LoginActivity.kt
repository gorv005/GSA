package com.gsa.ui.login

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.register.RegistrationActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) {
    override fun layout(): Int = R.layout.activity_login


    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun getIntent(context: Context): Intent? {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        btnLogin.setOnClickListener {
            this.let { UiUtils.hideSoftKeyboard(it) }
            doSignIn()

        }
        tvnewUserSignUp.setOnClickListener {
            startActivity(
                RegistrationActivity.getIntent(this),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
        subscribeLoading()
        subscribeUiLogin()
    }
    fun doSignIn() {

        this?.let { UiUtils.hideSoftKeyboard(it) }

        val validateEmailError = AndroidUtils.mobilePassword(etUsername.text.toString())
        val validatePasswordError = AndroidUtils.validateName(etPassword.text.toString())

        if (
            TextUtils.isEmpty(validateEmailError) || TextUtils.isEmpty(validatePasswordError)

        ) {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.login(etUsername.text.toString(), etPassword.text.toString(), "123", "123456")
            }

        } else {
            etUsername.error = validateEmailError
            etPassword.error = validatePasswordError
        }


    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.run {
                UiUtils.showInternetDialog(this@LoginActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUiLogin() {
        model.loginData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                showSnackbar(it.message, true)

                model.saveUserDetail(it.message, it.userList, true)


                this.let { UiUtils.hideSoftKeyboard(it) }
                startActivity(
                    LandingNavigationActivity.getIntent(this, 1),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
                )
            }
            else{
                showSnackbar(it.message, true)

            }
        })

    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}
