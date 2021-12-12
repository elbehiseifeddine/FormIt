package com.example.formit.ui.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.formit.R
import com.example.formit.data.repository.ApiInterface
import kotlinx.android.synthetic.main.activity_forget_password.*
import retrofit2.Call
import retrofit2.Callback
import java.util.HashMap

class forget_password : AppCompatActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        var randomString:String =""

        ti_ForgetPasswordCode!!.addTextChangedListener(this)
        btn_GeneratePassword.setOnClickListener {

            if (ForgetPasswordEmailValidate()) {

                val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
                randomString = (1..12)
                    .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                    .map(charPool::get)
                    .joinToString("")

                Log.e("!!!!!!!!!!!!!!!!!Random String : ", randomString)



                    val map: HashMap<String, String> = HashMap()
                    map["code"] = randomString
                    apiInterface.sendMail(ti_ForgetPasswordEmail.text.toString(), map).enqueue(object :
                        Callback<String> {
                        override fun onResponse(
                            call: Call<String>, response:
                            retrofit2.Response<String>
                        ) {
                            val messages = response.body()
                            if(messages.equals("Invalid Email! Try again")) {

                                ForgetPasswordEmailContainer.helperText = messages
                            }

                            if (messages.equals("non valider")){

                                Toast.makeText(this@forget_password, "An error has been occured", Toast.LENGTH_SHORT).show()
                            }

                            if(messages.equals("valider")){


                                text1.visibility = View.VISIBLE
                                text2.visibility = View.VISIBLE
                                ForgetPasswordCodeContainer.visibility = View.VISIBLE

                                val timerCountDown = object : CountDownTimer(60000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {
                                        timer.visibility = View.VISIBLE
                                        var time =millisUntilFinished/1000
                                        timer.setText(time.toString()+" seconds")

                                    }

                                    override fun onFinish() {
                                        resetMessageEdit()
                                        //btn_GeneratePassword.isEnabled = true
                                        text1.visibility = View.GONE
                                        text2.visibility = View.GONE
                                        timer.visibility = View.GONE
                                        ForgetPasswordCodeContainer.visibility = View.GONE
                                        btn_Validate.visibility=View.GONE
                                        btn_GeneratePassword.visibility=View.VISIBLE
                                    }
                                }
                                timerCountDown.start()

                               // btn_Validate.visibility=View.VISIBLE
                                //btn_GeneratePassword.visibility=View.GONE
                                //btn_Validate.isEnabled=true
                            }



                        }

                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.e("failure forget password Activity","true")
                        }
                    })


                }


            }


        btn_Validate.setOnClickListener {
            Log.e("random validate",randomString)
            if (ti_ForgetPasswordCode.text.toString() == randomString){
                //btn_Validate.isEnabled=true


                val intent =
                    Intent(this@forget_password, reset_password::class.java).also {

                        it.putExtra("EMAIL", ti_ForgetPasswordEmail.text.toString())
                    }
                startActivity(intent)
                finish()
            }

        }
        }


    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        val string = s.toString().trim { it <= ' ' }
        if (string.isEmpty()) {
            resetMessageEdit()
        } else {
            btn_Validate.visibility=View.VISIBLE
            btn_GeneratePassword.visibility=View.GONE
            //btn_Validate.isEnabled=true

        }
    }

    private fun resetMessageEdit() {
        //btn_Validate.isEnabled=false
        ti_ForgetPasswordCode!!.removeTextChangedListener(this)
        ti_ForgetPasswordCode!!.setText("")

        ti_ForgetPasswordCode!!.addTextChangedListener(this)
    }

    private fun ForgetPasswordEmailValidate(): Boolean {
        val emailText = ti_ForgetPasswordEmail.text.toString()
        if(emailText.isEmpty()){
            ForgetPasswordEmailContainer.helperText = "Required"
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            ForgetPasswordEmailContainer.helperText = "Email address invalide"
            return false
        } else {
            ForgetPasswordEmailContainer.helperText = ""
        }
        return true
    }
}