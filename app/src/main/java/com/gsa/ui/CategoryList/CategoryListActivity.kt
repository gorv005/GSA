package com.gsa.ui.CategoryList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.home.categories.CategoriesListResponse
import com.gsa.model.home.categories.CategoryListItem
import com.gsa.ui.companyList.CompanyListActivity
import com.gsa.ui.landing.home.adapter.AdapterHomeCategories
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.app_custom_tool_bar.*
import kotlinx.android.synthetic.main.fragment_home.*

class CategoryListActivity : BaseActivity<CategoryListViewModel>(CategoryListViewModel::class),
    AdapterViewClickListener<CategoryListItem> {
    override fun onClickAdapterView(
        objectAtPosition: CategoryListItem,
        viewType: Int,
        position: Int
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun layout(): Int = R.layout.activity_category_list

    override fun tag(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun title(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleColor(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var adapterCategories: AdapterHomeCategories? = null
    internal var categoryList: ArrayList<CategoryListItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)
        categoryList = ArrayList()

        val manager1 = GridLayoutManager(this, 4)
        rv_categories.layoutManager = manager1


        let {
            adapterCategories = AdapterHomeCategories(this, it)

        }
        rv_categories.adapter = adapterCategories
        fl_left_img_container.setOnClickListener {
            onBackPressed()
        }
        subscribeLoading()
        subscribeUi()
        getData()
    }


    override fun onResume() {
        super.onResume()
        tv_tool_title.text = AndroidUtils.getString(R.string.shop_by_category)

    }

    private fun getData() {


        if (NetworkUtil.isInternetAvailable(this)) {
            model.getCategories("Category List", model.getUserID()!!, model.getRoleID()!!)
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

        model.categoryModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    private fun showData(data: CategoriesListResponse?) {

        categoryList = data?.categoryList
        categoryList?.let {

            adapterCategories?.submitList(it)

            ViewCompat.setNestedScrollingEnabled(rv_categories, false)
            adapterCategories?.notifyDataSetChanged()
        }
    }

    companion object {


        fun getIntent(context: Context?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, CategoryListActivity::class.java)


        }
    }
}
