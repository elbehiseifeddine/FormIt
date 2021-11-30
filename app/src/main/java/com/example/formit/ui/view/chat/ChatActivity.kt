package com.example.formit.ui.view.chat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Message
import com.example.formit.data.repository.ApiInterface
import com.example.formit.ui.adapter.CourseDiscussionAdapter
import com.example.formit.ui.adapter.MessageAdapter
import com.example.formit.ui.view.activitys.FIRSTNAME
import com.example.formit.ui.view.activitys.ID
import com.example.formit.ui.view.activitys.PREF_NAME
import kotlinx.android.synthetic.main.activity_sign_in_up.*
import kotlinx.android.synthetic.main.fragment_messages.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : AppCompatActivity(), TextWatcher {

    val apiInterface = ApiInterface.create()
    lateinit var mSharedPref: SharedPreferences
    private var name: String? = null
    private var idUser: String? = null
    private var CourseName: String? = null
    private var idConversation: String? = null
    private var webSocket: WebSocket? = null
    private val SERVER_PATH = "ws://192.168.1.6:3000"
    private var messageEdit: EditText? = null
    private var sendBtn: View? = null
    private var pickImgBtn: View? = null
    private var recyclerView: RecyclerView? = null
    private val IMAGE_REQUEST_ID = 1
    private var messageAdapter: MessageAdapter? = null
    //private var CourseDiscussion :CourseDiscussionAdapter?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        name = intent.getStringExtra("name")
        idUser = intent.getStringExtra("idUser")
        CourseName = intent.getStringExtra("CourseName")

        idConversation = intent.getStringExtra("idConversation")

        toolbar_title.text = CourseName

        button_Right.visibility=View.GONE
        btn_reus_back.visibility=View.VISIBLE

        btn_reus_back.setOnClickListener{
            finish()
        }
        initiateSocketConnection()
    }

    private fun initiateSocketConnection() {
        val client = OkHttpClient()
        val request = Request.Builder().url(SERVER_PATH).build()
        webSocket = client.newWebSocket(request, SocketListener())
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(s: Editable) {
        val string = s.toString().trim { it <= ' ' }
        if (string.isEmpty()) {
            resetMessageEdit()
        } else {
            sendBtn!!.visibility = View.VISIBLE
            pickImgBtn!!.visibility = View.INVISIBLE
        }
    }

    private fun resetMessageEdit() {
        messageEdit!!.removeTextChangedListener(this)
        messageEdit!!.setText("")
        sendBtn!!.visibility = View.INVISIBLE
        pickImgBtn!!.visibility = View.VISIBLE
        messageEdit!!.addTextChangedListener(this)
    }

    private inner class SocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            runOnUiThread {
                Toast.makeText(
                    this@ChatActivity,
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
            runOnUiThread {
                try {
                    Log.e("(----------------------------------------------------)","-----------------------")
                    Log.e("text from onmessage",text)
                    Log.e("(----------------------------------------------------)","-----------------------")
                    var id = text.subSequence(text.indexOf("id=",0,false)+3,text.indexOf(",",0,false))
                    Log.e("idMessage",id.toString())

                    apiInterface.getMessageById(id.toString()).enqueue(object :
                        Callback<Message>{
                        override fun onResponse(
                            call: Call<Message>, response:
                            retrofit2.Response<Message>
                        ) {

                            val messages = response.body()
                            if (messages != null) {

                                //CourseDiscussion?.update(messages)
                                messageAdapter?.addItem(messages)
                                messageAdapter?.getItemCount()?.minus(1)?.let {
                                    recyclerView!!.smoothScrollToPosition(
                                        it
                                    )
                                }

                            } else {
                                Log.e("Error from message chat Activity","true")
                            }
                        }

                        override fun onFailure(call: Call<Message>, t: Throwable) {
                            Log.e("failure message send chat Activity","true")
                        }
                    })


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun initializeView() {
        messageEdit = findViewById(R.id.messageEdit)
        sendBtn = findViewById(R.id.sendBtn)
        pickImgBtn = findViewById(R.id.pickImgBtn)
        recyclerView = findViewById(R.id.recyclerView)
        Log.e("-", "---------------retrieve message---------------")
        Log.e("-", "-------------------------------------------")

        retrieveMessage()
        messageEdit!!.addTextChangedListener(this)
        sendBtn?.setOnClickListener(View.OnClickListener { v: View? ->
            try {

                val map: HashMap<String, String> = HashMap()
                map["message"] = messageEdit?.getText().toString()
                apiInterface.sendMessages(idConversation,idUser,messageEdit?.getText().toString()).enqueue(object :
                    Callback<Message>{
                    override fun onResponse(
                        call: Call<Message>, response:
                        retrofit2.Response<Message>
                    ) {
                        Log.e("++++++++++++++++++++++call request ",call.request().toString())
                        val messages = response.body()
                        if (messages != null) {

                            Log.e("+++++++++++++++++++++++message from send message",messages.toString())
                            messageAdapter?.addItem(messages)

                            messageAdapter?.getItemCount()?.minus(1)?.let {
                                recyclerView?.smoothScrollToPosition(
                                    it
                                )
                            }
                            webSocket!!.send(messages.toString())
                        } else {
                            Log.e(" Error from message chat Activity","true")
                        }
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.e("failure message sending chat Activity","true")
                    }
                })

                resetMessageEdit()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        })
        pickImgBtn?.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(
                Intent.createChooser(intent, "Pick image"),
                IMAGE_REQUEST_ID
            )
        })
    }



    private fun retrieveMessage(){
       Log.e("--------------------conversation id ",idConversation.toString())
        apiInterface.getConversationMessages(idConversation.toString()).enqueue(object :
            Callback<MutableList<Message>> {
            override fun onResponse(
                call: Call<MutableList<Message>>, response:
                retrofit2.Response<MutableList<Message>>
            ) {
                val messages = response.body()
                if (messages != null) {
                    Log.e("---------------------------Messages",messages.toString())

                    messageAdapter = MessageAdapter(layoutInflater,messages as List<Message>,mSharedPref.getString(ID, "").toString(),mSharedPref.getString(FIRSTNAME, "").toString())
                    recyclerView!!.setAdapter(messageAdapter)
                    recyclerView!!.setLayoutManager(LinearLayoutManager(this@ChatActivity))
                } else {
                    Log.e("------------Error from message chat Activity","true")
                }
            }

            override fun onFailure(call: Call<MutableList<Message>>, t: Throwable) {
                Log.e("------------failure message chat Activity","true")
            }
        })

    }
}