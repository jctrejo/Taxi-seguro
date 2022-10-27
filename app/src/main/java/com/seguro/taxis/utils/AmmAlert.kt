package com.seguro.taxis.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.xolostudios.cemain.ui.custom.alert.IAmmAlert

class AmmAlert: IAmmAlert {
    /***
     * title
     */
    private var title: String? = null
    /**
     * description
     */
    private var descrioption: String? = null
    /**
     * Title cancel
     */
    private var titleCancel: String? = null
    /**
     * Title acept
     */
    private var titleAcept: String? = null
    /**
     * on Acept
     */
    private var onAcept: (() -> Unit?)? = null
    /**
     * on cancel
     */
    private var onCancel: (() -> Unit?)? = null
    /**
     * alert
     */
    private var alert: AlertDialog.Builder? = null

    override fun title(title: String): IAmmAlert {
        this.title = title
        return this
    }

    override fun message(message: String): IAmmAlert {
        this.descrioption = message
        return this
    }

    override fun setActionAccept(title: String, action: () -> Unit): IAmmAlert {
        this.titleAcept = title
        this.onAcept = action
        return this
    }

    override fun setCancelAcction(title: String, action: () -> Unit): IAmmAlert {
        this.titleCancel = title
        this.onCancel = action
        return this
    }

    override fun show(context: Context) {
        this.alert = AlertDialog.Builder(context)
        alert?.setTitle(title)
        alert?.setMessage(descrioption)
        alert?.setCancelable(false)
        if (onCancel != null && onAcept != null) {
            multipleAction()
            return
        }
        if (onCancel == null && onAcept != null) {
            oneAction()
            return
        }
        alert?.setPositiveButton(android.R.string.ok) { dialogInterface, i -> dialogInterface.dismiss() }
        alert?.create()?.show()
    }

    private fun oneAction() {
        alert?.setPositiveButton(this.titleAcept) { _, _ -> this.onAcept?.invoke()
        }
                ?.show()
    }

    private fun multipleAction() {
        alert
                ?.setPositiveButton(this.titleAcept) { _, _ -> this.onAcept?.invoke() }
                ?.setNegativeButton(this.titleCancel) { _, _ -> this.onCancel?.invoke() }
                ?.show()
    }
}
