package com.atos.mobilehealthcareagent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shawnlin.numberpicker.NumberPicker

class SetGoalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals)

        val data = arrayOfNulls<String>(30)

        for (i in 1..30) {
            data[i - 1] = (i * 1000).toString()
        }

        val numberPicker = findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 1
        numberPicker.maxValue = data.size
        numberPicker.displayedValues = data
        numberPicker.wrapSelectorWheel = false
        numberPicker.value = 7
        numberPicker.wheelItemCount = 8
    }
}
