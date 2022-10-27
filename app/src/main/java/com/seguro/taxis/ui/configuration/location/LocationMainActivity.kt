package com.seguro.taxis.ui.configuration.location

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.seguro.taxis.R
import com.seguro.taxis.firebase.references.model.UserFacebookModel
import com.seguro.taxis.manager.Constants
import com.seguro.taxis.manager.Constants.Companion.EMPTY
import com.seguro.taxis.manager.Constants.Companion.FALSE_STR
import com.seguro.taxis.manager.Constants.Companion.SELECT_STATE
import com.seguro.taxis.manager.Constants.Companion.TRUE_STR
import com.seguro.taxis.manager.MyUserManager
import com.seguro.taxis.manager.SharedPreferencesManager
import com.seguro.taxis.ui.base.BaseActivity
import com.seguro.taxis.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_location_main.*
import java.util.*

class LocationMainActivity : BaseActivity() {

    private lateinit var valuesListener: ValueEventListener
    private lateinit var rootReference: DatabaseReference
    private var locationList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_main)
        init()
    }

    override fun init() {
        statuWifi()
        setupSplash()
        getInfoUserFacebook()
        locationList.add(SELECT_STATE)
    }

    private fun setupSplash() {
        val task = object : TimerTask() {
            override fun run() {
                val preferences = SharedPreferencesManager(this@LocationMainActivity)
                preferences.setLocationGPS("Ciudad Hidalgo")

                val intent = Intent(this@LocationMainActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
                MyUserManager.instance.signedIn(true)

            }
        }

        val timer = Timer()
        timer.schedule(task, Constants.SPLASH_SCREEN_DELAY)
    }

    private fun getInfoUserFacebook() {
        val preferences = SharedPreferencesManager(this)
        val isNewFacebook = preferences.getIsNewFacebook()
        val firebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth.currentUser?.uid
        if (isNewFacebook == "false") {
            if (userId != null) {
                rootReference = Constants.Users.child(userId)

                valuesListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user = dataSnapshot.getValue(UserFacebookModel::class.java)

                        if (user != null) {
                            if (isNewFacebook == FALSE_STR) {
                                val facebookId = user.facebookId
                                preferences.setKeyUser(facebookId ?: EMPTY)
                                preferences.setIsNewFacebook(TRUE_STR)
                                rootReference.removeEventListener(this)
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                }

                rootReference.addValueEventListener(valuesListener)
            }
        }
    }

    override fun showLoader() {
       if (animation_loader != null) {
           animation_loader.visibility = View.VISIBLE
           animation_loader.playAnimation()
        }
    }

    override fun hideLoader() {

        if (animation_loader != null) {
            animation_loader.cancelAnimation()
            animation_loader.visibility = View.GONE
        }
    }
}
