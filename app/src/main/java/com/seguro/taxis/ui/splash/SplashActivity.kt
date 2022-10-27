package com.seguro.taxis.ui.splash

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.seguro.taxis.databinding.ActivitySplashBinding
import com.seguro.taxis.firebase.references.model.UserFacebookModel
import com.seguro.taxis.manager.Constants
import com.seguro.taxis.manager.Constants.Companion.SPLASH_SCREEN_DELAY
import com.seguro.taxis.manager.MyUserManager
import com.seguro.taxis.manager.SharedPreferencesManager
import com.seguro.taxis.ui.base.BaseActivity
import com.seguro.taxis.ui.login.LoginOptionsActivity
import com.seguro.taxis.ui.main.MainActivity
import java.util.*

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        init()
        MyUserManager.instance.context = this@SplashActivity
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(binding.root)
        showLoader()
        getInfoUserFacebook()
        setupSplash()
    }

    private fun setupSplash() {
        val task = object : TimerTask() {
            override fun run() {
                val firebaseAuth = FirebaseAuth.getInstance()
                val userId = firebaseAuth.currentUser?.uid

                if (userId != null) {
                    if (MyUserManager.instance.isSignedIn()!!) {
                        val mainIntent = Intent().setClass(this@SplashActivity, MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    } else {
                        val mainIntent = Intent().setClass(this@SplashActivity, LoginOptionsActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    }
                } else {
                    FirebaseAuth.getInstance().signOut()
                    LoginManager.getInstance().logOut()
                    MyUserManager.instance.signedIn(false)

                    val mainIntent = Intent().setClass(this@SplashActivity, LoginOptionsActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
            }
        }

        val timer = Timer()
        timer.schedule(task, SPLASH_SCREEN_DELAY)
    }

    override fun init() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun getInfoUserFacebook() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth.currentUser?.uid
        val preferences = SharedPreferencesManager(this)

        if (userId != null) {
            val rootRef = Constants.Users.child(userId)
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(UserFacebookModel::class.java)

                    if (user != null) {
                        val facebookId = user.facebookId
                        preferences.setKeyUser(facebookId ?: "")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) { }
            }

            rootRef.addValueEventListener(postListener)
        }
    }

    override fun showLoader() {
        with(binding) {
            animationLoaderSplash.visibility = View.VISIBLE
            animationLoaderSplash.playAnimation()
        }
    }

    override fun hideLoader() {
        with(binding) {
            animationLoaderSplash.cancelAnimation()
            animationLoaderSplash.visibility = View.GONE
        }
    }
}