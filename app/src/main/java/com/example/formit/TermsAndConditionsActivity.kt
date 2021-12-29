package com.example.formit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.reusable_toolbar.*

class TermsAndConditionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)

        toolbar_title.text="Term & conditions"
        button_Right.visibility= View.GONE


        btn_reus_back.setOnClickListener {
            finish()
        }
    }
}