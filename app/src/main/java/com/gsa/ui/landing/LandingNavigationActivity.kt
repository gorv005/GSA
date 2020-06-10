package com.gsa.ui.landing

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.gsa.R
import com.gsa.base.BaseFragment
import com.gsa.ui.landing.home.HomeFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavLogger
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import kotlinx.android.synthetic.main.activity_landing_navigation.*

const val INDEX_HOME = FragNavController.TAB1
const val INDEX_ORDERS = FragNavController.TAB2
const val INDEX_POINTS = FragNavController.TAB3
const val INDEX_LEDGER = FragNavController.TAB4
const val INDEX_ACCOUNT = FragNavController.TAB5

class LandingNavigationActivity : AppCompatActivity(), BaseFragment.FragmentNavigation,
    FragNavController.TransactionListener, FragNavController.RootFragmentListener{


    override val numberOfRootFragments: Int = 5
    private val fragNavController: FragNavController =
        FragNavController(supportFragmentManager, R.id.container)

    override fun pushFragment(fragment: Fragment, sharedElementList: List<Pair<View, String>>?) {
        val options = FragNavTransactionOptions.newBuilder()
        options.reordering = true
        sharedElementList?.let {
            it.forEach { pair ->
                options.addSharedElement(pair)
            }
        }
        fragNavController.pushFragment(fragment, options.build())
    }

    override fun onTabTransaction(fragment: Fragment?, index: Int) {
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }
    override fun onFragmentTransaction(
        fragment: Fragment?,
        transactionType: FragNavController.TransactionType
    ) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(fragNavController.isRootFragment.not())

    }
    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            INDEX_HOME -> return HomeFragment.getInstance(0)
            INDEX_POINTS -> return HomeFragment.getInstance(0)
            INDEX_ORDERS -> return HomeFragment.getInstance(0)
            INDEX_LEDGER -> return HomeFragment.getInstance(0)
            INDEX_ACCOUNT -> return HomeFragment.getInstance(0)

        }
        throw IllegalStateException("Need to send an index that we know")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> fragNavController.popFragment()
        }
        return true
    }
    companion object {
        const val KEY_TAB = "KEY_TAB"

        fun getIntent(context: Context, f: Int): Intent? {
            val intent = Intent(context, LandingNavigationActivity::class.java)
            intent.putExtra(KEY_TAB, f)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_navigation)
        fragNavController.apply {
            transactionListener = this@LandingNavigationActivity
            rootFragmentListener = this@LandingNavigationActivity
            createEager = false
            fragNavLogger = object : FragNavLogger {
                override fun error(message: String, throwable: Throwable) {
                    Log.e("DEBUG", message, throwable)
                }
            }

            defaultTransactionOptions = FragNavTransactionOptions.newBuilder().customAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_to_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_to_right
            ).build()
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH

            navigationStrategy = UniqueTabHistoryStrategy(object : FragNavSwitchController {
                override fun switchTab(index: Int, transactionOptions: FragNavTransactionOptions?) {
                    bottomBar.selectTabAtPosition(index)
                }
            })
        }
        fragNavController.initialize(INDEX_HOME, savedInstanceState)
        val initial = savedInstanceState == null
        if (initial) {
            bottomBar.selectTabAtPosition(INDEX_HOME)
        }
        bottomBar.setOnTabSelectListener({ tabId ->
            when (tabId) {
                R.id.navigation_home -> {
                    fragNavController.switchTab(INDEX_HOME)
                  /*  if (fragNavController.isRootFragment && fragNavController.currentFrag is HomeFragment) {
                        //  (fragNavController.currentFrag as HomeFragment).showDivisionDialog()
                        setTitleOnBar(AndroidUtils.getString(R.string.dubai_refreshments))
                        setBack(false)
                    }
*/
                }
                R.id.navigation_orders -> {
                    fragNavController.switchTab(INDEX_ORDERS)

                }
                R.id.navigation_points -> {
                    fragNavController.switchTab(INDEX_POINTS)
                 /*   setTitleOnBar(AndroidUtils.getString(R.string.order_list))
                    setBack(false)*/
                }
                R.id.navigation_ledger -> {
                    fragNavController.switchTab(INDEX_LEDGER)
                  /*  setTitleOnBar(AndroidUtils.getString(R.string.more))
                    setBack(false)*/
                }
                R.id.navigation_account -> {
                    fragNavController.switchTab(INDEX_ACCOUNT)
                    /*setTitleOnBar(AndroidUtils.getString(R.string.more))
                    setBack(false)*/
                }
            }
        }, initial)
        bottomBar.setOnTabReselectListener { fragNavController.clearStack() }
    }
    override fun onBackPressed() {
        if (fragNavController.popFragment().not()) {
            super.onBackPressed()
        }
    }
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        fragNavController.onSaveInstanceState(outState!!)

    }
}
