package com.mypackage.it314_health_center.startups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.google.android.material.button.MaterialButton
import com.mypackage.it314_health_center.R

class Login : AppCompatActivity() {

    private lateinit var radiogroup:RadioGroup
    private lateinit var emailLayout:LinearLayout
    private lateinit var mobileLayout:LinearLayout
    private lateinit var emailLoginButton:MaterialButton
    private lateinit var mobileLoginButton:MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailLayout=findViewById(R.id.email_login_layout)
        mobileLayout=findViewById(R.id.mobile_login_layout)
        emailLoginButton=findViewById(R.id.email_login_button)
        radiogroup=findViewById(R.id.radio_grp)
        val user_Types=resources.getStringArray(R.array.user_types)

        val array_adapter=ArrayAdapter<String>(this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,user_Types)

        val autoCompleteview=findViewById <AutoCompleteTextView>(R.id.user_type_selector)

        autoCompleteview.setAdapter(array_adapter)
        autoCompleteview.setSelection(0)
        radiogroup.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.radio_mobile_login)
            {
                emailLayout.visibility=View.GONE
                mobileLayout.visibility=View.VISIBLE
            }
            else{
                emailLayout.visibility=View.VISIBLE
                mobileLayout.visibility= View.GONE
            }
        }

        emailLoginButton.setOnClickListener {
            // login with email

        }


    }

}