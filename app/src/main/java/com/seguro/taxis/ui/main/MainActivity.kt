package com.seguro.taxis.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.seguro.taxis.ui.taxi.TaxiFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.seguro.taxis.R
import com.seguro.taxis.firebase.references.model.VersionModel
import com.seguro.taxis.manager.Constants
import com.seguro.taxis.manager.SharedPreferencesManager
import com.seguro.taxis.ui.base.BaseActivity
import com.seguro.taxis.ui.base.BaseFragment
import com.seguro.taxis.ui.base.BaseView
import com.seguro.taxis.ui.configuration.ConfigurationFragment
import com.seguro.taxis.utils.UIUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private var currentFragment = Fragment()
    var view: BaseView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        init()
    }

    override fun init() {
        getVersion()
        mainBottomTabBar.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        setupHome()
    }

    private fun loadFragment(newInstance: BaseFragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragments_container, newInstance)
                .commit()

        currentFragment = newInstance
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
        when (item.itemId) {
            R.id.home -> {
                setupHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.settings -> {
                setupSettings()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun setupSettings() {
        loadFragment(ConfigurationFragment.newInstance())
        eventSection(Constants.EVENT_SERVICES,Constants.EVENT_SERVICES)
        mainBottomTabBar.menu.findItem(R.id.settings).isChecked = true
    }

    private fun setupHome() {
        loadFragment(TaxiFragment.newInstance())
        eventSection(Constants.EVENT_HOME,Constants.EVENT_HOME)
        mainBottomTabBar.menu.findItem(R.id.home).isChecked = true
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun getVersion() {
        val rootRef = Constants.version

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val version = dataSnapshot.getValue(VersionModel::class.java)

                if (version != null) {
                    val dev = version.dev
                    val prod = version.prod

                    if (dev != null && prod != null) {
                        try {
                            val pInfo = applicationContext.packageManager.getPackageInfo(packageName, 0)
                            var version: String = pInfo.versionName
                            version = if (version.contains("-")) (version.split("-"))[0] else version
                            val preferences = SharedPreferencesManager(this@MainActivity)
                            preferences.setVersion(version)

                            if (version < prod ?: "") {
                                UIUtils.alertTopCustom(this@MainActivity)
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }

        rootRef.addValueEventListener(postListener)
    }

    private fun eventSection(select: String, Type: String) {
        bundle.putString(Type, select)
        mFirebaseAnalytics?.logEvent(Constants.EVENTE_SECTION, bundle)
    }
}
