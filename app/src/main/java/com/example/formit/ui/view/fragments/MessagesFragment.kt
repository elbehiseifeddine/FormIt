package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.*
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.BubleMessageAdapter
import com.example.formit.ui.adapter.CoacheDiscussionAdapter
import com.example.formit.ui.adapter.CourseDiscussionAdapter
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.view.activitys.EMAIL
import com.example.formit.ui.view.activitys.ID
import com.example.formit.ui.view.activitys.PREF_NAME
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MessagesFragment: Fragment() {
    val apiInterface = ApiInterface.create()
    lateinit var mSharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val email = requireArguments().getString(EMAIL,"NULL")
        btn_reus_back.visibility=View.INVISIBLE
        var BubleList = mutableListOf(
            Buble_Message(R.drawable.test1,"ahmed",true),
            Buble_Message(R.drawable.test2,"Seif",false),
            Buble_Message(R.drawable.test3,"ahmedSeif",false),

        )
        val adapterBuble =BubleMessageAdapter(BubleList)
        bubleMessageRecycleView.adapter = adapterBuble
        bubleMessageRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        apiInterface.getOwnConversations(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Conversation>> {
            override fun onResponse(
                call: Call<MutableList<Conversation>>, response:
                Response<MutableList<Conversation>>
            ) {
                val conversations = response.body()
                if (conversations != null) {
                    Log.e("conversations",conversations.toString())
                    val adapterCourse =CourseDiscussionAdapter(conversations)
                    CoursesDiscussionRecycleView.adapter = adapterCourse
                    CoursesDiscussionRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

                } else {
                    Log.e("Error from conversation course","true")
                }
            }

            override fun onFailure(call: Call<MutableList<Conversation>>, t: Throwable) {
                Log.e("failure conversation course","true")
            }
        })
        var CourseDiscussion = mutableListOf(
            Course_Discussion(
                R.drawable.welcome_pic,
                "Android Course",
                "Welcome All of you ...",
                "1 hour",
                9,
                R.drawable.test1,
                R.drawable.test2,
                R.drawable.test3
            ),
            Course_Discussion(
                R.drawable.test2,
                "Ios Course",
                "So what new ...",
                "1 hour",
                1,
                R.drawable.male_student,
                R.drawable.welcome_pic,
                R.drawable.test3
            )
        )
        val adapterCourse =CourseDiscussionAdapter(CourseDiscussion)
        CoursesDiscussionRecycleView.adapter = adapterCourse
        CoursesDiscussionRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)


        var CoacheDiscussion = mutableListOf(
            Coache_Discussion(
                R.drawable.test1,
                "Ahmed Ben Dahmen",
                "Welcome seifoun !!",
                "1 hour",
                9,
            ),
            Coache_Discussion(
                R.drawable.test4,
                "SeifEddine ElBehi",
                "Yoo bouhmid ...",
                "1 hour",
                1,
            )
        )
        val adapterCoache =CoacheDiscussionAdapter(CoacheDiscussion,email)
        CoachesDiscussionRecycleView.adapter = adapterCoache
        CoachesDiscussionRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    }


    companion object {
        @JvmStatic
        fun newInstance(email: String) = MessagesFragment().apply {
            arguments = Bundle().apply {
                putString(EMAIL, email)

            }
        }
    }
}