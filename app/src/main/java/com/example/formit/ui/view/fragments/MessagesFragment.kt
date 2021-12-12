package com.example.formit.ui.view.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.formit.R
import com.example.formit.data.model.*
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.BubleMessageAdapter
import com.example.formit.ui.adapter.CoacheDiscussionAdapter
import com.example.formit.ui.adapter.CourseDiscussionAdapter
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.view.activitys.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MessagesFragment: Fragment() {

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
        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (mSharedPref.getString(ROLE,"").equals("coache")){
            updateTextWithRole.text="My Students"
        }
        else{
            updateTextWithRole.text="Coaches"
        }

        /*activity?.runOnUiThread{
            this.loadOwnConversation()
        }*/
        this.loadOwnConversation()
        this.loadOwnCoacheConversation()
        btn_reus_back.visibility=View.INVISIBLE

        /*activity?.runOnUiThread{
            val BubleList = mutableListOf(
                Buble_Message(R.drawable.test1,"ahmed",true),
                Buble_Message(R.drawable.test2,"Seif",false),
                Buble_Message(R.drawable.test3,"ahmedSeif",false),

                )
            val adapterBuble =BubleMessageAdapter(BubleList)
            bubleMessageRecycleView.adapter = adapterBuble
            bubleMessageRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }*/

        val BubleList = mutableListOf(
            Buble_Message(R.drawable.test1,"ahmed",true),
            Buble_Message(R.drawable.test2,"Seif",false),
            Buble_Message(R.drawable.test3,"ahmedSeif",false),

            )
        val adapterBuble =BubleMessageAdapter(BubleList)
        /*bubleMessageRecycleView.adapter = adapterBuble
        bubleMessageRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
*/


        Log.e("id user", mSharedPref.getString(ID, "").toString())


     /*  activity?.runOnUiThread {
           val CoacheDiscussion = mutableListOf(
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

       }*/

        /*val CoacheDiscussion = mutableListOf(
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
*/
    }

    private fun loadOwnConversation(){
        Log.e("User connected",mSharedPref.getString(ID, "").toString())
        apiInterface.getOwnConversations(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Conversation>> {
            override fun onResponse(
                call: Call<MutableList<Conversation>>, response:
                Response<MutableList<Conversation>>
            ) {
                val conversations = response.body()
                if (conversations != null) {
                    Log.e("conversations",conversations.toString())
                    val adapterCourse =CourseDiscussionAdapter(conversations,
                        mSharedPref.getString(ID, "").toString(),
                        mSharedPref.getString(FIRSTNAME, "").toString())

                    CoursesDiscussionRecycleView.adapter = adapterCourse
                    CoursesDiscussionRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

                } else {
                    Log.e("Error from conversation course","true")
                }
            }

            override fun onFailure(call: Call<MutableList<Conversation>>, t: Throwable) {
                Log.e("failure conversation course",call.request().toString())
                Log.e("failure conversation course",call.isExecuted.toString())
            }
        })

    }

    private fun loadOwnCoacheConversation(){
        Log.e("User connected",mSharedPref.getString(ID, "").toString())
        apiInterface.getOwnCoacheConversations(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Conversation>> {
            override fun onResponse(
                call: Call<MutableList<Conversation>>, response:
                Response<MutableList<Conversation>>
            ) {
                val conversations = response.body()
                if (conversations != null) {
                    Log.e("conversations coaches aaaaaaaaaaaaaaaaaaa",conversations.toString())
                    val adapterCoache =CoacheDiscussionAdapter(conversations,
                        mSharedPref.getString(ID, "").toString(),
                        mSharedPref.getString(FIRSTNAME, "").toString())



                    CoachesDiscussionRecycleView.adapter = adapterCoache
                    CoachesDiscussionRecycleView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

                } else {
                    Log.e("Error from conversation course","true")
                }
            }

            override fun onFailure(call: Call<MutableList<Conversation>>, t: Throwable) {
                Log.e("failure conversation course",call.request().toString())
                Log.e("failure conversation course",call.isExecuted.toString())
            }
        })

    }

    override fun onResume() {

        this.loadOwnConversation()
        this.loadOwnCoacheConversation()
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