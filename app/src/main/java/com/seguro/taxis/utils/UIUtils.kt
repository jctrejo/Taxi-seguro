package com.seguro.taxis.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.seguro.taxis.R
import com.tapadoo.alerter.Alerter

class UIUtils {

    companion object {
        fun showDialog(@NonNull title: String, @NonNull message: String, @NonNull context: Context) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.create().show()
        }

        fun showDialogAndPositiveFinish(@NonNull title: String, @NonNull message: String, @NonNull activity: AppCompatActivity) {
            val alertDialogBuilder = AlertDialog.Builder(activity)
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
                activity.finish()
            }
            alertDialogBuilder.create().show()
        }

        fun showDialogAndPositiveNegativev2(@NonNull title: String, @NonNull message: String,
                                            @NonNull positiveMsg: String, @NonNull negativeMsg: String, @NonNull activity: AppCompatActivity,
                                            @NonNull alertDialogButtonsListener: AlertDialogButtonsListener) {
            val alertDialogBuilder = AlertDialog.Builder(activity)
            alertDialogBuilder.setTitle(title)
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setPositiveButton(positiveMsg) { dialog, _ ->
                dialog.dismiss()
                alertDialogButtonsListener.onPositiveClickListener()
            }
            alertDialogBuilder.setNeutralButton(negativeMsg) { dialog, _ ->
                dialog.dismiss()
                alertDialogButtonsListener.onNegativeClickListener()
            }
            alertDialogBuilder.create().show()
        }

        fun buildNotificationServiceAlertDialog(activity: AppCompatActivity): AlertDialog {
            val alertDialogBuilder = AlertDialog.Builder(activity)
            alertDialogBuilder.setTitle("notification_listener_service AGREGAR STILO")
            alertDialogBuilder.setMessage("notification_listener_service_explanation")
            alertDialogBuilder.setPositiveButton(android.R.string.ok) { _, _ ->
                activity.startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }
            alertDialogBuilder.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
            return alertDialogBuilder.create()
        }

        fun alertTopCustom(activity: Activity) {
            Alerter.create(activity)
                    .setTitle(R.string.name_app)
                    .setText(R.string.update_request_message)
                    .addButton("Actualizar", R.style.ButtonAlertCustum, View.OnClickListener {
                        val appPackageName = activity.packageName // getPackageName() from Context or Activity object
                        try {
                            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                        } catch (anfe: android.content.ActivityNotFoundException) {
                            activity.startActivity(Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                        }
                    })
                    .setBackgroundColorInt(Color.parseColor("#D2FC6500"))
                    .setDuration(30000)
                    .enableSwipeToDismiss()
                    .setIcon(R.drawable.ic_logo_empresa)
                    .show()
        }

        fun alertErrorCustom(activity: Context, subTitleAlert: String, color: String) {
            Alerter.create(activity as Activity)
                    .setTitle(R.string.name_app)
                    .setText(subTitleAlert)
                    .setBackgroundColorInt(Color.parseColor(color))
                    .setIcon(R.drawable.ic_logo_empresa)
                    .setDuration(5000)
                    .show()
        }
    }
}
