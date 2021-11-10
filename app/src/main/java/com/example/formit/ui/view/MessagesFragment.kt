package com.example.formit.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Buble_Message
import com.example.formit.ui.adapter.BubleMessageAdapter
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.reusable_toolbar.*


class MessagesFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_reus_back.visibility=View.INVISIBLE
        var BubleList = mutableListOf(
            Buble_Message(R.drawable.test1,"ahmed"),
            Buble_Message(R.drawable.test2,"Seif"),
            Buble_Message(R.drawable.test3,"ahmedSeif"),

        )
        val adapter =BubleMessageAdapter(BubleList)
        bubleMessageRecycleView.adapter = adapter
        bubleMessageRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

    }
}