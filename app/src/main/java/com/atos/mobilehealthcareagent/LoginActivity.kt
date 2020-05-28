package com.atos.mobilehealthcareagent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.atos.mobilehealthcareagent.contract.LoginActivityInterface
import com.atos.mobilehealthcareagent.database.AppDatabase
import com.atos.mobilehealthcareagent.database.Goal
import com.atos.mobilehealthcareagent.presenter.LoginActivityPresenter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Field


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
    }

    override fun init() {

    }


    fun onContinueClick(view: View) {
        var intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)

    }

    override fun initilizeContentView() {
        setContentView(R.layout.activity_login)
    }


}
