package com.gsa.ui.landing.accounts


import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer

import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.managers.PreferenceManager
import com.gsa.model.cart.AddToCartResponse
import com.gsa.model.city_list.CityListItem
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.stateList.StateListItem
import com.gsa.model.user.UserResponsePayload
import com.gsa.ui.cart.CartViewModel
import com.gsa.ui.change_password.ChangePasswordActivity
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.landing.home.HomeFragment
import com.gsa.ui.landing.home.HomeViewModel
import com.gsa.ui.login.LoginActivity
import com.gsa.ui.register.RegistrationActivity
import com.gsa.ui.register.RegistrationViewModel
import com.gsa.ui.register.adapter.CityDropDownAdapter
import com.gsa.ui.register.adapter.StateDropDownAdapter
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_account.etGst
import kotlinx.android.synthetic.main.fragment_account.etMobile
import kotlinx.android.synthetic.main.fragment_account.etName
import kotlinx.android.synthetic.main.fragment_account.etShop_name
import kotlinx.android.synthetic.main.fragment_account.etState
import kotlinx.android.synthetic.main.fragment_account.et_address
import kotlinx.android.synthetic.main.fragment_account.et_city
import kotlinx.android.synthetic.main.fragment_account.et_email
import kotlinx.android.synthetic.main.fragment_account.et_pan_number
import kotlinx.android.synthetic.main.fragment_account.etaadhar_number
import kotlinx.android.synthetic.main.fragment_account.spinner_city
import kotlinx.android.synthetic.main.fragment_account.spinner_state
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class FragmentAccount : BaseFragment<AccountViewModel>(AccountViewModel::class){
    override fun getLayoutId() = R.layout.fragment_account

    var stateDropDownAdapter: StateDropDownAdapter? = null
    var cityDropDownAdapter: CityDropDownAdapter? = null
    val modelReg: RegistrationViewModel by viewModel()
     var userResponsePayload : UserResponsePayload?=null
    private var statesList: ArrayList<StateListItem>? = ArrayList()
    private var citiesList: ArrayList<CityListItem>? = ArrayList()
    var state_id: String? = null
    var city_id: String? = null
    var count: Int = 0
    var count1: Int = 0

    var  isGetting: Boolean=false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
                    if (NetworkUtil.isInternetAvailable(context)) {
                        modelReg.cityList("City List", statesList?.get(position)!!.stateId)
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
                if (count1 > 0) {
                    et_city.setText(citiesList?.get(position)!!.cityName)
                    city_id = citiesList?.get(position)!!.cityId
                }
                else {
                    count1++
                }

            }

        }

        etPassword.setOnClickListener {
            activity?.let {
                startActivity(
                    ChangePasswordActivity.getIntent(it),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()

                )
            }
        }
        subscribeUi()
        subscribeLoading()

        btnRegister.setOnClickListener {
            doUpdate()

        }

        btnLogout.setOnClickListener {
            logout()

        }
       getData()
    }

    public fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            modelReg.stateList("State List")
        }

    }

    companion object {

        fun getInstance(instance: Int): FragmentAccount {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = FragmentAccount()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentAccount()) {

            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.my_account))
            (activity as LandingNavigationActivity).setBack(false)
            (activity as LandingNavigationActivity).setSync(true)
            (activity as LandingNavigationActivity).setNotification(true)

        }
    }
    private fun logout() {
        activity?.let {
            val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(it)

            // set message of alert dialog
            dialogBuilder.setMessage("Do you want to logout from this application ?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->

                    var preferenceManager = PreferenceManager(it)
                    preferenceManager.savePreference(
                        Config.SharedPreferences.PROPERTY_LOGIN_PREF,
                        false
                    )
                    preferenceManager.savePreference(
                        Config.SharedPreferences.IS_SALESMAN_LOGIN,
                        false
                    )
                    startActivity(
                        LoginActivity.getIntent(it),
                        ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
                    )


                })
                // negative button text and action
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle(AndroidUtils.getString(R.string.logout))
            // show alert dialog
            alert.show()
        }
    }

    fun doUpdate() {

        activity?.let { UiUtils.hideSoftKeyboard(it) }

        val validateMobileError = AndroidUtils.mobilePassword(etMobile.text.toString())
        val validateNameError = AndroidUtils.validateName(etName.text.toString())
        val validateShopNameError = AndroidUtils.validateName(etShop_name.text.toString())
        val validateGSTError = AndroidUtils.validateName(etGst.text.toString())
        val validateGSTValidError = AndroidUtils.gstValidation(etGst.text.toString())

        val validatePanNumberError = AndroidUtils.panValidation(et_pan_number.text.toString())
        val validateaadharNumberError = AndroidUtils.aadharValidation(etaadhar_number.text.toString())
        val validateEmailError = AndroidUtils.validateEmail(et_email.text.toString())
        val validateAddressError = AndroidUtils.validateName(et_address.text.toString())
        val validateStateError = AndroidUtils.validateName(etState.text.toString())
        val validateCityError = AndroidUtils.validateName(et_city.text.toString())

        if (
            TextUtils.isEmpty(validateMobileError)
            && TextUtils.isEmpty(validateNameError) && TextUtils.isEmpty(validateShopNameError)
            && TextUtils.isEmpty(validateMobileError) && TextUtils.isEmpty(validateMobileError)
            && TextUtils.isEmpty(validateMobileError)
            && TextUtils.isEmpty(validateMobileError) && TextUtils.isEmpty(validateEmailError)
            && TextUtils.isEmpty(validateAddressError) && TextUtils.isEmpty(validateStateError)
            && TextUtils.isEmpty(validateCityError)
        ) {
            if (NetworkUtil.isInternetAvailable(context)) {
                model.updateUser(
                    "Update Profile",
                    ""+model.getUserID(),
                    ""+model.getRoleID(),
                    etName.text.toString(),
                    etShop_name.text.toString(),
                    et_email.text.toString(),
                    "",
                    et_address.text.toString(),
                    state_id!!,
                    city_id!!,
                    etGst.text.toString()
                    ,
                    et_pan_number.text.toString(),
                    etaadhar_number.text.toString())
            }

        } else {
            etMobile.error = validateMobileError
            etName.error = validateNameError
            etShop_name.error = validateShopNameError
            etGst.error = validateGSTError
            etGst.error = validateGSTValidError
            et_pan_number.error = validatePanNumberError
            etaadhar_number.error = validateaadharNumberError
            et_email.error = validateEmailError
            et_address.error = validateAddressError
            etState.error = validateStateError
            et_city.error = validateCityError

        }


    }

    private fun getUser() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getUser("User List", model.getUserID()!!)
        }

    }

    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.let {
                UiUtils.showInternetDialog(activity, R.string.something_went_wrong)
            }
        })


    }

    private fun subscribeUi() {
        model.userModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
        model.updateUserModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })
        modelReg.stateData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status.equals("true")) {
                statesList = it.stateList
                stateDropDownAdapter = StateDropDownAdapter(context!!, statesList)
                spinner_state.adapter = stateDropDownAdapter
                getUser()
            }
        })
        modelReg.cityData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status.equals("true")) {
                citiesList = it.cityList
                cityDropDownAdapter = CityDropDownAdapter(context!!, citiesList)
                spinner_city.adapter = cityDropDownAdapter

                if(userResponsePayload!=null && isGetting){
                    var i: Int =0
                    while (i<citiesList!!.size){
                        if(citiesList!!.get(i).cityId.equals(userResponsePayload?.userList?.cityId)){
                            et_city.setText(citiesList!!.get(i).cityName)
                            break
                        }
                        i++
                    }
                    isGetting=false
                }
            }
        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    private fun showData(data: UserResponsePayload?) {
        if (data!!.status) {
            userResponsePayload = data
            etMobile.setText(data?.userList?.phone)
            etName.setText(data?.userList?.name)
            etShop_name.setText(data?.userList?.shopName)
            etGst.setText(data?.userList?.gstNo)
            et_pan_number.setText(data?.userList?.pancardNo)
            etaadhar_number.setText(data?.userList?.pancardNo)
            et_email.setText(data?.userList?.email)
            et_address.setText(data?.userList?.address)
            state_id = data?.userList?.stateId
            city_id = data?.userList?.cityId
            var i: Int = 0
            while (i < statesList!!.size) {
                if (statesList!!.get(i).stateId.equals(data?.userList?.stateId)) {
                    etState.setText(statesList!!.get(i).stateName)
                    if (NetworkUtil.isInternetAvailable(context)) {
                        isGetting = true
                        modelReg.cityList("City List", statesList?.get(i)!!.stateId)
                    }
                    break
                }
                i++
            }
        }

    }
    private fun showData(data: AddToCartResponse?) {

        showSnackbar(data?.message, true)



    }
}
