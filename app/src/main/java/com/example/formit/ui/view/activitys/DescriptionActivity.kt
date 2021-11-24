package com.example.formit.ui.view.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View

import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.formit.R
import kotlinx.android.synthetic.main.activity_description.*
import kotlinx.android.synthetic.main.description_toolbar.*
import kotlinx.android.synthetic.main.item_course.view.*


class DescriptionActivity : AppCompatActivity() {
    private lateinit var myToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        myToolbar = findViewById (R.id.toolbar)
        btn_back.setOnClickListener {
            finish()
        }
        setSupportActionBar(myToolbar)

        tv_CourseName.text=intent.getStringExtra("NAME")
        tv_MentorName.text="Mentor: " +intent.getStringExtra("MENTOR")
        tv_Hours.text=intent.getStringExtra("DURATION")
        tv_Price.text=intent.getStringExtra("PRICE")
        tv_Prerequisites.text=intent.getStringExtra("PREREQUISITES")
        tv_Description.text=intent.getStringExtra("DESCRIPTION")
        tv_Places.text=intent.getStringExtra("PLACES")
        if(intent.getBooleanExtra("PARTICIPATED",false)){
            btn_Participate.visibility= View.GONE
        }
        else{
            btn_Participate.setOnClickListener {
                Intent(this, PaimentMethodeActivity::class.java).also {
                    it.putExtra("IDCOURS", intent.getStringExtra("ID"))
                    startActivity(it)
                    finish()
                }
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