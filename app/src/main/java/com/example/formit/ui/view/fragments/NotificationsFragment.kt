package com.example.formit.ui.view.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.formit.R
import com.example.formit.data.model.Notification
import com.example.formit.ui.adapter.NotificationsAdapter
import com.example.formit.ui.view.activitys.ID
import com.example.formit.ui.view.activitys.PREF_NAME
import com.example.formit.ui.view.activitys.apiInterface
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var colorDrawableBackground: ColorDrawable
    private lateinit var idUser: String
    private lateinit var deleteIcon: Drawable
    lateinit var mSharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSharedPref = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        /*activity?.runOnUiThread {
            LoadNotificationData()
        }*/
        toolbar_title.text = "My Notifications"
        button_Right.visibility = View.GONE
        btn_reus_back.visibility = View.INVISIBLE
        progBarFragNotification.visibility = View.VISIBLE
        pulltorefreshNotif.setOnRefreshListener {
            Log.e("********notificationsssssssssssssssssssssss********", "avvvvvvvvvvvvvvv")
            LoadNotificationData() }
    }

    private fun LoadNotificationData() {
        Log.e("********notifications********", "avvvvvvvvvvvvvvv")

        idUser = mSharedPref.getString(ID, "").toString()
        apiInterface.getUserNotifications(idUser).enqueue(object :
            Callback<MutableList<Notification>> {
            override fun onResponse(
                call: Call<MutableList<Notification>>, response:
                Response<MutableList<Notification>>
            ) {


                val notifications = response.body()

                if(isAdded()) {
                    if (notifications != null && notifications.isNotEmpty()) {
                        Log.e("********notifications********", notifications.toString())
                        val adapter = NotificationsAdapter(notifications, idUser)
                        rv_notification.adapter = adapter
                        rv_notification.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                        pulltorefreshNotif.isRefreshing = false
                        colorDrawableBackground = ColorDrawable(Color.parseColor("#ff0000"))
                        deleteIcon =
                            AppCompatResources.getDrawable(requireContext(), R.drawable.ic_delete)!!
                        rv_notification.apply {
                            //setHasFixedSize(true)
                            //adapter.notifyDataSetChanged()
                            //adapter = viewAdapter
                            //layoutManager = viewManager
                            addItemDecoration(
                                DividerItemDecoration(
                                    this.context,
                                    DividerItemDecoration.VERTICAL
                                )
                            )
                            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                                0,
                                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                            ) {
                                override fun onMove(
                                    recyclerView: RecyclerView,
                                    viewHolder: RecyclerView.ViewHolder,
                                    viewHolder2: RecyclerView.ViewHolder
                                ): Boolean {
                                    return false
                                }

                                override fun onSwiped(
                                    viewHolder: RecyclerView.ViewHolder,
                                    swipeDirection: Int
                                ) {
                                    (adapter as NotificationsAdapter).removeItem(
                                        viewHolder.adapterPosition,
                                        viewHolder as NotificationsAdapter.NotificationViewHolder
                                    )
                                }

                                override fun onChildDraw(
                                    c: Canvas,
                                    recyclerView: RecyclerView,
                                    viewHolder: RecyclerView.ViewHolder,
                                    dX: Float,
                                    dY: Float,
                                    actionState: Int,
                                    isCurrentlyActive: Boolean
                                ) {
                                    val itemView = viewHolder.itemView
                                    val iconMarginVertical =
                                        (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2
                                    if (dX > 0) {
                                        colorDrawableBackground.setBounds(
                                            itemView.left,
                                            itemView.top,
                                            dX.toInt(),
                                            itemView.bottom
                                        )
                                        deleteIcon.setBounds(
                                            itemView.left + iconMarginVertical,
                                            itemView.top + iconMarginVertical,
                                            itemView.left + iconMarginVertical + deleteIcon.intrinsicWidth,
                                            itemView.bottom - iconMarginVertical
                                        )
                                    } else {
                                        colorDrawableBackground.setBounds(
                                            itemView.right + dX.toInt(),
                                            itemView.top,
                                            itemView.right,
                                            itemView.bottom
                                        )
                                        deleteIcon.setBounds(
                                            itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth,
                                            itemView.top + iconMarginVertical,
                                            itemView.right - iconMarginVertical,
                                            itemView.bottom - iconMarginVertical
                                        )
                                        deleteIcon.level = 0
                                    }
                                    colorDrawableBackground.draw(c)
                                    c.save()
                                    if (dX > 0)
                                        c.clipRect(
                                            itemView.left,
                                            itemView.top,
                                            dX.toInt(),
                                            itemView.bottom
                                        )
                                    else
                                        c.clipRect(
                                            itemView.right + dX.toInt(),
                                            itemView.top,
                                            itemView.right,
                                            itemView.bottom
                                        )
                                    deleteIcon.draw(c)
                                    c.restore()
                                    super.onChildDraw(
                                        c,
                                        recyclerView,
                                        viewHolder,
                                        dX,
                                        dY,
                                        actionState,
                                        isCurrentlyActive
                                    )
                                }
                            }
                            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
                            itemTouchHelper.attachToRecyclerView(rv_notification)
                            adapter.notifyDataSetChanged()
                        }


                    } else {
                        Log.e("********notifications******** wrong", "true")
                        rv_notification.visibility = View.GONE
                        tv_NoNotificationYet.visibility = View.VISIBLE
                    }
                    scroll_view.visibility = View.VISIBLE
                    iv_no_connection.visibility = View.GONE
                    progBarFragNotification.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<MutableList<Notification>>, t: Throwable) {
                Log.e("notifaaaaaaaaaaaaaaaaaaaaaaaa", "true")
                if(isAdded()) {
                    pulltorefreshNotif.isRefreshing = false
                    scroll_view.visibility = View.GONE
                    iv_no_connection.visibility = View.VISIBLE
                    progBarFragNotification.visibility = View.GONE
                }

            }
        })
    }
    override fun onResume() {
        if(isAdded()) {
            progBarFragNotification.visibility = View.VISIBLE
            LoadNotificationData()
        }
        super.onResume()
    }
}