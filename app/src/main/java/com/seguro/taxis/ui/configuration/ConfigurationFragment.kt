package com.seguro.taxis.ui.configuration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.seguro.taxis.R
import com.seguro.taxis.manager.MyUserManager
import com.seguro.taxis.manager.SharedPreferencesManager
import com.seguro.taxis.ui.base.BaseFragment
import com.seguro.taxis.ui.configuration.profile.ProfileMainActivity
import com.seguro.taxis.ui.login.LoginOptionsActivity
import com.seguro.taxis.ui.main.MainActivity

class ConfigurationFragment : BaseFragment() {

    private var closeSession: Button? = null
    private var profil: Button? = null
    private var version: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_configuration, container, false)
        init()

        closeSession = view.findViewById(R.id.close_session) as Button
        profil = view.findViewById(R.id.profilButton) as Button

        closeSession?.setOnClickListener {
            val firebaseAuth = FirebaseAuth.getInstance()

            FirebaseAuth.getInstance().signOut()
            LoginManager.getInstance().logOut()
            MyUserManager.instance.signedIn(false)
            ActivityCompat.finishAffinity(activity as MainActivity)
            val intent =
                Intent(FacebookSdk.getApplicationContext(), LoginOptionsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        profil?.setOnClickListener {
            if (MyUserManager.instance.isSignedIn()!!) {
                val intent = Intent(activity, ProfileMainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } else {
                val intent = Intent(activity, LoginOptionsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }

        return view
    }

    override fun init() {
        statuWifi()
    }

    override fun setUp(view: View?) {
        val preferences = SharedPreferencesManager(requireContext())
        val versionNew = preferences.getVersion()
        version?.text = "Version: $versionNew"
    }

    override fun isWifi(wifi: Boolean) {}

    companion object {
        private val IDENTIFIER = "ConfigurationFragment"
        fun newInstance(): ConfigurationFragment {
            return ConfigurationFragment()
        }
    }
}
