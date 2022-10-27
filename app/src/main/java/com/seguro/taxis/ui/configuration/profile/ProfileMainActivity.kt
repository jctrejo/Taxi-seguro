package com.seguro.taxis.ui.configuration.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.seguro.taxis.R
import com.seguro.taxis.firebase.references.model.UserFacebookModel
import com.seguro.taxis.manager.Constants
import com.seguro.taxis.manager.MyUserManager
import com.seguro.taxis.ui.base.BaseActivity
import com.seguro.taxis.ui.login.LoginOptionsActivity
import kotlinx.android.synthetic.main.activity_profile_main.*

class ProfileMainActivity : BaseActivity() {

    var userPoint: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_main)
        init()

/*
        profileFavoritesButton?.setOnClickListener {
            val intent = Intent(this@ProfileMainActivity, AwardsActivity::class.java)
            intent.putExtra("userPoint", userPoint)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
*/
    }

    override fun init() {
        statuWifi()
        getInfoUser()
    }

    private fun getInfoUser() {
        showLoader()
        val firebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            val rootRef = Constants.Users.child(userId)

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(UserFacebookModel::class.java)

                    if (user != null) {
                        // val currentUser = MyUserManager.instance.getCurrentUser()

                        //val name = currentUser.name
                        //val pictureUrl = currentUser.pictureUrl
                        val birthday = user.birthday
                        val gende = user.gender
                        val point = user.pointCounter

                        userPoint = point

                        /*if (name != null) {
                            proflleNameTextView.text = name
                        }*/

                        if (birthday != null) {
                            profileBirthdateTextView.text = birthday
                        }

                        if (gende != null) {
                            gendeTextView.text = gende
                        }

/*
                        if (pictureUrl != null) {
                            Glide.with(getApplicationContext())
                                    .load(pictureUrl)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(profileImageButton)
                        }
*/

                        hideLoader()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) { hideLoader() }
            }

            rootRef.addValueEventListener(postListener)
        } else {
            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut()
            MyUserManager.instance.signedIn(false)

            val mainIntent = Intent().setClass(this@ProfileMainActivity, LoginOptionsActivity::class.java)
            startActivity(mainIntent)
            finish()
            hideLoader()
        }
    }

    override fun showLoader() {
        if (animation_loader_profile != null) {
            animation_loader_profile.visibility = View.VISIBLE
            animation_loader_profile.playAnimation()
        }
    }

    override fun hideLoader() {
        if (animation_loader_profile != null) {
            animation_loader_profile.cancelAnimation()
            animation_loader_profile.visibility = View.GONE
        }
    }
}
