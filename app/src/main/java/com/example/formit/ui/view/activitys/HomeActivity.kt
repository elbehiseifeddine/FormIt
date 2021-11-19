package com.example.formit.ui.view.activitys

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.formit.R
import com.example.formit.ui.view.fragments.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val home_Fragment= HomeFragment()
        val messages_Fragment= MessagesFragment()
        val bookmark_Fragment= BookmarkFragment()
        val notification_Fragment= NotificationsFragment()
        val profile_Fragment= ProfileFragment()
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        setCurrentFragment(home_Fragment)
        bottomNavigationView.setOnItemSelectedListener() {
            when(it.itemId) {
                R.id.mihome->setCurrentFragment(home_Fragment)
                R.id.mimessages->setCurrentFragment(messages_Fragment)
                R.id.mibookmark->setCurrentFragment(bookmark_Fragment)
                R.id.minotification->
                {
                    setCurrentFragment(notification_Fragment)
                }
                R.id.miprofile->setCurrentFragment(profile_Fragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_Fragment,fragment)
            commit()
        }
}