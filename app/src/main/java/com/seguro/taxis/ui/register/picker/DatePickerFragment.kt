package com.seguro.taxis.ui.register.picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import java.util.Locale

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {

        const val TWO_FORMAT = "%02d"
        const val FOUR_FORMAT = "%04d"
        const val SLASH = "/"

        fun newInstance(editText: EditText): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.setEditText(editText)
            return fragment
        }
    }

    private var editText: EditText? = null

    fun setEditText(editText: EditText) {
        this.editText = editText
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        editText?.setText("")

        val selectedTime = (String.format(Locale.getDefault(), TWO_FORMAT, dayOfMonth)
                + SLASH +
                String.format(Locale.getDefault(), TWO_FORMAT, month + 1)
                + SLASH +
                String.format(Locale.getDefault(), FOUR_FORMAT, year))
        editText?.setText(selectedTime)
    }
}
