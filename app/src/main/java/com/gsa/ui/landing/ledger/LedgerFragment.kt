package com.gsa.ui.landing.ledger


import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.net.http.SslError
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.Observer

import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.landing.home.HomeFragment
import com.gsa.ui.landing.home.HomeViewModel
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger
import com.gsa.utils.NetworkUtil
import kotlinx.android.synthetic.main.fragment_ledger.*

/**
 * A simple [Fragment] subclass.
 */
class LedgerFragment : BaseFragment<LedgerViewModel>(LedgerViewModel::class){
    override fun getLayoutId() = R.layout.fragment_ledger


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWebView()
        setWebClient()
        subscribeLoading()
        subscribeUi()
        if (NetworkUtil.isInternetAvailable(context)) {
            model.getLedger("Ledger Report", model.getUserID()!!, model.getRoleID()!!)
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
                loadUrl(it.url)
            }
            else{
                showSnackbar(it.message, true)

            }
        })

    }

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
    private fun loadUrl(pageUrl: String) {
        webView.loadUrl(pageUrl)
    }
    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

}
