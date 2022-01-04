package com.example.formit.ui.view.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.formit.R
import com.example.formit.data.model.Course
import com.example.formit.data.model.Event
import com.example.formit.data.model.User
import com.example.formit.ui.adapter.HomeCouseAdapter
import com.example.formit.ui.adapter.HomeEventAdapter
import com.example.formit.ui.view.activitys.*
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var mSharedPref: SharedPreferences
    lateinit var EditBirthdate: TextInputEditText
    private var profilePic: ImageView? = null
    private var selectedImageUri: Uri? = null
    lateinit var storage: FirebaseStorage
    private val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val now = Date()
    val fileName: String = formater.format(now)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storage = Firebase.storage
        toolbar_title.text = "Profile"

        button_Right.setBackgroundResource(R.drawable.ic_logout)
        btn_ProfileAchievements.setOnClickListener {
            Intent(context, AchievementActivity::class.java).also {
                startActivity(it)
            }
        }
        button_Right.setOnClickListener {
            Log.e("logout pressed", "true")

            val dialogBuilder = AlertDialog.Builder(it.context)
            dialogBuilder.setMessage(R.string.logoutMessage)
                // if the dialog is cancelable
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                    activity?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
                        ?.edit()
                        ?.clear()?.apply()
                    Intent(context, SignInUpActivity::class.java).also {
                        startActivity(it)
                        activity?.finish()
                    }
                })

            dialogBuilder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss()
            }
            dialogBuilder.create().show()


        }
        btn_reus_back.visibility = View.GONE

        mSharedPref = view.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (mSharedPref.getString(ROLE, "").toString() == "coache") {
            button_Right.setBackgroundResource(R.drawable.ic_logout)
            btn_ProfileAchievements.setOnClickListener {
                Intent(context, AchievementActivity::class.java).also {
                    startActivity(it)
                }
            }
            button_Right.setOnClickListener {
                Log.e("logout pressed", "true")

                val dialogBuilder = AlertDialog.Builder(it.context)
                dialogBuilder.setMessage(R.string.logoutMessage)
                    // if the dialog is cancelable
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                        activity?.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
                            ?.edit()
                            ?.clear()?.apply()
                        Intent(context, SignInUpActivity::class.java).also {
                            startActivity(it)
                            activity?.finish()
                        }
                    })

                dialogBuilder.setNegativeButton("No") { dialogInterface, which ->
                    dialogInterface.dismiss()
                }
                dialogBuilder.create().show()


            }
            btn_reus_back.visibility = View.GONE
           profileStudentLayout.visibility=View.GONE
            profileCoacheLayout.visibility=View.VISIBLE



            EditBirthdate = requireActivity().findViewById(R.id.ti_EditBirthdateCoache)



            CoacheProfilePicture!!.setOnClickListener {
                openGallery()
            }

            btn_Upload_imageCoache!!.setOnClickListener {
                uploadImage()
            }

            val birthDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select start date")
                .build()
            birthDatePicker.addOnPositiveButtonClickListener {
                EditBirthdate.setText(birthDatePicker.headerText.toString())
            }

            EditBirthdate.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus){
                    birthDatePicker.show(requireActivity().supportFragmentManager, "START_DATE")
                }else{
                    birthDatePicker.dismiss()
                }
            }
            if(mSharedPref.getString(PICTURE, "").toString()=="avatar default.png")
            {
                profilePic!!.setImageResource(R.drawable.male_student)
            }
            else
            {
                val filename2 = mSharedPref.getString(PICTURE, "").toString()
                val path =
                    "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F$filename2?alt=media"
                Log.e("*******************************path image ",path)
                Glide.with(this)
                    .load(path)
                    .into(CoacheProfilePicture)
            }
            btn_UpdateCoache.setOnClickListener {

                val map: HashMap<String, String> = HashMap()
                map["email"] = ti_EditEmailCoache.text.toString()
                map["firstname"] = ti_EditFirstNameCoache.text.toString()
                map["lastname"] = ti_EditLastNameCoache.text.toString()
                map["birthdate"] = EditBirthdate.text.toString()
                map["address"] = ti_EditAddressCoache.text.toString()
                map["telnumber"] = ti_EditPhoneNumberCoache.text.toString()
                if (selectedImageUri == null) {
                    map["picture"] = mSharedPref.getString(PICTURE, "").toString()
                }
                else
                    map["picture"] = fileName.toString()
                apiInterface.UpdateCurrentUser(mSharedPref.getString(ID,"").toString(),map).enqueue(object : Callback<User> {
                    override fun onResponse(
                        call: Call<User>, response:
                        Response<User>
                    ) {
                        val user = response.body()
                        if (user !=null) {
                            mSharedPref.edit().apply{
                                putString(EMAIL, ti_EditEmailCoache.text.toString())
                                putString(FIRSTNAME, ti_EditFirstNameCoache.text.toString())
                                putString(LASTNAME, ti_EditLastNameCoache.text.toString())
                                putString(ADDRESS, ti_EditAddressCoache.text.toString())
                                putString(PICTURE, fileName.toString())
                                putString(BIRTHDATE, EditBirthdate.text.toString())
                                putInt(PHONENUMBER, ti_EditPhoneNumberCoache.text.toString().toInt())
                            }.apply()


                        } else {
                            Log.e("Something went wrong","true")
                            Toast.makeText(requireContext(), "Something went wrong !!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("aaaaaaaaaaaaaaaaaaaaaaaa","true")
                        Toast.makeText(requireContext(), "Connexion error!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            ti_EditEmailCoache.setText(mSharedPref.getString(EMAIL,"").toString())
            ti_EditFirstNameCoache.setText(mSharedPref.getString(FIRSTNAME,"").toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            ti_EditLastNameCoache.setText(mSharedPref.getString(LASTNAME,"").toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            ti_EditAddressCoache.setText(mSharedPref.getString(ADDRESS,"").toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            EditBirthdate.setText(mSharedPref.getString(BIRTHDATE,"").toString())
            ti_EditPhoneNumberCoache.setText(mSharedPref.getInt(PHONENUMBER,0).toString())
            tv_FullNameCoache.setText(mSharedPref.getString(FIRSTNAME,"").toString().capitalize()+" "+mSharedPref.getString(LASTNAME,"").toString().capitalize())



        }else {
            profileCoacheLayout.visibility=View.GONE
            profileStudentLayout.visibility=View.VISIBLE
            /*activity?.runOnUiThread{
                LoadUserParticipatedData()
            }*/



            Profile_CourseSeeAll.setOnClickListener {
                Intent(activity, CoursesActivity::class.java).also {
                    startActivity(it)
                }
            }

            Profile_EventsSeeAll.setOnClickListener {
                Intent(activity, CoursesActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        ProfileSettings.setOnClickListener {
            Intent(activity, SettingsActivity::class.java).also {
                startActivity(it)
            }
        }
        if(mSharedPref.getString(PICTURE, "").toString()=="avatar default.png")
        {
            CoacheProfilePicture!!.setImageResource(R.drawable.male_student)
        }
        else
        {
            val filename2 = mSharedPref.getString(PICTURE, "").toString()
            val path =
                "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F$filename2?alt=media"
            Log.e("*******************************path image ",path)
            Glide.with(requireActivity())
                .load(path)
                .into(ProfilePicture)
        }
        tv_ProfileFullName.setText(
            mSharedPref.getString(FIRSTNAME, "").toString() + " " + mSharedPref.getString(LASTNAME,"").toString()
        )
        tv_ProfileEmail.setText(mSharedPref.getString(EMAIL, "").toString())


//        Profile_Participated_events.adapter = adapter
//        Profile_Enrolled_courses.adapter = adapter
//        Profile_Enrolled_courses.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        Profile_Participated_events.layoutManager =
//            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        if(mSharedPref.getInt(XP, 0)<500){
            iv_AchievmentId.setImageResource(R.drawable.ic_bronze)
            tv_Achievement.text="Bronze"
        }else if (mSharedPref.getInt(XP, 0)>=500 && mSharedPref.getInt(XP, 0)<1000){
            iv_AchievmentId.setImageResource(R.drawable.ic_sliver)
            tv_Achievement.text="Silver"
        }else if (mSharedPref.getInt(XP, 0)>=1000){
            iv_AchievmentId.setImageResource(R.drawable.ic_gold)
            tv_Achievement.text="Gold"
        }


        pulltorefresh.setOnRefreshListener {LoadUserParticipatedData()}

    }

    override fun onResume() {
        tv_ProfileFullName.setText(
            mSharedPref.getString(FIRSTNAME, "").toString() + " " + mSharedPref.getString(LASTNAME,"")
        )
        tv_ProfileEmail.setText(mSharedPref.getString(EMAIL, "").toString())
        if(isAdded()) {
            LoadUserParticipatedData()
        }
        super.onResume()
    }

    private fun LoadUserParticipatedData(){
        progBarFragProfile.visibility = View.VISIBLE

        apiInterface.getCoursesParticipated(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Course>> {
            override fun onResponse(
                call: Call<MutableList<Course>>, response:
                Response<MutableList<Course>>
            ) {


                val courses = response.body()
                if(isAdded()) {
                    if (courses != null && courses.isNotEmpty()) {
                        Log.e("coursessssssssssssssssssssss     ", courses.toString())
                        val adapter = HomeCouseAdapter(courses, true)
                        Profile_Enrolled_courses.adapter = adapter
                        Profile_Enrolled_courses.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    } else {
                        Log.e("Username or Password wrong", "true")
                        Profile_Enrolled_courses.visibility = View.GONE
                        tv_NoEnrolledCourses.visibility = View.VISIBLE
                    }

                    progBarFragProfile.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<MutableList<Course>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
            }
        })

        apiInterface.getEventsParticipated(mSharedPref.getString(ID, "")).enqueue(object :
            Callback<MutableList<Event>> {
            override fun onResponse(
                call: Call<MutableList<Event>>, response:
                Response<MutableList<Event>>
            ) {
                val events = response.body()
                if(isAdded()) {
                    if (events != null && events.isNotEmpty()) {
                        Log.e("coursessssssssssssssssssssss     ", events.toString())
                        val adapter = HomeEventAdapter(events, true)
                        Profile_Participated_events.adapter = adapter
                        Profile_Participated_events.layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    } else {
                        Log.e("Username or Password wrong", "true")
                        Profile_Participated_events.visibility = View.GONE
                        tv_NoParticipatedEvents.visibility = View.VISIBLE
                    }
                    pulltorefresh.isRefreshing = false
                    scroll_view.visibility = View.VISIBLE
                    iv_no_connection.visibility = View.GONE
                    progBarFragProfile.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<MutableList<Event>>, t: Throwable) {
                Log.e("aaaaaaaaaaaaaaaaaaaaaaaa", "true")
                if(isAdded()) {
                    pulltorefresh.isRefreshing = false
                    scroll_view.visibility = View.GONE
                    iv_no_connection.visibility = View.VISIBLE
                    progBarFragProfile.visibility = View.GONE
                    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }
                }
        })

    }


    private fun uploadImage()
    {
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Uploading Image ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val storageReference = FirebaseStorage.getInstance().reference.child("images/$fileName")
        storageReference.putFile(selectedImageUri!!).
        addOnSuccessListener {
            CoacheProfilePicture!!.setImageURI(selectedImageUri)
            if(progressDialog.isShowing)
            {
                progressDialog.dismiss()
            }
            Toast.makeText(requireContext(),"Successfuly uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            if(progressDialog.isShowing)
            {
                progressDialog.dismiss()
            }
            Toast.makeText(requireContext(),"Sorry", Toast.LENGTH_SHORT).show()

        }


    }

    private val startForResultOpenGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if(result.resultCode == AppCompatActivity.RESULT_OK)
        {
            selectedImageUri = result.data!!.data
            CoacheProfilePicture!!.setImageURI(selectedImageUri)
        }
    }
    private fun openGallery() {
        val intent = Intent()

        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startForResultOpenGallery.launch(intent)
    }

}