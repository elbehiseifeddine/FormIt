package com.example.formit

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.formit.ui.view.activitys.EMAIL
import com.example.formit.ui.view.activitys.PREF_NAME
import com.example.formit.ui.view.activitys.apiInterface
import kotlinx.android.synthetic.main.contactus_form.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class ContactUsActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_contact_us)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);mSharedPref.getString(EMAIL, "").toString()

        toolbar_title.text="Contact Us"
        button_Right.visibility= View.GONE


        btn_reus_back.setOnClickListener {
            finish()
        }

        btn_Contact_us_Send_Mail.setOnClickListener {
            if (SubjectValidate()&&BodyValidate()){

                progBarContactUs.visibility = View.VISIBLE
                btn_Contact_us_Send_Mail.visibility = View.GONE

                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
                sendMail()
            }
        }
    }



    private fun sendMail(){
        val map: HashMap<String, String> = HashMap()
        map["mailBody"] = ti_Contact_us_EditEmailBody.text.toString()
        map["mailSubject"] = ti_Contact_us_EditEmailSubject.text.toString()
            apiInterface.sendMailToFormit(mSharedPref.getString(EMAIL, "").toString(), map).enqueue(object :
            Callback<String> {
            override fun onResponse(
                call: Call<String>, response:
                retrofit2.Response<String>
            ) {
                val messages = response.body()

                if (messages.equals("non valider")){

                    Toast.makeText(this@ContactUsActivity, "An error has been occured", Toast.LENGTH_SHORT).show()
                }

                if(messages.equals("valider")){
                    progBarContactUs.visibility = View.GONE
                    btn_Contact_us_Send_Mail.visibility = View.VISIBLE
                    window.clearFlags( WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                    Toast.makeText(this@ContactUsActivity, "Mail send Succefully", Toast.LENGTH_SHORT).show()


                }



            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("failure forget password Activity","true")
            }
        })
    }

    private fun SubjectValidate(): Boolean {
        val emailText = ti_Contact_us_EditEmailSubject.text.toString()
        if (emailText.isEmpty()) {
            Contact_us_EditEmailSubjectContainer.helperText = "Required"
            return false
        } else {
            Contact_us_EditEmailSubjectContainer.helperText = ""
        }
        return true
    }

    private fun BodyValidate(): Boolean {
        val emailText = ti_Contact_us_EditEmailBody.text.toString()
        if (emailText.isEmpty()) {
            Contact_us_EditEmailBodyContainer.helperText = "Required"
            return false
        } else {
            Contact_us_EditEmailBodyContainer.helperText = ""
        }
        return true
    }
}