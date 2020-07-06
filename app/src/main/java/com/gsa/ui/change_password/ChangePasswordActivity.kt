package com.gsa.ui.change_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class ChangePasswordActivity : BaseActivity<ChangePasswordViewModel>(ChangePasswordViewModel::class){
    override fun layout(): Int = R.layout.activity_change_password


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
            val intent = Intent(context, ChangePasswordActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnChangePassword.setOnClickListener {
            doChangepassword()
        }
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        subscribeLoading()
        subscribeUi()
    }

    override fun onResume() {
        super.onResume()

        tv_tool_title.text = AndroidUtils.getString(R.string.change_password)
        iv_cart.visibility= View.GONE

    }
    fun doChangepassword() {

        this?.let { UiUtils.hideSoftKeyboard(it) }

        val validateOldPasswordError = AndroidUtils.validateName(etOldPassword.text.toString())
        val validateNewPasswordError = AndroidUtils.validateName(etNewPassword.text.toString())

        if (
            TextUtils.isEmpty(validateOldPasswordError) || TextUtils.isEmpty(validateNewPasswordError)

        ) {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.changepassword(""+model.getUserID(), etOldPassword.text.toString(), etNewPassword.text.toString())
            }

        } else {
            etOldPassword.error = validateOldPasswordError
            etNewPassword.error = validateNewPasswordError
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
                UiUtils.showInternetDialog(this@ChangePasswordActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.changepasswordModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

                showSnackbar(it.message, true)
               etOldPassword.setText("")
                etNewPassword.setText("")

            } else {
                showSnackbar(it.message, true)

            }
        })

    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}
