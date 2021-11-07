package com.example.formit.ui.view

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.formit.R
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.btn_SignUp
import kotlinx.android.synthetic.main.activity_sign_in.emailContainer
import kotlinx.android.synthetic.main.activity_sign_in.passwordContainer
import kotlinx.android.synthetic.main.activity_sign_in.ti_Email
import kotlinx.android.synthetic.main.activity_sign_in.ti_Password

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val shader = LinearGradient(
            0f,
            0f,
            0f,
            tv_SignIn.textSize,
            Color.parseColor("#647DEE"),
            Color.parseColor("#7F53AC"),
            Shader.TileMode.CLAMP
        )
        tv_SignIn.paint.shader = shader
        btn_SignUp.paint.shader = shader

        btn_SignUp.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        btn_Login.setOnClickListener {
            Intent(this,HomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
            //clickLogin()
        }
    }

    private fun clickLogin() {
        val emailVerif = EmailValidate()
        val passVerif = PassValidate()
        if (emailVerif && passVerif) {

        }
    }

    private fun EmailValidate(): Boolean {
        val emailText = ti_Email.text.toString()
        if(emailText.isEmpty()){
            emailContainer.helperText = "Required"
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            emailContainer.helperText = "Email address invalide"
            return false
        } else {
            emailContainer.helperText = ""
        }
        return true
    }

    private fun PassValidate(): Boolean {
        val passwordText = ti_Password.text.toString()
        if(passwordText.isEmpty()){
            passwordContainer.helperText = "Required"
            return false
        }
        else if (passwordText.length < 6) {
            passwordContainer.helperText = "Minimum 8 Character Password"
            return false
        }else if (passwordText.length > 16) {
            passwordContainer.helperText = "Maximum 16 Character Password"
            return false
        } else if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            passwordContainer.helperText = "Must Contain 1 Upper-case Character"
            return false
        } else if (!passwordText.matches(".*[a-z].*".toRegex())) {
            passwordContainer.helperText = "Must Contain 1 Lower-case Character"
            return false
        } else if (!passwordText.matches(".*[0-9].*".toRegex())) {
            passwordContainer.helperText = "Must Contain 1 Number"
            return false
        } else {
            passwordContainer.helperText = ""
        }
        return true
    }
}