package com.example.formit.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formit.R
import kotlinx.android.synthetic.main.activity_description.*


class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val courseName = intent.getStringExtra("NAME")

        tv_CourseName.text=courseName
    }
}