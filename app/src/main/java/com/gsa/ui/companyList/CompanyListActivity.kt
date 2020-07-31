package com.gsa.ui.companyList

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.callbacks.AdapterViewCompanyClickListener
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.home.CompanyListItem
import com.gsa.ui.cart.CartActivity
import com.gsa.ui.companyCategoryList.CompanyCategoryListActivity
import com.gsa.ui.landing.home.adapter.AdapterHomeCompanies
import com.gsa.ui.productList.ProductListActivity
import com.gsa.ui.search.SearchActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.activity_company_list.*
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.rv_companies

class CompanyListActivity : BaseActivity<CompanyListViewModel>(CompanyListViewModel::class),
    AdapterViewCompanyClickListener<CompanyListItem> {
    override fun onClickCompanyAdapterView(
        objectAtPosition: CompanyListItem,
        viewType: Int,
        position: Int
    ) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_COMPANIES -> {

                let {
                    let {
                        UiUtils.hideSoftKeyboard(it)
                        startActivity(
                            ProductListActivity.getIntent(
                                it, objectAtPosition.id, ""
                            ),
                            ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                        )
                    }

                }
            }
        }
    }

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun layout(): Int = R.layout.activity_company_list

    private var adapterCompanies: AdapterHomeCompanies? = null
    internal var companyList: ArrayList<CompanyListItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        companyList = ArrayList()

        val manager1 = GridLayoutManager(this, 4)
        rv_companies.layoutManager = manager1


        let {
            adapterCompanies = AdapterHomeCompanies(this, it)

        }
        rv_companies.adapter = adapterCompanies
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        rlCart.setOnClickListener {

            let {
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    CartActivity.getIntent(
                        it
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                )
            }
        }
        tvCompany.setOnClickListener {
            let {
                UiUtils.hideSoftKeyboard(it)
                startActivity(
                    SearchActivity.getIntent(
                        it,"",""
                    ),
                    ActivityOptions.makeSceneTransitionAnimation(it).toBundle()
                )
            }
        }
        rlSync.setOnClickListener {
            getData()

        }

        subscribeLoading()
        subscribeUi()
        getData()
    }

    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.shop_by_company)
        rlSync.visibility= View.VISIBLE
        if (!model.getCartValue().toString()!!.equals("0")
            &&!model.getCartValue().toString()!!.equals("")) {
            ivCounter.visibility = View.VISIBLE
            ivCounter.setText(
                model.getCartValue().toString()
            )


        } else {
            ivCounter.visibility = View.INVISIBLE

        }
    }
    private fun getData() {


        if (NetworkUtil.isInternetAvailable(this)) {
            model.getCompanies("Company List", model.getUserID()!!, model.getRoleID()!!)
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
                UiUtils.showInternetDialog(this, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {

        model.companyModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            showData(it)

        })

    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun showData(data: CompaniesListResponse?) {
        companyList = data?.companyList
        companyList?.let {

            adapterCompanies?.submitList(it)

            ViewCompat.setNestedScrollingEnabled(rv_companies, false)

            adapterCompanies?.notifyDataSetChanged()
        }
    }
    companion object {


        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, CompanyListActivity::class.java)


        }
    }

}

