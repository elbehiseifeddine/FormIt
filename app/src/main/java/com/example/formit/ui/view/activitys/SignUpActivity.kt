package com.example.formit.ui.view.activitys

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.formit.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.btn_SignUp
import kotlinx.android.synthetic.main.activity_sign_up.emailContainer
import kotlinx.android.synthetic.main.activity_sign_up.passwordContainer
import kotlinx.android.synthetic.main.activity_sign_up.ti_Email
import kotlinx.android.synthetic.main.activity_sign_up.ti_Password

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val shader = LinearGradient(
            0f,
            0f,
            0f,
            tv_SignUp.textSize,
            Color.parseColor("#647DEE"),
            Color.parseColor("#7F53AC"),
            Shader.TileMode.CLAMP
        )
        tv_SignUp.paint.shader = shader
        btn_Login.paint.shader = shader

        btn_Login.setOnClickListener {
            Intent(this, SignInActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        btn_SignUp.setOnClickListener {
            clickSignUp()
        }


    }

    private fun clickSignUp() {
        val emailVerif = EmailValidate()
        val passVerif = PassValidate()
        val confirmPassVerif = ConfirmPassValidate()
        if (emailVerif && passVerif && confirmPassVerif) {
            Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun EmailValidate(): Boolean {
        val emailText = ti_Email.text.toString()
        if (emailText.isEmpty()) {
            emailContainer.helperText = "Required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            emailContainer.helperText = "Email address invalide"
            return false
        } else {
            emailContainer.helperText = ""
        }
        return true
    }

    private fun PassValidate(): Boolean {
        val passwordText = ti_Password.text.toString()
        if (passwordText.isEmpty()) {
            passwordContainer.helperText = "Required"
            return false
        } else if (passwordText.length < 6) {
            passwordContainer.helperText = "Minimum 8 Character Password"
            return false
        }
        else if (passwordText.length > 16) {
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

    private fun ConfirmPassValidate(): Boolean {
        val confirmPasswordText = ti_ConfirmPassword.text.toString()
        val passwordText = ti_Password.text.toString()
        if (confirmPasswordText.isEmpty()) {
            confirmPasswordContainer.helperText = "Required"
            return false
        }
        else if (confirmPasswordText != passwordText) {
            confirmPasswordContainer.helperText = "Passwords does not match"
            return false
        } else {
            confirmPasswordContainer.helperText = ""
        }
        return true
    }
}