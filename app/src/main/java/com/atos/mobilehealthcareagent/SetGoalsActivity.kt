package com.atos.mobilehealthcareagent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.android.synthetic.main.activity_set_goals.*

class SetGoalsActivity : AppCompatActivity() {

    var  data = arrayOfNulls<String>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals)



        data[0] = (5 * 1000).toString()
        data[1] = (6 * 1000).toString()
        data[2] = (7 * 1000).toString()


//        for (i in 6..8) {
//            data[i - 1] = (i * 1000).toString()
//        }

        val numberPicker = findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 1
        numberPicker.maxValue = data.size
        numberPicker.displayedValues = data
        numberPicker.wrapSelectorWheel = false
        numberPicker.value = 2
        numberPicker.wheelItemCount = 6

        //Set the goal and pass back the values
        set_btn.setOnClickListener{
            val intent = Intent(this,RegistrationActivity::class.java)
            intent.putExtra("Goal",data[numberPicker.value-1].toString())

            intent.putExtra("name", getIntent().getStringExtra("name"))
            intent.putExtra("gender", getIntent().getIntExtra("gender",0))
            intent.putExtra("dob", getIntent().getStringExtra("dob"))
            intent.putExtra("height", getIntent().getStringExtra("height"))
            intent.putExtra("weight", getIntent().getStringExtra("weight"))
            startActivity(intent)
            finish()

        }
    }
}
