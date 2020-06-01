package com.atos.mobilehealthcareagent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.android.synthetic.main.activity_set_goals.*

class SetGoalsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals)

        val getIntent = getIntent()

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

        //Set the goal and pass back the values
        set_btn.setOnClickListener{
            val intent = Intent(this,RegistrationActivity::class.java)
            intent.putExtra("Goal",numberPicker.value.times(1000).toString())
            intent.putExtra("name", getIntent.getStringExtra("name"))
            intent.putExtra("gender", getIntent.getIntExtra("gender",0))
            intent.putExtra("dob", getIntent.getStringExtra("dob"))
            intent.putExtra("height", getIntent.getStringExtra("height"))
            intent.putExtra("weight", getIntent.getStringExtra("weight"))
            startActivity(intent)

        }
    }
}
