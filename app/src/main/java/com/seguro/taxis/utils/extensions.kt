package com.seguro.taxis.utils

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.seguro.taxis.R
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun postDelayed(delayMillis: Long, task: () -> Unit) {
    Handler().postDelayed(task, delayMillis)
}

fun AppCompatActivity.toolbar(toolbar: Toolbar, titleStr: String = "") {
    setSupportActionBar(toolbar)
    supportActionBar?.title = titleStr
    toolbar.apply {
        setNavigationIcon(R.drawable.ic_arrow_back_grey_24dp)
        setNavigationOnClickListener { finish() }
    }
}

fun String.isValidEmail() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.typeLoginError(context: Context) =
    when (this) {
        "ERROR_CUSTOM_TOKEN_MISMATCH" ->
            UIUtils.alertErrorCustom(
                context as Activity,
                "El token personalizado corresponde a una audiencia diferente.",
                "#03C317"
            )

        "ERROR_INVALID_CREDENTIAL" ->
            UIUtils.alertErrorCustom(
                context as Activity,
                "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.",
                "#03C317"
            )

        "ERROR_USER_MISMATCH" ->
            UIUtils.alertErrorCustom(
                context as Activity,
                "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente.",
                "#03C317"
            )

        "ERROR_REQUIRES_RECENT_LOGIN" ->
            UIUtils.alertErrorCustom(
                context as Activity,
                "Esta operación es confidencial y requiere autenticación reciente. Vuelva a iniciar sesión antes de volver a intentar esta solicitud.",
                "#03C317"
            )

        "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
            UIUtils.alertErrorCustom(
                context as Activity,
                "Ya existe una cuenta con la misma dirección de correo electrónico pero con diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado con esta dirección de correo electrónico.",
                "#03C317"
            )
        }
        "ERROR_USER_NOT_FOUND" -> {
            UIUtils.alertErrorCustom(
                context as Activity,
                "No existe registro de usuario correspondiente a este correo. Es posible que el usuario haya sido eliminado.",
                "#03C317"
            )
        }
        else -> {
            UIUtils.alertErrorCustom(
                context as Activity,
                context.getString(R.string.error_try_again_later),
                "#03C317"
            )
        }
    }

fun timeCourrent(): String {
    val formatter = SimpleDateFormat("hh:mm a")
    val time = formatter.format(Date())
    return time
}

