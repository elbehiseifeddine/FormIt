package com.example.formit.ui.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.example.formit.R
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.description_toolbar.*
import kotlinx.android.synthetic.main.description_toolbar.toolbar_title
import kotlinx.android.synthetic.main.reusable_toolbar.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var myToolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar_title.text="Settings"
        button_Right.visibility= View.GONE

        btn_reus_back.setOnClickListener {
            finish()
        }

        tv_EditProfile.setOnClickListener{
            Intent(this, EditProfileActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}