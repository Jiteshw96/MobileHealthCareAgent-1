package com.atos.mobilehealthcareagent.businesslogic

import android.text.InputFilter
import android.text.Spanned


class MinMaxFilter: InputFilter{

    private var mIntMin = 0
    private  var mIntMax:Int = 0

    constructor(minValue: Int, maxValue: Int) {
        mIntMin = minValue
        this.mIntMax = maxValue
    }

    fun MinMaxFilter(minValue: String, maxValue: String) {
        mIntMin = minValue.toInt()
        this.mIntMax = maxValue.toInt()
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(mIntMin, mIntMax, input)) return null
        } catch (nfe: NumberFormatException) {
        }
        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c >= a && c <= b else c >= b && c <= a
    }

}