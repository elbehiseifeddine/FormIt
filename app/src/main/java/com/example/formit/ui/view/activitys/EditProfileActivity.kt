package com.example.formit.ui.view.activitys

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.formit.R
import com.example.formit.data.model.User
import com.google.android.material.datepicker.MaterialDatePicker
import com.example.formit.data.repository.ApiInterface
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.reusable_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {
    lateinit var mSharedPref: SharedPreferences
    lateinit var EditBirthdate: TextInputEditText
    private var profilePic: ImageView? = null
    private var selectedImageUri: Uri? = null
    lateinit var storage: FirebaseStorage
    val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val now = Date()
    val fileName = formater.format(now)
    override fun onCreate(savedInstanceState: Bundle?) {

        storage = Firebase.storage
        profilePic = findViewById(R.id.ProfilePicture)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        mSharedPref = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        toolbar_title.text="Edit Profile"
        button_Right.visibility= View.GONE

        EditBirthdate = findViewById(R.id.ti_EditBirthdate)

        btn_reus_back.setOnClickListener {
            finish()
        }

        ProfilePicture!!.setOnClickListener {
            openGallery()
        }

        btn_Upload_image!!.setOnClickListener {
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
                birthDatePicker.show(supportFragmentManager, "START_DATE")
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
            val path = "https://firebasestorage.googleapis.com/v0/b/formit-f214c.appspot.com/o/images%2F"+filename2+"?alt=media"
            Log.e("*******************************path image ",path)
            Glide.with(this)
                .load(path)
                .into(ProfilePicture)
        }
        btn_Update.setOnClickListener {

            val map: HashMap<String, String> = HashMap()
            map["email"] = ti_EditEmail.text.toString()
            map["firstname"] = ti_EditFirstName.text.toString()
            map["lastname"] = ti_EditLastName.text.toString()
            map["birthdate"] = EditBirthdate.text.toString()
            map["address"] = ti_EditAddress.text.toString()
            map["telnumber"] = ti_EditPhoneNumber.text.toString()
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
                            putString(EMAIL, ti_EditEmail.text.toString())
                            putString(FIRSTNAME, ti_EditFirstName.text.toString())
                            putString(LASTNAME, ti_EditLastName.text.toString())
                            putString(ADDRESS, ti_EditAddress.text.toString())
                            putString(PICTURE, fileName.toString())
                            putString(BIRTHDATE, EditBirthdate.text.toString())
                            putInt(PHONENUMBER, ti_EditPhoneNumber.text.toString().toInt())
                        }.apply()
                        finish()

                    } else {
                        Log.e("Something went wrong","true")
                        Toast.makeText(this@EditProfileActivity, "Something went wrong !!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("aaaaaaaaaaaaaaaaaaaaaaaa","true")
                    Toast.makeText(this@EditProfileActivity, "Connexion error!", Toast.LENGTH_SHORT).show()
                }
            })
        }
        ti_EditEmail.setText(mSharedPref.getString(EMAIL,"").toString())
        ti_EditFirstName.setText(mSharedPref.getString(FIRSTNAME,"").toString().capitalize())
        ti_EditLastName.setText(mSharedPref.getString(LASTNAME,"").toString().capitalize())
        ti_EditAddress.setText(mSharedPref.getString(ADDRESS,"").toString().capitalize())
        EditBirthdate.setText(mSharedPref.getString(BIRTHDATE,"").toString())
        ti_EditPhoneNumber.setText(mSharedPref.getInt(PHONENUMBER,0).toString())
        tv_FullName.setText(mSharedPref.getString(FIRSTNAME,"").toString().capitalize()+" "+mSharedPref.getString(LASTNAME,"").toString().capitalize())
    }









    private fun uploadImage()
    {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image ...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val storageReference = FirebaseStorage.getInstance().reference.child("images/$fileName")
        storageReference.putFile(selectedImageUri!!).
        addOnSuccessListener {
            profilePic!!.setImageURI(selectedImageUri)
            if(progressDialog.isShowing)
            {
                progressDialog.dismiss()
            }
            Toast.makeText(this,"Successfuly uploaded", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            if(progressDialog.isShowing)
            {
                progressDialog.dismiss()
            }
            Toast.makeText(this,"Sorry", Toast.LENGTH_SHORT).show()

        }


    }

    private val startForResultOpenGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if(result.resultCode == RESULT_OK)
        {
            selectedImageUri = result.data!!.data
            profilePic!!.setImageURI(selectedImageUri)
        }
    }
    private fun openGallery() {
        val intent = Intent()

        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startForResultOpenGallery.launch(intent)
    }
}