package com.example.formit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formit.ui.view.*
import kotlinx.android.synthetic.main.activity_formit.*

class FormItActivity : AppCompatActivity() {

    //TODO 1 "Declare a var of SharedPreferences"
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formit)

        //TODO 2 "Initialize the var of SharedPreferences"
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        //TODO 3 "Test in the SharedPreferences if there's data"
        if (mSharedPref.getBoolean(IS_REMEMBRED, false)){
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        Welcome_SignIn.setOnClickListener {
            Intent(this,SignInUpActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}