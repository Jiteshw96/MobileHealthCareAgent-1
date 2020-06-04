package com.atos.mobilehealthcareagent

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface
import com.atos.mobilehealthcareagent.presenter.LoginActivityPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity(),
    LoginActivityInterface.LoginActivityInterfaceViewInterface {

    lateinit var LoginActivityPresenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        LoginActivityPresenter = LoginActivityPresenter(this, this)

    }

    override fun redirectToDashBoard() {
        var intent = Intent(this, DashBoard::class.java)
        startActivity(intent)
        finish()
    }

    override fun init() {

    }


    fun onContinueClick(view: View) {

        if(isValidMobile(ph_number.text.toString())){
            var intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            Toast.makeText(this,"Please Enter a Valid Mobile Number",Toast.LENGTH_LONG).show()
        }


    }

    override fun initilizeContentView() {
        setContentView(R.layout.activity_login)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName = packageName
            val pm =
                getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.action = ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        }


    }

    private fun isValidMobile(phone: String): Boolean {
       /* if(phone.contains("." ) || phone.length < 10){
            return false
        }
        return Patterns.PHONE.matcher(phone).matches()*/
        val  patterntomatch ="^[+]?[0-9]{10,13}\$";
        val pattern=Pattern.compile(patterntomatch);
        val matcher=pattern.matcher(phone);

        return matcher.matches();
    }


}
