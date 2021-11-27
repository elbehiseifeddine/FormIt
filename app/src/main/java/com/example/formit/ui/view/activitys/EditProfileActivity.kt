package com.example.formit.ui.view.activitys

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.formit.R
import com.example.formit.data.model.User
import com.google.android.material.datepicker.MaterialDatePicker
import com.example.formit.data.repository.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    lateinit var EditBirthdate: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        toolbar_title.text="Edit Profile"
        button_Right.visibility= View.GONE

        EditBirthdate = findViewById(R.id.ti_EditBirthdate)

        btn_reus_back.setOnClickListener {
            finish()
        }

        val birthDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select start date")
            .build()
        birthDatePicker.addOnPositiveButtonClickListener {
            EditBirthdate.setText(birthDatePicker.headerText.toString())
        }

        EditBirthdate.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus){
                birthDatePicker.show(supportFragmentManager, "START_DATE")
            }else{
                birthDatePicker.dismiss()
            }
        }

        btn_Update.setOnClickListener {

            val apiInterface = ApiInterface.create()
            val map: HashMap<String, String> = HashMap()
            map["email"] = ti_EditEmail.text.toString()
            map["firstname"] = ti_EditFirstName.text.toString()
            map["lastname"] = ti_EditLastName.text.toString()
            map["birthdate"] = EditBirthdate.text.toString()
            map["address"] = ti_EditAddress.text.toString()
            map["telnumber"] = ti_EditPhoneNumber.text.toString()
            apiInterface.UpdateCurrentUser(mSharedPref.getString(ID,"").toString(),map).enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>, response:
                    Response<User>
                ) {
                    val user = response.body()
                    if (user !=null) {
                        mSharedPref.edit().apply{
                            putString(EMAIL, ti_EditEmail.text.toString())
                            putString(FIRSTNAME, ti_EditFirstName.text.toString())
                            putString(LASTNAME, ti_EditLastName.text.toString())
                            putString(ADDRESS, ti_EditAddress.text.toString())
                            putString(BIRTHDATE, EditBirthdate.text.toString())
                            putInt(PHONENUMBER, ti_EditPhoneNumber.text.toString().toInt())
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
        ti_EditFirstName.setText(mSharedPref.getString(FIRSTNAME,"").toString().capitalize())
        ti_EditLastName.setText(mSharedPref.getString(LASTNAME,"").toString().capitalize())
        ti_EditAddress.setText(mSharedPref.getString(ADDRESS,"").toString().capitalize())
        EditBirthdate.setText(mSharedPref.getString(BIRTHDATE,"").toString())
        ti_EditPhoneNumber.setText(mSharedPref.getInt(PHONENUMBER,0).toString())
        tv_FullName.setText(mSharedPref.getString(FIRSTNAME,"").toString().capitalize()+" "+mSharedPref.getString(LASTNAME,"").toString().capitalize())
    }
}