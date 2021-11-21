package com.example.formit.ui.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.formit.R
import kotlinx.android.synthetic.main.description_toolbar.*
import kotlinx.android.synthetic.main.description_toolbar.toolbar_title
import kotlinx.android.synthetic.main.reusable_toolbar.*

class PaimentMethodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paiment_methode)

        toolbar_title.text="Paiment Methode"
        button_Right.visibility= View.GONE

        btn_reus_back.setOnClickListener {
            finish()
        }
    }
}