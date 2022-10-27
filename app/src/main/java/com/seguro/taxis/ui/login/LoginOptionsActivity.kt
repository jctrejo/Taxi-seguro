package com.seguro.taxis.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.seguro.taxis.R
import com.seguro.taxis.databinding.ActivityLoginOptionsBinding
import com.seguro.taxis.manager.MyUserManager
import com.seguro.taxis.ui.base.BaseActivity
import com.seguro.taxis.ui.configuration.location.LocationMainActivity
import com.seguro.taxis.ui.view.activity.OnBoardingActivity
import com.seguro.taxis.utils.AppPrefs
import com.seguro.taxis.utils.UIUtils.Companion.alertErrorCustom
import com.seguro.taxis.utils.isValidEmail
import com.seguro.taxis.utils.typeLoginError

class LoginOptionsActivity : BaseActivity() {

    private lateinit var mCallbackManager: CallbackManager
    private lateinit var binding: ActivityLoginOptionsBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOptionsBinding.inflate(layoutInflater)
        MyUserManager.instance.context = this@LoginOptionsActivity
        setContentView(binding.root)

        if (AppPrefs(this).isFirstTimeLaunch()) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }
        init()
    }

    private fun loaderLottieIcon() {
        with(binding) {
            animationLoaderFacebook.visibility = View.VISIBLE
            animationLoaderFacebook.playAnimation()
        }
    }

    override fun init() {
        loaderLottieIcon()

        with(binding) {
            btnSignIn.text = getString(R.string.signed)
            btnNextLogin.text = getString(R.string.next)

            btnNextLogin.setOnClickListener {
                loginUser(
                    email = emailEditText.text.toString(),
                    password = passwordEditText.text.toString()
                )
            }

            btnSignIn.setOnClickListener {
                val intent =
                    Intent(binding.root.context, LoginEmailActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            laterButton.setOnClickListener {
                val intent =
                    Intent(FacebookSdk.getApplicationContext(), LocationMainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        if (!email.isValidEmail()) {
            hideLoader()
            alertErrorCustom(this, getString(R.string.error_email), "#03C317")
        } else if (TextUtils.isEmpty(password)) {
            hideLoader()
            alertErrorCustom(this, getString(R.string.error_password), "#03C317")
        } else {
            showLoader()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        goToHome()
                        hideLoader()
                    } else {
                        val errorCode = (task.exception as FirebaseAuthException?)?.errorCode
                        errorCode?.typeLoginError(this)
                        hideLoader()
                    }
                }
        }
    }

    private fun goToHome() {
        val intent =
            Intent(FacebookSdk.getApplicationContext(), LocationMainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
