package com.gsa.ui.splash

import android.app.ActivityOptions
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.gsa.R
import com.gsa.base.BaseActivity
import com.gsa.model.home.CompaniesListResponse
import com.gsa.model.splash.VersionResponse
import com.gsa.ui.landing.LandingNavigationActivity
import com.gsa.ui.login.LoginActivity
import com.gsa.util.UiUtils
import com.gsa.utils.AndroidUtils
import com.gsa.utils.Logger

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
        subscribeLoading()
        subscribeUi()

    }

    override fun onResume() {
        super.onResume()
        model.getVersion("Version")
    }
    private fun subscribeLoading() {

        model.searchEvent.observe(this, Observer {
            if (it.isLoading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
            it.error?.let {
                UiUtils.showInternetDialog(this@SplashActivity, R.string.something_went_wrong)
            }
        })


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

        model.versionModel.observe(this, Observer {
            Logger.Debug("DEBUG", it.toString())

                showData(it)
        })
    }

    private fun showData(data: VersionResponse?) {
        if(data!!.status){
            val versionName = GetAppVersion(this)

            if(versionName.equals(data?.versionList?.versionName)){
                model.loadData()

               // onUpdateNeeded(data?.versionList?.playstoreUrl)

            }else{
               // model.loadData()
                onUpdateNeeded(data?.versionList?.playstoreUrl)
            }
        }

    }

    fun onUpdateNeeded(updateUrl: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setTitle("New version available")
            .setMessage("Please, update app to new version to continue.")
            .setPositiveButton("Update",
                DialogInterface.OnClickListener { dialog, which -> redirectStore(updateUrl) })
            .setNegativeButton("No, thanks",
                DialogInterface.OnClickListener { dialog, which -> finish() }).create()
        dialog.show()
    }

    private fun redirectStore(updateUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    fun GetAppVersion(context: Context): String {
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return version
    }
    fun showProgressDialog() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }
}
