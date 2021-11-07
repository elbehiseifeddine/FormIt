package com.example.formit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formit.ui.view.SignInActivity
import kotlinx.android.synthetic.main.activity_formit.*

class FormItActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formit)

        Welcome_SignIn.setOnClickListener {
            Intent(this,SignInActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}