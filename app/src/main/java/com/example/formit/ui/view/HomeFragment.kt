package com.example.formit.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.ui.adapter.CoursesAdapter
import kotlinx.android.synthetic.main.fragment_home.*
class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView : View = inflater.inflate(R.layout.fragment_home, container, false)


        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var coursesList= mutableListOf(
            Course("Android Course","You will learn how to manipulate and create an android app using kotlin",
                "Seifeddine El Behi",150,50,"Android studio with all the sdks installed","12/5/2022"),
            Course("Ios Course","You will learn how to manipulate and create an Ios app using swift",
                "Ahmed Ben Dahmen",150,50,"Android studio with all the sdks installed","12/5/2022"),
            Course(".Net Core Course","You will learn how to manipulate and create a backend  using c#",
                "Dali ben chikha",150,50,"Android studio with all the sdks installed","12/5/2022")
        )

        val adapter= CoursesAdapter(coursesList)

        rv_courses.adapter=adapter
        rv_courses.layoutManager =LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
    }


}