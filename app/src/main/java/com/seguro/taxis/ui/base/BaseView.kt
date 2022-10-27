package com.seguro.taxis.ui.base

import androidx.annotation.StringRes
import com.seguro.taxis.utils.AlertDialogButtonsListener

interface BaseView {

    fun showLoader()

    fun hideLoader()

    fun showAlertDialog(@StringRes titleId: Int, @StringRes messageId: Int)

    fun showAlertDialog(title: String, message: String)

    fun showAlertDialogAndFinish(@StringRes titleId: Int, @StringRes messageId: Int)

    fun showAlertDialogAndFinish(title: String, message: String)

    fun showDialogAndPositiveNegativev2(@StringRes title: Int, @StringRes message: Int,
                                        @StringRes positiveMsg: Int, @StringRes negativeMsg: Int,
                                        listener: AlertDialogButtonsListener
    )

    fun showDialogAndPositiveNegativev2(title: String, message: String,
                                        positiveMsg: String, negativeMsg: String,
                                        listener: AlertDialogButtonsListener)

    fun showMessage(@StringRes messageId: Int)

    fun showMessage(message: String)

    fun isNetworkConnected(): Boolean
}
