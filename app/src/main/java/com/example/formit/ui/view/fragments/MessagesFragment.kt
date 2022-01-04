package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.Conversation
import com.example.formit.ui.adapter.BubleMessageAdapter
import com.example.formit.ui.adapter.CoacheDiscussionAdapter
import com.example.formit.ui.adapter.CourseDiscussionAdapter
import com.example.formit.ui.view.activitys.*
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MessagesFragment : Fragment() {

    //private var webSocket: WebSocket? = null
    //private val SERVER_PATH = "ws://192.168.1.6:3000"
    lateinit var mSharedPref: SharedPreferences

    //private var courseAdapter :CourseDiscussionAdapter?= null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //initiateSocketConnection()
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    /*private fun initiateSocketConnection() {
        val client = OkHttpClient()
        val request = Request.Builder().url(SERVER_PATH).build()
        webSocket = client.newWebSocket(request, SocketListener())
    }


    private inner class SocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            super.onOpen(webSocket, response)
            activity!!.runOnUiThread {
                Toast.makeText(
                    context,
                    "Socket Connection Successful!",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("-", "-------------------------------------------")
                Log.e("before intialized", "true")
                Log.e("-", "-------------------------------------------")
                initializeView()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            activity!!.runOnUiThread {
                try {

                    Log.e("**************Text",text)
                    var textstart = text.indexOf("conversationId=",0,false)+15
                    var textend =text.indexOf(",",textstart,false)
                    var id1 = text.subSequence(textstart,textend)
                    Log.e("**************idConversation",id1.toString())
                    val position = courseAdapter?.getItemPosition(id1.toString())
                    courseAdapter?.getItemPosition(id1.toString())?.let {

                        courseAdapter?.notifyItemChanged(
                            it
                        )
                    }


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun initializeView() {
        this.loadOwnConversation()
    }*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_Right.visibility = View.GONE

        progBarFragMessage.visibility = View.VISIBLE
        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (mSharedPref.getString(ROLE, "").equals("coache")) {
            updateTextWithRole.text = "My Students"
        } else {
            updateTextWithRole.text = "Coaches"
        }

        /*activity?.runOnUiThread{
            this.loadOwnConversation()
        }*/
        if(isAdded()){
        pulltorefresh.setOnRefreshListener {
            this.loadOwnConversation()
            this.loadOwnCoacheConversation()
            this.loadBubleConversation()
        }
        }
        btn_reus_back.visibility = View.INVISIBLE

        /*activity?.runOnUiThread{}*/

        Log.e("id user", mSharedPref.getString(ID, "").toString())
    }

    private fun loadOwnConversation() {
        Log.e("User connected", mSharedPref.getString(ID, "").toString())
        apiInterface.getOwnConversations(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Conversation>> {
            override fun onResponse(
                call: Call<MutableList<Conversation>>, response:
                Response<MutableList<Conversation>>
            ) {

                val conversations = response.body()
                if(isAdded()) {
                    if (conversations != null) {
                        Log.e("conversations", conversations.toString())
                        val adapterCourse = CourseDiscussionAdapter(
                            conversations,
                            mSharedPref.getString(ID, "").toString(),
                            mSharedPref.getString(FIRSTNAME, "").toString()
                        )

                        CoursesDiscussionRecycleView.adapter = adapterCourse
                        CoursesDiscussionRecycleView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    } else {
                        Log.e("Error from conversation course", "true")
                    }
                    progBarFragMessage.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<Conversation>>, t: Throwable) {
                Log.e("failure conversation course", call.request().toString())
                Log.e("failure conversation course", call.isExecuted.toString())

            }
        })

    }

    private fun loadOwnCoacheConversation() {
        Log.e("User connected", mSharedPref.getString(ID, "").toString())
        apiInterface.getOwnCoacheConversations(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Conversation>> {
            override fun onResponse(
                call: Call<MutableList<Conversation>>, response:
                Response<MutableList<Conversation>>
            ) {


                val conversations = response.body()
                if(isAdded()) {
                    if (conversations != null && conversations.isNotEmpty()) {
                        Log.e("conversations coaches aaaaaaaaaaaaaaaaaaa", conversations.toString())
                        val adapterCoache = CoacheDiscussionAdapter(
                            conversations,
                            mSharedPref.getString(ID, "").toString(),
                            mSharedPref.getString(FIRSTNAME, "").toString()
                        )



                        CoachesDiscussionRecycleView.adapter = adapterCoache
                        CoachesDiscussionRecycleView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                    } else {
                        Log.e("Error from conversation course", "true")
                    }
                    progBarFragMessage.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<Conversation>>, t: Throwable) {
                Log.e("failure conversation course", call.request().toString())
                Log.e("failure conversation course", call.isExecuted.toString())
            }

        })

    }


    private fun loadBubleConversation() {
        Log.e("User connected", mSharedPref.getString(ID, "").toString())
        apiInterface.getOwnCoacheConversations(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Conversation>> {
            override fun onResponse(
                call: Call<MutableList<Conversation>>, response:
                Response<MutableList<Conversation>>
            ) {

                val conversations = response.body()
                if(isAdded()) {
                    if (conversations != null && conversations.isNotEmpty()) {
                        //Log.e("conversations coaches aaaaaaaaaaaaaaaaaaa",conversations.toString())


                        val adapterBuble = BubleMessageAdapter(
                            conversations,
                            mSharedPref.getString(ID, "").toString(),
                            mSharedPref.getString(FIRSTNAME, "").toString()
                        )
                        bubleMessageRecycleView.adapter = adapterBuble
                        bubleMessageRecycleView.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


                    } else {
                        Log.e("Error from conversation course", "true")
                    }
                    pulltorefresh.isRefreshing = false
                    scroll_view.visibility = View.VISIBLE
                    iv_no_connection.visibility = View.GONE
                    progBarFragMessage.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MutableList<Conversation>>, t: Throwable) {
                Log.e("failure conversation course", call.request().toString())
                Log.e("failure conversation course", call.isExecuted.toString())
                if(isAdded()) {
                    pulltorefresh.isRefreshing = false
                    scroll_view.visibility = View.GONE
                    iv_no_connection.visibility = View.VISIBLE
                    progBarFragMessage.visibility = View.GONE
                }

            }
        })

    }

    override fun onResume() {
        if(isAdded()) {
            this.loadOwnConversation()
            this.loadOwnCoacheConversation()
            this.loadBubleConversation()
        }
        super.onResume()
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