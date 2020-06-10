package com.gsa.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.model.city_list.CityListItem
import com.gsa.model.stateList.StateListItem
import com.gsa.ui.register.adapter.CityDropDownAdapter
import com.gsa.ui.register.adapter.StateDropDownAdapter
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*

class RegistrationActivity : BaseActivity<RegistrationViewModel>(RegistrationViewModel::class) {
    override fun layout(): Int = R.layout.activity_registration

    companion object {
        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, RegistrationActivity::class.java)

        }
    }

    var stateDropDownAdapter: StateDropDownAdapter? = null
    var cityDropDownAdapter: CityDropDownAdapter? = null

    private var statesList: List<StateListItem>? = ArrayList()
    private var citiesList: List<CityListItem>? = ArrayList()
    var state_id: String? = null
    var city_id: String? = null
    var count: Int = 0

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()

        tv_tool_title.text = AndroidUtils.getString(R.string.register_as_retailer)
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (NetworkUtil.isInternetAvailable(this)) {
            model.stateList("State List")
        }
        etState.setOnClickListener {
            spinner_state.performClick()
        }
        et_city.setOnClickListener {
            spinner_city.performClick()
        }
        spinner_state?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (count > 0) {
                    state_id = statesList?.get(position)!!.stateId
                    etState.setText(statesList?.get(position)!!.stateName)
                    et_city.setText("")
                    if (NetworkUtil.isInternetAvailable(this@RegistrationActivity)) {
                        model.cityList("City List", statesList?.get(position)!!.stateId)
                    }
                } else {
                    count++
                }

            }

        }
        spinner_city?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                et_city.setText(citiesList?.get(position)!!.cityName)
                city_id = citiesList?.get(position)!!.cityId


            }

        }
        subscribeUi()
        subscribeLoading()
        btnRegister.setOnClickListener {
            doRegister()

        }
    }

    fun doRegister() {

        this?.let { UiUtils.hideSoftKeyboard(it) }

        val validateMobileError = AndroidUtils.mobilePassword(etMobile.text.toString())
        val validatePasswordError = AndroidUtils.validateName(etPassword.text.toString())
        val validateNameError = AndroidUtils.validateName(etName.text.toString())
        val validateShopNameError = AndroidUtils.validateName(etShop_name.text.toString())
        val validateGSTError = AndroidUtils.validateName(etGst.text.toString())
        val validatePanNumberError = AndroidUtils.validateName(et_pan_number.text.toString())
        val validateaadharNumberError = AndroidUtils.validateName(etaadhar_number.text.toString())
        val validateEmailError = AndroidUtils.validateEmail(et_email.text.toString())
        val validateAddressError = AndroidUtils.validateName(et_address.text.toString())
        val validateStateError = AndroidUtils.validateName(etState.text.toString())
        val validateCityError = AndroidUtils.validateName(et_city.text.toString())

        if (
            TextUtils.isEmpty(validateMobileError) || TextUtils.isEmpty(validatePasswordError)
            || TextUtils.isEmpty(validateNameError) || TextUtils.isEmpty(validateShopNameError)
            || TextUtils.isEmpty(validateGSTError) || TextUtils.isEmpty(validatePanNumberError)
            || TextUtils.isEmpty(validateaadharNumberError) || TextUtils.isEmpty(validateEmailError)
            || TextUtils.isEmpty(validateAddressError) || TextUtils.isEmpty(validateStateError)
            || TextUtils.isEmpty(validateCityError)
        ) {
            if (NetworkUtil.isInternetAvailable(this)) {
                model.register(
                    "User Signup",
                    etMobile.text.toString(),
                    "2",
                    etName.text.toString(),
                    etShop_name.text.toString(),
                    et_email.text.toString(),
                    "151001",
                    et_address.text.toString(),
                    state_id!!,
                    city_id!!,
                    etGst.text.toString()
                    ,
                    et_pan_number.text.toString(),
                    etaadhar_number.text.toString(),
                    etPassword.text.toString()
                )
            }

        } else {
            etMobile.error = validateMobileError
            etPassword.error = validatePasswordError
            etName.error = validateNameError
            etShop_name.error = validateShopNameError
            etGst.error = validateGSTError
            et_pan_number.error = validatePanNumberError
            etaadhar_number.error = validateaadharNumberError
            et_email.error = validateEmailError
            et_address.error = validateAddressError
            etState.error = validateStateError
            et_city.error = validateCityError

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
                UiUtils.showInternetDialog(this@RegistrationActivity, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.registerData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {

                showSnackbar(it.message, true)
                model.saveUserId(it.message, "" + it.userId)
                this.let { UiUtils.hideSoftKeyboard(it) }

            } else {
                showSnackbar(it.message, true)

            }
        })
        model.stateData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status.equals("true")) {
                statesList = it.stateList
                stateDropDownAdapter = StateDropDownAdapter(this, statesList)
                spinner_state.adapter = stateDropDownAdapter

            }
        })
        model.cityData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status.equals("true")) {
                citiesList = it.cityList
                cityDropDownAdapter = CityDropDownAdapter(this, citiesList)
                spinner_city.adapter = cityDropDownAdapter
            }
        })
    }

    fun showProgressDialog() {
        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}
