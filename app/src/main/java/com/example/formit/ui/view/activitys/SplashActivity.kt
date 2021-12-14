package com.example.formit.ui.view.activitys

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.formit.FormItActivity
import com.example.formit.R
import kotlinx.android.synthetic.main.activity_formit.*

class SplashActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        Handler(Looper.getMainLooper()).postDelayed({
            if (mSharedPref.getBoolean(IS_REMEMBRED, false)){
                Intent(this, HomeActivity::class.java).also {

                    startActivity(it)
                    finish()
                }
            }else{
                Intent(this, FormItActivity::class.java).also {

                    startActivity(it)
                    finish()
                }
            }
        }, 3000)
    }
}