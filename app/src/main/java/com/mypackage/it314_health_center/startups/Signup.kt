package com.mypackage.it314_health_center.startups

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.LocaleDisplayNames.DialectHandling
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.mypackage.it314_health_center.ActivityHome
import com.mypackage.it314_health_center.EmailVerificationAfterSignUp
import com.mypackage.it314_health_center.R
import java.util.concurrent.TimeUnit


class Signup : AppCompatActivity() {

     private lateinit var mobileLayout:LinearLayout
     private lateinit var emailLayout:LinearLayout
     private lateinit var radioGroup: RadioGroup
     private lateinit var emailText:EditText
     private lateinit var password:EditText
     private lateinit var confirmPassword:EditText
     private lateinit var signup:MaterialButton
     private lateinit var dialog: Dialog
    private lateinit var getOtpBtn:MaterialButton
    private lateinit var edtMobile: EditText
    private lateinit var edtOtp: EditText
    private lateinit var btnVerify: Button
    private lateinit var verificationId: String
    private lateinit var verifyOtpBtn: MaterialButton
    private lateinit var otpText: TextView
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
        verificationId = "not defined"

        getOtpBtn=findViewById(R.id.getOtpBtn)
        edtMobile = findViewById(R.id.edtMobile)
        edtOtp = findViewById(R.id.edtOtp)
        verifyOtpBtn = findViewById(R.id.verifyOtpBtn)
        otpText = findViewById(R.id.otpText)

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
                show_error("Please enter OTP")
            } else {
                // if OTP field is not empty calling
                // method to verify the OTP.
                verifyCode(edtOtp.text.toString())
            }

        })
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

    // check if email is valid
    private fun isValidMail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // check if mobile is valid
    private fun isValidMobile(phone: String): Boolean {
        return Patterns.PHONE.matcher(phone).matches()
    }


    private fun sendVerificationCode(number: String) {
        // this method is used for getting
        // OTP on user phone number.
        // initializing our callbacks for on
        // verification callback method.

        val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
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
                show_error(e.message.toString())
//                Log.d("TAG", "onVerificationFailed: ${e.message}")
            }

            override fun onCodeSent(s: String, forceResendingToken: PhoneAuthProvider.ForceResendingToken) {
//                    super.onCodeSent(s, forceResendingToken)
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
                verificationId = s


                // after setting this code
                // to OTP edittext field we
                // are calling our verify code method.

                Toast.makeText(this@Signup, "OTP sent", Toast.LENGTH_LONG).show()
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
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    Log.d("TAG", "signInWithCredential:success")
                    val i = Intent(this, Login::class.java)
                    startActivity(i)
                    finish()
                } else {
                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    show_error(task.exception?.message.toString())
//                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}