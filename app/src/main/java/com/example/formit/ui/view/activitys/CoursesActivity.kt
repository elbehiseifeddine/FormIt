package com.example.formit.ui.view.activitys
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CoursesAdapter
import kotlinx.android.synthetic.main.activity_courses.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesActivity : AppCompatActivity() {
//    lateinit var courses:MutableList<Course>
    lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)
        var courses:MutableList<Course> = ArrayList()
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        apiInterface.getCoursesNotParticipated(mSharedPref.getString(ID, "")).enqueue(object : Callback<MutableList<Course>> {
            override fun onResponse(
                call: Call<MutableList<Course>>, response:
                Response<MutableList<Course>>
            ) {
                 courses = response.body() as MutableList<Course>
                if (courses != null) {
                    Log.e("coursesaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",courses.toString())
                    val adapter= CoursesAdapter(courses,false)
                    rv_Cour.adapter=adapter
                    rv_Cour.layoutManager =
                        LinearLayoutManager(this@CoursesActivity, LinearLayoutManager.VERTICAL, false)

                } else {
                    Log.e("Username or Password wrong","true")
                }
            }

            override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa","true")
            }
        })

        sv_Search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                apiInterface.search(ApiInterface.SearchBody(newText,mSharedPref.getString(ID, "").toString())).enqueue(
                    object : Callback<ApiInterface.SearchResponse> {
                        override fun onResponse(
                            call: Call<ApiInterface.SearchResponse>,
                            response: Response<ApiInterface.SearchResponse>
                        ) {
                            courses.clear()
                            if (response.code() == 200) {
                                response.body()!!.courses.forEach {

                                    courses.add(it)
                                }
                                Log.e("aaaaabbbbbbbbbbbb",courses.toString())
                                val adapter= CoursesAdapter(courses,false)
                                rv_Cour.adapter=adapter
                                rv_Cour.setLayoutManager( LinearLayoutManager(CoursesActivity()))
                            } else {
                                courses.clear()

                            }
                        }

                        override fun onFailure(
                            call: Call<ApiInterface.SearchResponse>,
                            t: Throwable
                        ) {
                            Log.e("onfailure search ","true")
                            courses.clear()

                        }

                    }
                )
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })

        toolbar_title.text="Courses"
        button_Right.visibility=View.INVISIBLE

        btn_reus_back.setOnClickListener {
            finish()
        }

    }

}