package com.example.formit.ui.view.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.formit.R
import com.example.formit.data.ApiInterface
import com.example.formit.data.model.User
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_sign_in_up.*
import kotlinx.android.synthetic.main.description_toolbar.*
import kotlinx.android.synthetic.main.description_toolbar.toolbar_title
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class EditProfileActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        toolbar_title.text="Edit Profile"
        button_Right.visibility= View.GONE

        btn_reus_back.setOnClickListener {
            finish()
        }
        btn_Update.setOnClickListener {

            val apiInterface = ApiInterface.create()
            val map: HashMap<String, String> = HashMap()
            map["email"] = ti_EditEmail.text.toString()
            map["firstname"] = ti_EditFirstName.text.toString()
            map["lastname"] = ti_EditLastName.text.toString()
            apiInterface.UpdateCurrentUser(mSharedPref.getString(ID,""),map).enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>, response:
                    Response<User>
                ) {
                    val user = response.body()
                    if (user != null) {
                        mSharedPref.edit().apply{
                            putString(EMAIL, user.email)
                            putString(FIRSTNAME, user.firstname)
                            putString(LASTNAME, user.lastname)
                        }.apply()
                        finish()

                    } else {
                        Log.e("Something went wrong","true")
                        Toast.makeText(this@EditProfileActivity, "Something went wrong !!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("aaaaaaaaaaaaaaaaaaaaaaaa","true")
                    Toast.makeText(this@EditProfileActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
                }
            })
        }
        ti_EditEmail.setText(mSharedPref.getString(EMAIL,"").toString())
        ti_EditFirstName.setText(mSharedPref.getString(FIRSTNAME,"").toString())
        ti_EditLastName.setText(mSharedPref.getString(LASTNAME,"").toString())
        tv_FullName.setText(mSharedPref.getString(FIRSTNAME,"").toString()+" "+mSharedPref.getString(LASTNAME,"").toString())

    }
}