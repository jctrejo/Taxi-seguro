package com.seguro.taxis.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.seguro.taxis.R
import com.seguro.taxis.utils.AlertDialogButtonsListener
import com.seguro.taxis.utils.NetworkUtils
import com.google.firebase.analytics.FirebaseAnalytics
import com.tapadoo.alerter.Alerter

abstract class BaseFragment: Fragment(), BaseView {

    val loadingMessage = "Cargando..."
    private var activity: BaseActivity? = null
    private var progressDialog: ProgressDialog? = null
    val bundle = Bundle()
    var mFirebaseAnalytics: FirebaseAnalytics? = null

    companion object {
        const val REQUEST_ALL_CODE = 1
        const val REQUEST_GALLERY_CODE = 2
    }

    protected abstract fun init()
    //abstract fun getFragmentName() : String

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setHasOptionsMenu(false)
//    }

    abstract fun setUp(view: View?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(activity!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.activity = context
        }
    }

    override fun showLoader() {
        progressDialog = ProgressDialog(context)
        progressDialog?.setMessage(loadingMessage)
        progressDialog?.show()
    }

    override fun hideLoader() {
        if (progressDialog != null && progressDialog!!.isShowing()) {
            progressDialog?.cancel()
        }
    }

    override fun showAlertDialog(title: String, message: String) {
        activity!!.showAlertDialog(title, message)
    }

    override fun showAlertDialog(titleId: Int, messageId: Int) {
        activity!!.showAlertDialog(titleId, messageId)
    }

    override fun showAlertDialogAndFinish(title: String, message: String) {
        activity!!.showAlertDialogAndFinish(title, message)
    }

    override fun showAlertDialogAndFinish(titleId: Int, messageId: Int) {
        activity?.showAlertDialogAndFinish(titleId, messageId)
    }

    override fun showDialogAndPositiveNegativev2(title: String, message: String, positiveMsg: String, negativeMsg: String,
                                                 listener: AlertDialogButtonsListener
    ) {
        activity?.showDialogAndPositiveNegativev2(title, message, positiveMsg, negativeMsg, listener)
    }

    override fun showDialogAndPositiveNegativev2(title: Int, message: Int, positiveMsg: Int, negativeMsg: Int,
                                                 listener: AlertDialogButtonsListener) {
        activity?.showDialogAndPositiveNegativev2(title, message, positiveMsg, negativeMsg, listener)
    }

    override fun showMessage(message: String) {
        activity?.showMessage(message)
    }

    override fun showMessage(messageId: Int) {
        activity?.showMessage(messageId)
    }

    override fun isNetworkConnected(): Boolean {
        return NetworkUtils.isConnected(activity!!)
    }

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    fun getBaseActivity(): BaseActivity {
        return activity!!
    }

    fun hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view!!.windowToken, 0)
    }

    fun statuWifi() {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
                Alerter.create(activity)
                        .setTitle(R.string.name_app)
                        .setText(R.string.alert_no_wifi)
                        .setBackgroundColorInt(Color.parseColor("#E26B0101"))
                        .setIcon(R.drawable.ic_logo_empresa)
                        .setDuration(7000)
                        .show()
                showLoader()
                isWifi(true)
            }
        }
    }

    abstract fun isWifi(wifi: Boolean)
}
