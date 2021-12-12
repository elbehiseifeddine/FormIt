package com.example.formit.ui.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.formit.R
import com.example.formit.data.repository.ApiInterface
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_sign_in_up.*
import retrofit2.Call
import retrofit2.Callback
import java.util.HashMap

class reset_password : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        var email = intent.getStringExtra("EMAIL")

        btn_reset.setOnClickListener {
            if (ResetPassValidate() && ResetConfirmPassValidate()) {
                val map: HashMap<String, String> = HashMap()
                map["password"] = ti_resetPassword.text.toString()
                apiInterface.resetPassword(email, map).enqueue(object :
                    Callback<String> {
                    override fun onResponse(
                        call: Call<String>, response:
                        retrofit2.Response<String>
                    ) {
                        val messages = response.body()
                        if (messages.equals("password reset")){
                            Toast.makeText(this@reset_password, "Password reset!", Toast.LENGTH_SHORT).show()
                            var intent = Intent(this@reset_password,SignInUpActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("failure forget password Activity", "true")
                    }
                })

            }


        }
    }

    private fun ResetPassValidate(): Boolean {
        val passwordText = ti_resetPassword.text.toString()
        if (passwordText.isEmpty()) {
            resetPasswordContainer.helperText = "Required"
            return false
        } else if (passwordText.length < 6) {
            resetPasswordContainer.helperText = "Minimum 8 Character Password"
            return false
        }
        else if (passwordText.length > 16) {
            resetPasswordContainer.helperText = "Maximum 16 Character Password"
            return false
        } else if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            resetPasswordContainer.helperText = "Must Contain 1 Upper-case Character"
            return false
        } else if (!passwordText.matches(".*[a-z].*".toRegex())) {
            resetPasswordContainer.helperText = "Must Contain 1 Lower-case Character"
            return false
        } else if (!passwordText.matches(".*[0-9].*".toRegex())) {
            resetPasswordContainer.helperText = "Must Contain 1 Number"
            return false
        } else {
            resetPasswordContainer.helperText = ""
        }
        return true
    }

    private fun ResetConfirmPassValidate(): Boolean {
        val confirmPasswordText = ti_resetPasswordConfirm.text.toString()
        val passwordText = ti_resetPassword.text.toString()
        if (confirmPasswordText.isEmpty()) {
            resetPasswordContainerConfirm.helperText = "Required"
            return false
        }
        else if (confirmPasswordText != passwordText) {
            resetPasswordContainerConfirm.helperText = "Passwords does not match"
            return false
        } else {
            resetPasswordContainerConfirm.helperText = ""
        }
        return true
    }

}