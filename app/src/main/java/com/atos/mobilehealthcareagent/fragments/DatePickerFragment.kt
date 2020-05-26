package com.atos.mobilehealthcareagent.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.atos.mobilehealthcareagent.R
import java.util.*


class DatePickerFragment:DialogFragment(){


    override fun onCreateDialog(savedInstanceState: Bundle?):Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            activity!!,
            R.style.CalendarPicker,
            activity as OnDateSetListener?,
            year,
            month,
            day
        )
    }
}
