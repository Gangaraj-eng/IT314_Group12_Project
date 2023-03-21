package com.mypackage.it314_health_center.startups

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.LocaleDisplayNames.DialectHandling
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.mypackage.it314_health_center.EmailVerificationAfterSignUp
import com.mypackage.it314_health_center.R


class Signup : AppCompatActivity() {

     private lateinit var mobileLayout:LinearLayout
     private lateinit var emailLayout:LinearLayout
     private lateinit var radioGroup: RadioGroup
     private lateinit var emailText:EditText
     private lateinit var password:EditText
     private lateinit var confirmPassword:EditText
     private lateinit var signup:MaterialButton
     private lateinit var dialog: Dialog
     private var mAuth=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        radioGroup=findViewById(R.id.radio_grp)
        mobileLayout=findViewById(R.id.mobile_signup_layout)
        emailLayout=findViewById(R.id.email_signup_layout)
        password=findViewById(R.id.password)
        confirmPassword=findViewById(R.id.confirmPassword)
        signup=findViewById(R.id.email_signup_button)
        emailText=findViewById(R.id.email_input)
        dialog= Dialog(this)
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

        signup.setOnClickListener {
            dialog.setContentView(R.layout.error_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
                val email=emailText.text.toString().trim()
                val passwordText=password.text.toString()
                val confirmPasswordText=confirmPassword.text.toString()
            if(passwordText!=confirmPasswordText)
            {
                show_error("password and confirm password are not matching.Please enter correct deails")
                return@setOnClickListener
            }
            if(passwordText.length<3)
            {
                show_error("password should be atleast 3 characters long.Please enter a valid password.")
                return@setOnClickListener
            }
            mAuth.createUserWithEmailAndPassword(email,passwordText)
                    .addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                                val user=mAuth.currentUser
                                if(user!=null)
                                {
                                    user.sendEmailVerification().addOnSuccessListener {
                                        startActivity(Intent(this,EmailVerificationAfterSignUp::class.java))
                                    }
                                }
                        }
                        else {
                            try {
                                throw it.exception!!
                            }
                            catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                                show_error("The email entered is not a valid email id.Please check the email you have entered")
                            } catch (existEmail: FirebaseAuthUserCollisionException) {

                                show_error("An account already exists with this email id.Please check the email you have entered")
                            } catch (e: Exception) {
                                show_error("Cannot connect to the internet.Please check your connection")
                            }
                        }

                    }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun set_up_editttexts() {

        password.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= password.getRight() - password.getCompoundDrawables()
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {

                    if(password.inputType==InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                    {
                        password.inputType=InputType.TYPE_CLASS_TEXT
                        password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_close,0)
                    }
                    else{
                        password.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_open,0)
                    }
                    // your action here
                    return@OnTouchListener true
                }
            }
            false
        })

        confirmPassword.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= confirmPassword.getRight() - confirmPassword.getCompoundDrawables()
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {

                    if(confirmPassword.inputType==InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                    {
                        confirmPassword.inputType=InputType.TYPE_CLASS_TEXT
                        confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_close,0)
                    }
                    else{
                        confirmPassword.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        confirmPassword.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_open,0)
                    }
                    // your action here
                    return@OnTouchListener true
                }
            }
            false
        })

    }

    private fun show_error(msg:String)
    {
            dialog.findViewById<LinearLayout>(R.id.loading).visibility=View.GONE
        dialog.findViewById<LinearLayout>(R.id.error_view).visibility=View.VISIBLE
        dialog.findViewById<TextView>(R.id.error_message).text=msg
        dialog.findViewById<MaterialButton>(R.id.close_button).setOnClickListener {
            dialog.dismiss()
        }
    }

}