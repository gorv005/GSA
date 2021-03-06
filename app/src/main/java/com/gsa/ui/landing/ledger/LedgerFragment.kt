package com.gsa.ui.landing.ledger


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.callbacks.AdapterViewClickListener
import com.gsa.model.ledger.ReportListItem
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.landing.ledger.adapter.AdapterLedger
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_ledger.*

/**
 * A simple [Fragment] subclass.
 */
class LedgerFragment : BaseFragment<LedgerViewModel>(LedgerViewModel::class),
    AdapterViewClickListener<ReportListItem> {
    override fun onClickAdapterView(
        objectAtPosition: ReportListItem,
        viewType: Int,
        position: Int
    ) {
    }

    override fun getLayoutId() = R.layout.fragment_ledger

    private var adapterLedger: AdapterLedger? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var manager2 = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        rv_ledger_report.layoutManager = manager2


        activity?.let {
            adapterLedger = AdapterLedger(this, it)

        }
        rv_ledger_report.adapter = adapterLedger
        subscribeLoading()
        subscribeUi()

    }

    public fun getData() {

        if (NetworkUtil.isInternetAvailable(activity)) {
            AdapterLedger.balance = 0.0
            if (model.isSalesMan()) {
                model.getLedger(
                    "Ledger Report",
                    model.getRetailerUSERID()!!,
                    model.getRetailerROLEID()!!
                )
            } else {
                model.getLedger("Ledger Report", model.getUserID()!!, model.getRoleID()!!)
            }
        }

    }

    companion object {
        const val PAGE_URL = "pageUrl"
        const val MAX_PROGRESS = 100
        fun getInstance(instance: Int): LedgerFragment {
            val bundle = Bundle()
            bundle.putInt(BaseFragment.ARGS_INSTANCE, instance)

            val fragment = LedgerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        if ((activity as LandingNavigationActivity).getVisibleFragmentLedger()) {

            (activity as LandingNavigationActivity).setTitleOnBar(AndroidUtils.getString(R.string.my_ledger))
            (activity as LandingNavigationActivity).setBack(false)
            (activity as LandingNavigationActivity).setSync(true)
            (activity as LandingNavigationActivity).setNotification(true)
            getData()
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
                UiUtils.showInternetDialog(context, R.string.something_went_wrong)
            }
        })
    }

    private fun subscribeUi() {
        model.ledgerData.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())
            if (it.status) {
                it.reportList?.let {
                    adapterLedger?.submitList(it)
                    adapterLedger?.notifyDataSetChanged()
                }            }
            else{
                showSnackbar(it.message, true)

            }
        })

    }

/*
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
    }
*/
/*
    private fun setWebClient() {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress < MAX_PROGRESS && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == MAX_PROGRESS) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }
*/
  /*  private fun loadUrl(pageUrl: String) {
        webView.loadUrl(pageUrl)
    }*/
    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

}
