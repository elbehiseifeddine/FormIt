package com.example.formit.ui.view.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.formit.R
import com.example.formit.data.repository.ApiInterface
import kotlinx.android.synthetic.main.activity_description_event.*
import kotlinx.android.synthetic.main.description_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionEventActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    private lateinit var myToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_event)

        myToolbar = findViewById (R.id.toolbar)
        btn_back.setOnClickListener {
            finish()
        }
        setSupportActionBar(myToolbar)

        tv_EventName.text=intent.getStringExtra("NAME")
        tv_LocationName.text="Location: " +intent.getStringExtra("LOCATION")
        tv_Hours.text=intent.getStringExtra("DURATION")
        tv_Description.text=intent.getStringExtra("DESCRIPTION")
        tv_Places.text=intent.getStringExtra("PLACES")
        if(intent.getBooleanExtra("PARTICIPATED",false)){
            btn_Participate.visibility= View.GONE
        }
        else{
            btn_Participate.setOnClickListener {
                val apiInterface = ApiInterface.create()
                apiInterface.AddEventParticipation(mSharedPref.getString(ID,"")as String,intent.getStringExtra("ID")).enqueue(object :
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu,menu)
        return true
    }
}