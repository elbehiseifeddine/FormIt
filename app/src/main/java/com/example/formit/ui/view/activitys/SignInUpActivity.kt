package com.example.formit.ui.view.activitys

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.formit.R
import com.example.formit.data.repository.ApiInterface
import com.example.formit.data.model.User
import kotlinx.android.synthetic.main.activity_sign_in_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

const val PREF_NAME = "LOGIN_PREF"
const val EMAIL = "EMAIL"
const val PASSWORD = "PASSWORD"
const val ROLE = "ROLE"
const val ID= "ID"
const val FIRSTNAME= "FIRSTNAME"
const val LASTNAME= "LASTNAME"
const val SERVER_PATH= "http://192.168.137.1:5000"
const val SERVER_CHAT_PATH= "ws://192.168.137.1:3000"
const val ADDRESS= "ADDRESS"
const val PICTURE= "PICTURE"
const val XP= "XP"
const val BIRTHDATE= "BIRTHDATE"
const val PHONENUMBER= "PHONENUMBER"
const val IS_REMEMBRED = "IS_REMEMBRED"
val apiInterface = ApiInterface.create()
class SignInUpActivity : AppCompatActivity() {

    //TODO 1 "Declare a var of SharedPreferences"
    lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_up)

        //TODO 2 "Initialize the var of SharedPreferences"
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        //TODO 3 "Test in the SharedPreferences if there's data"
        if (mSharedPref.getBoolean(IS_REMEMBRED, false)){
            Intent(this, HomeActivity::class.java).also {

                startActivity(it)
                finish()
            }
        }

        val shader = LinearGradient(
            0f,
            0f,
            0f,
            singUp.textSize,
            Color.parseColor("#647DEE"),
            Color.parseColor("#7F53AC"),
            Shader.TileMode.CLAMP
        )
        singUp.paint.shader = shader
        singUp.setOnClickListener {
            singUp.background = resources.getDrawable(R.drawable.gradient_background_and_rounded,null)
            singUp.paint.shader = null
            singUp.setTextColor(resources.getColor(R.color.white,null))
            logIn.background = null
            singUpLayout.visibility = View.VISIBLE
            logInLayout.visibility = View.GONE
            logIn.paint.shader = shader
        }
        logIn.setOnClickListener {
            singUp.background = null
            singUp.paint.shader = shader
            logIn.paint.shader = null
            logIn.background = resources.getDrawable(R.drawable.gradient_background_and_rounded,null)
            singUpLayout.visibility = View.GONE
            logInLayout.visibility = View.VISIBLE
            logIn.setTextColor(resources.getColor(R.color.white,null))
        }
        btn_SingIn.setOnClickListener {
            clickLogin()
        }
        btn_SignUp.setOnClickListener {
            clickSignUp()

        }

        forgetpassword.setOnClickListener {
            val intent = Intent(this@SignInUpActivity, forget_password::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun clickLogin() {
        val emailVerif = SignInEmailValidate()
        val passVerif = SignInPassValidate()
        if (emailVerif && passVerif) {
            progBarSignIn.visibility = View.VISIBLE
            btn_SingIn.visibility = View.GONE

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            val apiInterface = ApiInterface.create()
            val map: HashMap<String, String> = HashMap()
            map["email"] = ti_SignInEmail.text.toString()
            map["password"] = ti_SignInPassword.text.toString()
            Log.e("user",map.toString())
            apiInterface.login(map).enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>, response:
                    Response<User>
                ) {
                    Log.e("user from onResponse",map.toString())
                    val user = response.body()
                    if (user != null) {
                        progBarSignIn.visibility = View.GONE
                        btn_SingIn.visibility = View.VISIBLE
                        window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Log.e("user : ",user.toString())

                            //TODO 4 "Edit the SharedPreferences by putting all the data"
                            mSharedPref.edit().apply{
                                putBoolean(IS_REMEMBRED, true)
                                putString(EMAIL, user.email)
                                putString(FIRSTNAME, user.firstname)
                                putString(LASTNAME, user.lastname)
                                putString(PASSWORD, ti_SignInPassword.text.toString())
                                putInt(PHONENUMBER, user.phonenumber)
                                putString(ROLE, user.role)
                                putInt(XP, user.achievements)
                                putString(ADDRESS, user.address)
                                putString(PICTURE, user.picture)
                                putString(ID, user.id)
                            }.apply()


                        val intent = Intent(this@SignInUpActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        progBarSignIn.visibility = View.GONE
                        btn_SingIn.visibility = View.VISIBLE
                        window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        mSharedPref.edit().clear().apply()
                        Log.e("Username or Password wrong","true")
                        Toast.makeText(this@SignInUpActivity, "Username or Password wrong !!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("aaaaaaaaaaaaaaaaaaaaaaaa","true")
                    Toast.makeText(this@SignInUpActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
                    progBarSignIn.visibility = View.GONE
                    btn_SingIn.visibility = View.VISIBLE
                    window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            })
        }
    }

    private fun SignInEmailValidate(): Boolean {
        val emailText = ti_SignInEmail.text.toString()
        if(emailText.isEmpty()){
            SignInEmailContainer.helperText = "Required"
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            SignInEmailContainer.helperText = "Email address invalide"
            return false
        } else {
            SignInEmailContainer.helperText = ""
        }
        return true
    }

    private fun SignInPassValidate(): Boolean {
        val passwordText = ti_SignInPassword.text.toString()
        if(passwordText.isEmpty()){
            SignInPasswordContainer.helperText = "Required"
            return false
        }
        else if (passwordText.length < 6) {
            SignInPasswordContainer.helperText = "Minimum 8 Character Password"
            return false
        }else if (passwordText.length > 16) {
            SignInPasswordContainer.helperText = "Maximum 16 Character Password"
            return false
        } else if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            SignInPasswordContainer.helperText = "Must Contain 1 Upper-case Character"
            return false
        } else if (!passwordText.matches(".*[a-z].*".toRegex())) {
            SignInPasswordContainer.helperText = "Must Contain 1 Lower-case Character"
            return false
        } else if (!passwordText.matches(".*[0-9].*".toRegex())) {
            SignInPasswordContainer.helperText = "Must Contain 1 Number"
            return false
        } else {
            SignInPasswordContainer.helperText = ""
        }
        return true
    }

    private fun clickSignUp() {
        val emailVerif = SignUpEmailValidate()
        val passVerif = SignUpPassValidate()
        val confirmPassVerif = SignUpConfirmPassValidate()
        if (emailVerif && passVerif && confirmPassVerif) {
            progBarSignUp.visibility = View.VISIBLE
            btn_SignUp.visibility = View.GONE

            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            val apiInterface = ApiInterface.create()
            val map: HashMap<String, String> = HashMap()
            map["email"] = ti_signUpEmail.text.toString()
            map["password"] = ti_signUpPassword.text.toString()
            apiInterface.signup(map).enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>, response:
                    Response<User>
                ) {
                    val user = response.body()
                    if (user != null) {
                        progBarSignUp.visibility = View.GONE
                        btn_SignUp.visibility = View.VISIBLE
                        window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                            mSharedPref.edit().clear().apply()
                            //TODO 4 "Edit the SharedPreferences by putting all the data"
                            mSharedPref.edit().apply{
                                putBoolean(IS_REMEMBRED, true)
                                putString(EMAIL, user.email)
                                putString(FIRSTNAME, user.firstname)
                                putString(LASTNAME, user.lastname)
                                putInt(PHONENUMBER, user.phonenumber)
                                putInt(XP, user.achievements)
                                putString(BIRTHDATE, user.birthdate)
                                putString(ROLE, user.role)
                                putString(ADDRESS, user.address)
                                putString(PASSWORD, ti_signUpPassword.text.toString())
                                putString(PICTURE, user.picture)
                                putString(ID, user.id)
                            }.apply()


                        val intent = Intent(this@SignInUpActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        progBarSignUp.visibility = View.GONE
                        btn_SignUp.visibility = View.VISIBLE
                        window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        Log.e("Username or Password wrong","true")
                        Toast.makeText(this@SignInUpActivity, "Username or Password wrong !!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@SignInUpActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
                    progBarSignUp.visibility = View.GONE
                    btn_SignUp.visibility = View.VISIBLE
                    window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
            })
        }
    }

    private fun SignUpEmailValidate(): Boolean {
        val emailText = ti_signUpEmail.text.toString()
        if (emailText.isEmpty()) {
            signUpEmailContainer.helperText = "Required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            signUpEmailContainer.helperText = "Email address invalide"
            return false
        } else {
            signUpEmailContainer.helperText = ""
        }
        return true
    }

    private fun SignUpPassValidate(): Boolean {
        val passwordText = ti_signUpPassword.text.toString()
        if (passwordText.isEmpty()) {
            signUpPasswordContainer.helperText = "Required"
            return false
        } else if (passwordText.length < 6) {
            signUpPasswordContainer.helperText = "Minimum 8 Character Password"
            return false
        }
        else if (passwordText.length > 16) {
            signUpPasswordContainer.helperText = "Maximum 16 Character Password"
            return false
        } else if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            signUpPasswordContainer.helperText = "Must Contain 1 Upper-case Character"
            return false
        } else if (!passwordText.matches(".*[a-z].*".toRegex())) {
            signUpPasswordContainer.helperText = "Must Contain 1 Lower-case Character"
            return false
        } else if (!passwordText.matches(".*[0-9].*".toRegex())) {
            signUpPasswordContainer.helperText = "Must Contain 1 Number"
            return false
        } else {
            signUpPasswordContainer.helperText = ""
        }
        return true
    }

    private fun SignUpConfirmPassValidate(): Boolean {
        val confirmPasswordText = ti_ConfirmPassword.text.toString()
        val passwordText = ti_signUpPassword.text.toString()
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
