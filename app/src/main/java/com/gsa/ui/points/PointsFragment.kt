package com.gsa.ui.points


import android.app.ActivityOptions
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.managers.PreferenceManager
import com.gsa.model.order.OrderListItem
import com.gsa.model.order.OrderListResponse
import com.gsa.model.points.PointListItem
import com.gsa.model.points.PointsResponse
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.login.LoginActivity
import com.gsa.ui.order.OrderFragment
import com.gsa.ui.order.OrderViewModel
import com.gsa.ui.order.adapter.AdapterOrderList
import com.gsa.ui.points.adapter.AdapterPointsList
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Config
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.fragment_points.*

/**
 * A simple [Fragment] subclass.
 */
class PointsFragment : BaseFragment<PointsViewModel>(PointsViewModel::class),
    AdapterViewClickListener<PointListItem> {

    override fun getLayoutId() = R.layout.fragment_points
    companion object {

        fun getInstance(instance: Int): PointsFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = PointsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClickAdapterView(objectAtPosition: PointListItem, viewType: Int, position: Int) {
    }


    private var adapterPointsList: AdapterPointsList? = null
    internal var pointList: ArrayList<PointListItem>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var manager2 = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rv_points.layoutManager = manager2
        activity?.let {
            adapterPointsList = AdapterPointsList(this, it)
            rv_points.adapter = adapterPointsList

        }
        rlRedeemPoints.setOnClickListener {
            redeemPoints()
        }
        rlGetPoints.setOnClickListener {
            activity?.let {
                startActivity(LandingNavigationActivity.getIntent(it, 1))
            }
        }
        llRewardPoint.setOnClickListener {
            redeemPoints()
        }
        tvSpendPoint.setOnClickListener {
            redeemPoints()
        }
        llShopNow.setOnClickListener {
            activity?.let {
                startActivity(LandingNavigationActivity.getIntent(it, 1))
            }
        }
        tvEarnPoint.setOnClickListener {
            activity?.let {
                startActivity(LandingNavigationActivity.getIntent(it, 1))
            }
        }
        subscribeLoading()
        subscribeUi()
        getData()

    }
    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentPoints()) {

            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.my_points))
            (activity as LandingNavigationActivity).setBack(false)
            (activity as LandingNavigationActivity).setSync(true)
            (activity as LandingNavigationActivity).setNotification(true)

        }
    }

    public fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.getPoints("Point List", model.getUserID()!!, model.getRoleID()!!)
        }

    }
    private fun redeemPoints(points : String) {

        if (NetworkUtil.isInternetAvailable(activity)) {
            model.reedemPoints("Redeem Request", model.getUserID()!!, model.getRoleID()!!,points)
        }

    }
    private fun redeemPoints() {
        activity?.let {
            val dialogBuilder = android.app.AlertDialog.Builder(it)
            val inflater = layoutInflater
            dialogBuilder.setTitle("Redeem Points")
            // set message of alert dialog
            val dialogLayout = inflater.inflate(R.layout.dialog_redeem_points, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.etPoints)                // if the dialog is cancelable
            dialogBuilder.setView(dialogLayout)

                // positive button text and action
                .setPositiveButton("Done", DialogInterface.OnClickListener { dialog, id ->
                    if(editText.text.toString().toDouble()<=tvPoints.text.toString().toDouble()) {
                        redeemPoints(editText.text.toString())
                        dialog.dismiss()
                    }else{
                        showSnackbar(AndroidUtils.getString(R.string.entered_points_greater), false)

                    }

                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle(AndroidUtils.getString(R.string.Redeem_Points))
            // show alert dialog
            alert.show()
        }
    }

/*
    fun withEditText(view: View) {
        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        builder.setTitle("Redeem Points")
        val dialogLayout = inflater.inflate(R.layout.dialog_redeem_points, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.etPoints)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Done") {
                dialogInterface, i -> redeemPoints(editText.text.toString())
            dialogInterface.dismiss()
        }
        builder.show()
    }
*/
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
        model.pointsModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

            showData(it)

        })
        model.redeemPointsListModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if(it.status) {
                showSnackbar(it.message, true)
                getData()
            }
            else{
                showSnackbar(it.message, true)

            }
        })
    }

    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }


    private fun showData(data: PointsResponse?) {
        tvPoints.text=""+data?.totalPoints

        pointList = data?.pointList
        pointList?.let {

            adapterPointsList?.submitList(it)

            adapterPointsList?.notifyDataSetChanged()
        }

        pointList?.let {
            if(it.size==0){
                tvRecentHistory.visibility=View.GONE
            }
        }
    }
}
