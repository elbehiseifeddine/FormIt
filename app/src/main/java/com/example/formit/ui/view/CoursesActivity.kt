package com.example.formit.ui.view
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.ui.adapter.CoursesAdapter
import kotlinx.android.synthetic.main.activity_courses.*
import kotlinx.android.synthetic.main.reusable_toolbar.*

class CoursesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)

        var coursesList= mutableListOf(
            Course("Android Course","You will learn how to manipulate and create an android app using kotlin",
                "Seifeddine El Behi",150,50,"Android studio with all the sdks installed","12/5/2022"),
            Course("Ios Course","You will learn how to manipulate and create an Ios app using swift",
                "Ahmed Ben Dahmen",150,50,"Android studio with all the sdks installed","12/5/2022"),
            Course(".Net Core Course","You will learn how to manipulate and create a backend  using c#",
                "Dali ben chikha",150,50,"Android studio with all the sdks installed","12/5/2022")
        )

        toolbar_title.text="Courses"
        button_Right.visibility=View.INVISIBLE

        btn_reus_back.setOnClickListener {
            finish()
        }
        val adapter= CoursesAdapter(coursesList)

        rv_Cour.adapter=adapter

        rv_Cour.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
    }
}