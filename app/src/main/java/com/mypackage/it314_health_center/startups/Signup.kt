package com.mypackage.it314_health_center.startups

import android.os.Bundle
import android.renderscript.ScriptGroup.Input
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.mypackage.it314_health_center.R

class Signup : AppCompatActivity() {

     private lateinit var mobileLayout:LinearLayout
     private lateinit var emailLayout:LinearLayout
     private lateinit var radioGroup: RadioGroup
     private lateinit var password:EditText
     private lateinit var confirmPassword:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        radioGroup=findViewById(R.id.radio_grp)
        mobileLayout=findViewById(R.id.mobile_signup_layout)
        emailLayout=findViewById(R.id.email_signup_layout)
        password=findViewById(R.id.password)
        confirmPassword=findViewById(R.id.confirmPassword)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            Log.d("123",checkedId.toString())
            if(checkedId==R.id.radio_email_login)
            {
                emailLayout.visibility= View.VISIBLE
                mobileLayout.visibility=View.GONE
            }
            else{
                emailLayout.visibility= View.GONE
                mobileLayout.visibility=View.VISIBLE
            }

            set_up_editttexts()
        }


    }

    private fun set_up_editttexts() {

    }


}