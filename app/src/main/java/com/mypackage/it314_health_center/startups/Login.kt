package com.mypackage.it314_health_center.startups

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.mypackage.it314_health_center.ActivityHome
import com.mypackage.it314_health_center.R
import java.security.KeyStore.TrustedCertificateEntry
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance();

        emailLayout=findViewById(R.id.email_login_layout)
        mobileLayout=findViewById(R.id.mobile_login_layout)
        emailLoginButton=findViewById(R.id.email_login_button)
        getOtpBtn=findViewById(R.id.getOtpBtn)
        radiogroup=findViewById(R.id.radio_grp)
        edtMobile = findViewById(R.id.edtMobile)
        edtOtp = findViewById(R.id.edtOtp)
        verifyOtpBtn = findViewById(R.id.verifyOtpBtn)
        otpText = findViewById(R.id.otpText)

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
                edtMobile.error = "Please enter valid mobile number"
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
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, Signup::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If log in fails, display a message to the user.
                    Toast.makeText(this,"User does not exist", Toast.LENGTH_LONG).show()
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
                    // displaying error message with firebase exception.
                    Toast.makeText(this@Login,e.message,Toast.LENGTH_LONG).show()
                    Log.d("TAG", "onVerificationFailed: ${e.message}")
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
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    Log.d("TAG", "signInWithCredential:success")
                    val i = Intent(this, Signup::class.java)
                    startActivity(i)
                    finish()
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    Toast.makeText(this, task.exception?.message,Toast.LENGTH_LONG).show()
                }
            }
    }


}