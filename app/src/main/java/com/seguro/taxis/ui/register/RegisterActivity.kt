package com.seguro.taxis.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.seguro.taxis.R
import com.seguro.taxis.databinding.ActivityRegisterBinding
import com.seguro.taxis.firebase.references.model.RegisterModel
import com.seguro.taxis.manager.Constants
import com.seguro.taxis.manager.SharedPreferencesManager
import com.seguro.taxis.ui.base.BaseActivity
import com.seguro.taxis.ui.configuration.location.LocationMainActivity
import com.seguro.taxis.ui.register.picker.DatePickerFragment
import com.seguro.taxis.utils.UIUtils
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    var genderButton = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonActive()

        viewClickeable.setOnClickListener {
            val datePickerFragment = DatePickerFragment.newInstance(input_birth)
            datePickerFragment.show(supportFragmentManager, "datePickerDialog")
        }

        femaleGenderButton.setOnClickListener {
            if (genderButton)
                setButtonActive()
        }

        maleGenderButton.setOnClickListener {
            if (!genderButton)
                setButtonActive()
        }

        nextRegisterButton.setOnClickListener {
            validateDate()
        }
    }

    override fun init() { }

    private fun validateDate() {
        val gender = if (genderButton) "Hombre" else "Mujer"

        if (binding.inputBirth.text.toString() == "") {
            UIUtils.alertErrorCustom(
                this,
                getString(R.string.attendance_error_try_again),
                "#007A00"
            )
        } else if (gender.isEmpty()) {
            UIUtils.alertErrorCustom(
                this,
                getString(R.string.attendance_error_try_again),
                "#007A00"
            )
        } else if (binding.etName.text.toString() == "") {
            UIUtils.alertErrorCustom(
                this,
                getString(R.string.attendance_error_try_again),
                "#007A00"
            )
        } else {
            setSaveData(gender, binding.inputBirth.text.toString(), binding.etName.text.toString())
        }
    }

    private fun setSaveData(gender: String, birthDate: String, name: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth.currentUser?.uid
        val register = RegisterModel(gender, birthDate, userId ?: "", name, "", "")
        val preferences = SharedPreferencesManager(this)
        showLoader()
        Constants.Users.child(userId ?: "").setValue(register)
                .addOnSuccessListener {
                    binding.etName.setText("")
                    preferences.setUserName(name)
                    val intent = Intent(this, LocationMainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    hideLoader()
                }
                .addOnFailureListener {
                    UIUtils.alertErrorCustom(
                        this,
                        getString(R.string.error_singed_up_failed),
                        "#007A00"
                    )
                    hideLoader()
                }
    }

    private fun setButtonActive() {
        if (genderButton) {
            femaleGenderButton.setBackgroundResource(R.drawable.gender_button_background)
            femaleImage.setImageResource(R.drawable.ic_female_on)
            femaleText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            maleGenderButton.setBackgroundResource(R.drawable.gender_button_border)
            maleImage.setImageResource(R.drawable.ic_male_off)
            maleText.setTextColor(ContextCompat.getColor(this, R.color.white))

            genderButton = false
        } else {
            femaleGenderButton.setBackgroundResource(R.drawable.gender_button_border)
            femaleImage.setImageResource(R.drawable.ic_female_off)
            femaleText.setTextColor(ContextCompat.getColor(this, R.color.white))

            maleGenderButton.setBackgroundResource(R.drawable.gender_button_background)
            maleImage.setImageResource(R.drawable.ic_male_on)
            maleText.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))

            genderButton = true
        }
    }
}
