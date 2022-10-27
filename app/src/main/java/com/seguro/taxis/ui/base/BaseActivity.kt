package com.seguro.taxis.ui.base

import android.Manifest
import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.seguro.taxis.utils.AlertDialogButtonsListener
import com.seguro.taxis.utils.NetworkUtils
import com.seguro.taxis.utils.UIUtils
import com.google.firebase.analytics.FirebaseAnalytics
import com.seguro.taxis.R
import com.tapadoo.alerter.Alerter

abstract class BaseActivity : AppCompatActivity(), BaseView {
    companion object {
        const val REQUEST_ALL_CODE = 1
        const val REQUEST_GALLERY_CODE = 2
    }

    val loadingMessage = "Cargando..."
    private var progressDialog: ProgressDialog? = null
    private var galleryCallback: (() -> Unit)? = null
    val bundle = Bundle()
    var mFirebaseAnalytics: FirebaseAnalytics? = null

    protected abstract fun init()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        requestAllPermission()

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        if (Build.VERSION.SDK_INT < 16) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            val decorView = window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_VISIBLE
            decorView.systemUiVisibility = uiOptions
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestAllPermission() {
        val permissions = arrayOf(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION

                /*,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA*/
        )

        val listPermissionNeeded = ArrayList<String>()

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                listPermissionNeeded.add(permission)
            }
        }
        if (listPermissionNeeded.size > 0) {
            requestPermissions(listPermissionNeeded.toTypedArray(), REQUEST_ALL_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val allPermissionsAllowed: Boolean
        var countPermissions = 0

        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                countPermissions++
            }
        }

        when (requestCode) {
            REQUEST_ALL_CODE -> {
                if (grantResults.size != 0) {
                    allPermissionsAllowed = countPermissions == grantResults.size
                    if (!allPermissionsAllowed) {
                        UIUtils.showDialogAndPositiveFinish(
                                getString(R.string.description_info),
                                getString(R.string.request_permission), this
                        )
                    }
                }
            }
            REQUEST_GALLERY_CODE -> {
                if (galleryCallback != null) {
                    if (countPermissions > 0) {
                        galleryCallback!!()
                    }
                    galleryCallback = null
                }
            }
        }
    }

    override fun showLoader() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setMessage(loadingMessage)
        progressDialog?.show()
    }

    override fun hideLoader() {
        if (progressDialog != null && progressDialog!!.isShowing()) {
            progressDialog?.cancel()
        }
    }

    override fun showAlertDialog(title: String, message: String) {
        if (!message.isEmpty()) {
            UIUtils.showDialog(title, message, this)
        }
    }

    override fun showAlertDialog(titleId: Int, messageId: Int) {
        showAlertDialog(getString(titleId), getString(messageId))
    }

    override fun showAlertDialogAndFinish(title: String, message: String) {
        if (!message.isEmpty()) {
            UIUtils.showDialogAndPositiveFinish(title, message, this)
        }
    }

    override fun showAlertDialogAndFinish(titleId: Int, messageId: Int) {
        showAlertDialogAndFinish(getString(titleId), getString(messageId))
    }

    override fun showDialogAndPositiveNegativev2(title: String, message: String, positiveMsg: String, negativeMsg: String,
                                                 listener: AlertDialogButtonsListener) {
        if (!message.isEmpty()) {
            UIUtils.showDialogAndPositiveNegativev2(title, message, positiveMsg, negativeMsg, this, listener)
        }
    }

    override fun showDialogAndPositiveNegativev2(title: Int, message: Int, positiveMsg: Int, negativeMsg: Int,
                                                 listener: AlertDialogButtonsListener
    ) {
        showDialogAndPositiveNegativev2(getString(title), getString(message), getString(positiveMsg), getString(negativeMsg), listener)
    }

    override fun showMessage(message: String) {
        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(messageId: Int) {
        showMessage(getString(messageId))
    }

    override fun isNetworkConnected(): Boolean {
        return NetworkUtils.isConnected(this)
    }

    fun hideKeyboard() {
        val view = this.getCurrentFocus()
        if (view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun statuWifi() {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        val isWiFi: Boolean = activeNetwork?.type == ConnectivityManager.TYPE_WIFI
        val isMovil: Boolean = activeNetwork?.type == ConnectivityManager.TYPE_MOBILE

        print(isConnected)
        if (isWiFi != false) {
            //Todo: Si hay internet
        } else {
            if (isMovil != false) {
                hideLoader()
                //Todo: Si hay internet
            } else {
                Alerter.create(this)
                        .setTitle(R.string.name_app)
                        .setText(R.string.alert_no_wifi)
                        .setBackgroundColorInt(Color.parseColor("#E26B0101"))
                        .setIcon(R.drawable.ic_logo_empresa)
                        .setDuration(7000)
                        .show()
                showLoader()
            }
        }
    }
}

