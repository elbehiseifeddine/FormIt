package com.example.formit.ui.view.activitys

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.formit.R
import com.example.formit.data.model.User
import com.example.formit.data.repository.ApiInterface
import kotlinx.android.synthetic.main.activity_paiment_methode.*
import kotlinx.android.synthetic.main.description_toolbar.*
import kotlinx.android.synthetic.main.description_toolbar.toolbar_title
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaimentMethodeActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paiment_methode)

        toolbar_title.text="Paiment Methode"
        button_Right.visibility= View.GONE

        btn_reus_back.setOnClickListener {
            finish()
        }

        btn_payCourse.setOnClickListener {
            apiInterface.AddParticipation(mSharedPref.getString(ID,"")as String,intent.getStringExtra("IDCOURS")).enqueue(object :
                Callback<String> {
                override fun onResponse(
                    call: Call<String>, response:
                    Response<String>
                ) {
                    val courses = response.body()
                    if (courses != null) {
                        if(courses.equals("no places left")){
                            Log.e("coursessssssssssdsgfdgfdg","no places left")
                            finish()
                        }else{
                            Log.e("coursessssssssssdsgfdgfdg","participation done")
                            finish()
                        }
                    } else {
                        Log.e("Username or Password wrong","true")
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("bbbbbbbbbbbbbbbbbbbbbbb","true")
                }
            })
        }
    }
}