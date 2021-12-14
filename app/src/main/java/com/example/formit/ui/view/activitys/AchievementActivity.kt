package com.example.formit.ui.view.activitys

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.formit.R
import kotlinx.android.synthetic.main.activity_achievement.*


class AchievementActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        if(mSharedPref.getInt(XP, 0)<500){
            iv_AchievementPic.setImageResource(R.drawable.ic_bronze)
            tv_Achievement.text="Bronze"
            tv_AchievementXP.setText("${mSharedPref.getInt(XP, 0)}/500 ")
            pb_AchievementText.setText("${mSharedPref.getInt(XP, 0)} ")
            pb_Achievement.progress=mSharedPref.getInt(XP, 0)/5
        }else if (mSharedPref.getInt(XP, 0)>=500 && mSharedPref.getInt(XP, 0)<1000){
            iv_AchievementPic.setImageResource(R.drawable.ic_sliver)
            iv_AchievementXPMessage.setImageResource(R.drawable.silver_xp)
            tv_Achievement.text="Silver"
            tv_Achievement.setTextColor(Color.parseColor("#A0A2A0"))
            tv_AchievementXP.setText("${mSharedPref.getInt(XP, 0)}/1000 ")
            tv_AchievementXP.setTextColor(Color.parseColor("#A0A2A0"))
            pb_AchievementText.setText("${mSharedPref.getInt(XP, 0)} ")
            pb_Achievement.progress=(mSharedPref.getInt(XP, 0)-500)/5
            Xp_Text.setTextColor(Color.parseColor("#A0A2A0"))
            benefits.setText("Benefit : 10% Reduction ")
            benefits.setTextColor(Color.parseColor("#A0A2A0"))
            pb_Achievement.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#CBCEC9")))
            pb_Achievement.setProgressBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#A0A2A0")))
        }else if (mSharedPref.getInt(XP, 0)>=1000){
            iv_AchievementPic.setImageResource(R.drawable.ic_gold)
            iv_AchievementXPMessage.setImageResource(R.drawable.gold_xp)
            tv_Achievement.text="Gold"
            tv_Achievement.setTextColor(Color.parseColor("#FDB300"))
            tv_AchievementXP.setText("${mSharedPref.getInt(XP, 0)}/1000 ")
            tv_AchievementXP.setTextColor(Color.parseColor("#FDB300"))
            pb_AchievementText.setText("${mSharedPref.getInt(XP, 0)} ")
            pb_Achievement.progress=(mSharedPref.getInt(XP, 0)-500)/5
            Xp_Text.setTextColor(Color.parseColor("#FDB300"))
            benefits.setText("Benefit : Free Course ")
            benefits.setTextColor(Color.parseColor("#FDB300"))
            pb_Achievement.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FED500")))
            pb_Achievement.setProgressBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FDB300")))

        }
    }
}