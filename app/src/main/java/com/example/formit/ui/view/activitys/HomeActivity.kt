package com.example.formit.ui.view.activitys

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.formit.R
import com.example.formit.ui.view.fragments.*
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)







        GoogleApiAvailability.getInstance().makeGooglePlayServicesAvailable(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            // Log and toast
            //val msg = getString(R.string.msg_token_fmt, token)
            val msg = token
            Log.e(TAG, msg.toString())
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })










        val home_Fragment= HomeFragment()
        val messages_Fragment= MessagesFragment.newInstance(mSharedPref.getString(EMAIL,"").toString())
        val bookmark_Fragment= BookmarkFragment()
        val notification_Fragment= NotificationsFragment()
        val profile_Fragment= ProfileFragment()
        setCurrentFragment(home_Fragment)
        bottomNavigationView.setOnItemSelectedListener() {
            when(it.itemId) {
                R.id.mihome->setCurrentFragment(home_Fragment)
                R.id.mimessages-> {

                    setCurrentFragment(messages_Fragment)
                }
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