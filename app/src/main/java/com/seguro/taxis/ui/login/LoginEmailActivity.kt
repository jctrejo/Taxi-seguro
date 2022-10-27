package com.seguro.taxis.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.google.firebase.auth.FirebaseAuth
import com.seguro.taxis.R
import com.seguro.taxis.databinding.ActivityLoginEmailBinding
import com.seguro.taxis.ui.base.BaseActivity
import com.seguro.taxis.ui.register.RegisterActivity
import com.seguro.taxis.utils.UIUtils

class LoginEmailActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginEmailBinding
    private lateinit var mCallbackManager: CallbackManager
    private var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        binding.nextRegisterEmailButton.setOnClickListener { view ->
            if (binding.inputEmail.text.toString() == "") {
                UIUtils.alertErrorCustom(
                    this,
                    getString(R.string.error_email),
                    "#007A00"
                )
            } else if (binding.etSPassword.text.toString() == "") {
                UIUtils.alertErrorCustom(
                    this,
                    getString(R.string.error_password),
                    "#007A00"
                )
            } else if (binding.etSConfPassword.text.toString() == "") {
                UIUtils.alertErrorCustom(
                    this,
                    getString(R.string.error_confirm_password),
                    "#007A00"
                )
            } else if (binding.etSPassword.text.toString() != binding.etSConfPassword.text.toString()) {
                UIUtils.alertErrorCustom(
                    this,
                    getString(R.string.error_password_do_not_match),
                    "#007A00"
                )
            } else {
                signUpUser(binding.inputEmail.text.toString(), binding.etSPassword.text.toString())
            }
        }
    }

    override fun init() { }

    private fun signUpUser(email: String, password: String) {
        showLoader()
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, getString(R.string.error_successfully_singed_up), Toast.LENGTH_SHORT).show()
                finish()
                successLogin()
            } else {
                UIUtils.alertErrorCustom(
                    this,
                    getString(R.string.error_singed_up_failed),
                    "#007A00"
                )
                hideLoader()
            }
        }
    }

    private fun successLogin() {
        hideLoader()
        goToRegister()
    }

    private fun goToRegister() {
        val intent = Intent(FacebookSdk.getApplicationContext(), RegisterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
