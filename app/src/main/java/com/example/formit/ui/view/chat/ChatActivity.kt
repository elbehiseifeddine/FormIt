package com.example.formit.ui.view.chat

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.ui.adapter.MessageAdapter
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


class chatActivity : AppCompatActivity(), TextWatcher {
    private var name: String? = null
    private var webSocket: WebSocket? = null
    private val SERVER_PATH = "ws://192.168.1.15:3000"
    private var messageEdit: EditText? = null
    private var sendBtn: View? = null
    private var pickImgBtn: View? = null
    private var recyclerView: RecyclerView? = null
    private val IMAGE_REQUEST_ID = 1
    private var messageAdapter: MessageAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        name = intent.getStringExtra("name")
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
                    this@chatActivity,
                    "Socket Connection Successful!",
                    Toast.LENGTH_SHORT
                ).show()
                initializeView()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            runOnUiThread {
                try {
                    val jsonObject = JSONObject(text)
                    jsonObject.put("isSent", false)
                    messageAdapter?.addItem(jsonObject)
                    messageAdapter?.getItemCount()?.minus(1)?.let {
                        recyclerView!!.smoothScrollToPosition(
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
        messageEdit = findViewById(R.id.messageEdit)
        sendBtn = findViewById(R.id.sendBtn)
        pickImgBtn = findViewById(R.id.pickImgBtn)
        recyclerView = findViewById(R.id.recyclerView)
        messageAdapter = MessageAdapter(layoutInflater)
        recyclerView!!.setAdapter(messageAdapter)
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        messageEdit!!.addTextChangedListener(this)
        sendBtn?.setOnClickListener(View.OnClickListener { v: View? ->
            val jsonObject = JSONObject()
            try {
                jsonObject.put("name", name)
                jsonObject.put("message", messageEdit?.getText().toString())
                webSocket!!.send(jsonObject.toString())
                jsonObject.put("isSent", true)
                messageAdapter?.addItem(jsonObject)
                messageAdapter?.getItemCount()?.minus(1)?.let {
                    recyclerView?.smoothScrollToPosition(
                        it
                    )
                }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK) {
            try {
                val `is` = contentResolver.openInputStream(data!!.data!!)
                val image = BitmapFactory.decodeStream(`is`)
                sendImage(image)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun sendImage(image: Bitmap) {
        val outputStream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val base64String = Base64.encodeToString(
            outputStream.toByteArray(),
            Base64.DEFAULT
        )
        val jsonObject = JSONObject()
        try {
            jsonObject.put("name", name)
            jsonObject.put("image", base64String)
            webSocket!!.send(jsonObject.toString())
            jsonObject.put("isSent", true)
            messageAdapter?.addItem(jsonObject)
            messageAdapter?.getItemCount()?.minus(1)?.let { recyclerView!!.smoothScrollToPosition(it) }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}