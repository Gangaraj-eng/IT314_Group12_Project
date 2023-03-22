package com.mypackage.it314_health_center.startups

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.EmailNotVerified
import com.mypackage.it314_health_center.MainActivity
import com.mypackage.it314_health_center.R
import java.util.concurrent.TimeUnit


class Login : AppCompatActivity() {

    private lateinit var radiogroup:RadioGroup
    private lateinit var emailLayout:LinearLayout
    private lateinit var mobileLayout:LinearLayout
    private lateinit var emailLoginButton:MaterialButton
    private lateinit var getOtpBtn:MaterialButton
    private lateinit var edtMobile: EditText
    private lateinit var edtOtp: EditText
    private lateinit var btnVerify: Button
    private lateinit var verificationId: String
    private var mAuth: FirebaseAuth? = null
    private lateinit var verifyOtpBtn: MaterialButton
    private lateinit var otpText: TextView
    private lateinit var dialog: Dialog
    private lateinit var mdbref: DatabaseReference
    private lateinit var signupText: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance();
        if(mAuth!!.currentUser!=null)
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
         mdbref= FirebaseDatabase.getInstance().reference
        emailLayout=findViewById(R.id.email_login_layout)
        mobileLayout=findViewById(R.id.mobile_login_layout)
        emailLoginButton=findViewById(R.id.email_login_button)
        getOtpBtn=findViewById(R.id.getOtpBtn)
        radiogroup=findViewById(R.id.radio_grp)
        edtMobile = findViewById(R.id.edtMobile)
        edtOtp = findViewById(R.id.edtOtp)
        verifyOtpBtn = findViewById(R.id.verifyOtpBtn)
        otpText = findViewById(R.id.otpText)
        signupText = findViewById(R.id.signupText)
        dialog = Dialog(this)

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
            dialog.setContentView(R.layout.error_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
            val email=findViewById<EditText>(R.id.email_input).text.toString()
            val password=findViewById<EditText>(R.id.password_input).text.toString()
            if(!isValidMail(email))
            {
                show_error("Email entered is invalid.Please enter a valid email")
            }
            login(email,password)
        }

        val password=findViewById<EditText>(R.id.password_input)
        password.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= password.getRight() - password.getCompoundDrawables()
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {

                    if (password.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                        password.inputType = InputType.TYPE_CLASS_TEXT
                        password.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_eye_close,
                            0
                        )
                    } else {
                        password.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        password.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_eye_open,
                            0
                        )
                    }
                    // your action here
                    return@OnTouchListener true
                }
            }
            false
        })

        getOtpBtn.setOnClickListener {
            // login with mobile


            val phone = "+91" + edtMobile.text.toString()

            if(isValidMobile(phone)){
                // send otp to mobile
                otpText.visibility = View.VISIBLE
                edtOtp.visibility = View.VISIBLE
                verifyOtpBtn.visibility = View.VISIBLE
                sendVerificationCode(phone)
            }
            else{
                show_error("Please enter a valid phone number")
                edtMobile.requestFocus()
            }
        }

        verifyOtpBtn.setOnClickListener(View.OnClickListener {
            // validating if the OTP text field is empty or not.

                if (TextUtils.isEmpty(edtOtp.text.toString())) {
                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
                } else {
                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(edtOtp.text.toString())
                }

        })

        signupText.setOnClickListener {
            // go to signup page
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
            
        }
    }

    // check if email is valid
    private fun isValidMail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // check if mobile is valid
    private fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }

    private fun login(email: String, password: String) {
        //code for logging in the user
        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val user= mAuth!!.currentUser
                    if (user != null) {
                        if(user.isEmailVerified)
                        {
                            mdbref.child("users").child(user.uid)
                                .child("user_details").get().addOnSuccessListener {
                                    if(it.exists())
                                    {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                                    }
                                    else{
                                        val intent = Intent(this, user_details_activity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                        }
                        else{
                            mAuth!!.signOut()
                            val intent = Intent(this, EmailNotVerified::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                } else {
                    // If log in fails, display a message to the user.
                    show_error("Invalid credentials entered. Please enter valid credentials")
                }
            }
    }

    private fun sendVerificationCode(number: String) {
        // this method is used for getting
        // OTP on user phone number.
        // initializing our callbacks for on
        // verification callback method.

        val mCallBack: OnVerificationStateChangedCallbacks = object : OnVerificationStateChangedCallbacks() {
            // below method is used when
            // OTP is sent from Firebase

            // this method is called when user
            // receive OTP from Firebase.
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                val code = phoneAuthCredential.smsCode

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                    edtOtp.setText(code)

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verify code method.
                    verifyCode(code)
                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            override fun onVerificationFailed(e: FirebaseException) {
                dialog.setContentView(R.layout.error_layout)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                show_error("Invalid mobile number entered. Please enter a vailid mobile number")
            }

            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
//                    super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s


                // after setting this code
                // to OTP edittext field we
                // are calling our verify code method.

                Toast.makeText(this@Login, "OTP sent", Toast.LENGTH_LONG).show()
            }
        }

        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(mCallBack) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyCode(code: String) {
        dialog.setContentView(R.layout.error_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        // below line is used for getting
        // credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(verificationId, code)

        // after getting credential we are
        // calling up in method.
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                   val user=mAuth!!.currentUser
                    if(user!=null)
                    {
                        mdbref.child("users").child(user.uid)
                            .child("user_details").get()
                            .addOnSuccessListener {
                                if(it.exists())
                                {
                                    val i = Intent(this, Signup::class.java)
                                    startActivity(i)
                                    finish()
                                }
                                else{
                                    val i = Intent(this, user_details_activity::class.java)
                                    startActivity(i)
                                    finish()
                                }
                            }
                    }

                } else {
                    show_error("You have entered wrong OTP. Please enter correct otp sent to your device or click on resend otp")
                }
            }
    }

    private fun show_error(msg:String)
    {
        dialog.findViewById<LinearLayout>(R.id.loading).visibility=View.GONE
        dialog.findViewById<LinearLayout>(R.id.error_view).visibility=View.VISIBLE
        dialog.findViewById<TextView>(R.id.failed_title).text="Login failed"
        dialog.findViewById<TextView>(R.id.error_message).text=msg
        dialog.findViewById<MaterialButton>(R.id.close_button).setOnClickListener {
            dialog.dismiss()
        }
    }

}