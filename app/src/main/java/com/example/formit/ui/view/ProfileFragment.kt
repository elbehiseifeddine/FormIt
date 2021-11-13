package com.example.formit.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.formit.R
import kotlinx.android.synthetic.main.reusable_toolbar.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView : View = inflater.inflate(R.layout.fragment_profile, container, false)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_title.text="Profile"

        button_Right.setBackgroundResource(R.drawable.ic_logout)

        button_Right.setOnClickListener{
            activity?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
                ?.edit()
                ?.clear()?.apply()
            Intent(context, SignInUpActivity::class.java).also {
                startActivity(it)
                activity?.finish()
            }
        }
        btn_reus_back.visibility=View.GONE
    }


}