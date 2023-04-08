package com.mypackage.it314_health_center.startups

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.EmailVerificationAfterSignUp
import com.mypackage.it314_health_center.PatientHomePage
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.helpers.HelperFunctions
import com.poovam.pinedittextfield.PinField
import com.poovam.pinedittextfield.SquarePinField
import java.util.concurrent.TimeUnit


class Signup : AppCompatActivity() {

    private lateinit var mobileLayout: LinearLayout
    private lateinit var emailLayout: LinearLayout
    private lateinit var radioGroup: RadioGroup
    private lateinit var emailText: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var signup: MaterialButton
    private lateinit var dialog: Dialog
    private lateinit var getOtpBtn: MaterialButton
    private lateinit var edtMobile: EditText
    private lateinit var otpView: LinearLayout
    private lateinit var otpPinView: SquarePinField
    private lateinit var verificationId: String

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()
        mdbRef = FirebaseDatabase.getInstance().reference

        // initialize views
        radioGroup = findViewById(R.id.radio_grp)
        mobileLayout = findViewById(R.id.mobile_signup_layout)
        emailLayout = findViewById(R.id.email_signup_layout)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        signup = findViewById(R.id.email_signup_button)
        emailText = findViewById(R.id.email_input)
        mdbRef = FirebaseDatabase.getInstance().reference
        verificationId = "not defined"

        getOtpBtn = findViewById(R.id.getOtpBtn)
        edtMobile = findViewById(R.id.edtMobile)
        otpPinView = findViewById(R.id.otp_edit_text)
        otpView = findViewById(R.id.otp_view)
        dialog = Dialog(this)

        setUp()

    }

    private fun setUp() {

        // when get otp button is clicked, this will get called
        getOtpBtn.setOnClickListener {
            // login with mobile
            dialog.setContentView(R.layout.error_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.findViewById<TextView>(R.id.txt__verify).text =
                getString(R.string.verifyingMessage)
            dialog.show()

            var phone = edtMobile.text.toString()

            // validate phone number
            if (phone.isNotEmpty() && isValidMobile(phone) && phone.length == 10) {

                // if valid, then send code
                edtMobile.clearFocus()
                phone = "+91$phone" // adding country code
                sendVerificationCode(phone)

                // hide keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            } else {

                // show error message if number is invalid
                showErrorMessage("Please enter a valid phone number")
                edtMobile.requestFocus() // focusing edittext

            }
        }

        // when 6 digits otp is entered , this will get called
        otpPinView.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {

                if (TextUtils.isEmpty(enteredText)) {

                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(this@Signup, "Please enter OTP", Toast.LENGTH_SHORT).show()

                } else {

                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(enteredText)

                }

                return true
            }
        }

        // when signup button is clicked
        // this is for email signup
        signup.setOnClickListener {

            dialog.setContentView(R.layout.error_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            // validate emails and passwords
            val email = emailText.text.toString().trim()
            val passwordText = password.text.toString()
            val confirmPasswordText = confirmPassword.text.toString()
            if (passwordText != confirmPasswordText) {
                showErrorMessage("password and confirm password are not matching.Please enter correct details")
                return@setOnClickListener
            }
            if (passwordText.length < 6) {
                showErrorMessage("password should be at least 6 characters long.Please enter a valid password.")
                return@setOnClickListener
            }

            // create a new user
            mAuth.createUserWithEmailAndPassword(email, passwordText)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        val user = mAuth.currentUser
                        if (user != null) {

                            // send verification email
                            mAuth.signOut()
                            user.sendEmailVerification().addOnSuccessListener {

                                // redirect
                                startActivity(
                                    Intent(
                                        this,
                                        EmailVerificationAfterSignUp::class.java
                                    )
                                )
                                finish()

                            }
                        }
                    } else {

                        // find what's wrong
                        try {
                            throw it.exception!!
                        } catch (malformedEmail: FirebaseAuthInvalidCredentialsException) {
                            showErrorMessage("The email entered is not a valid email id.Please check the email you have entered")
                        } catch (existEmail: FirebaseAuthUserCollisionException) {

                            showErrorMessage("An account already exists with this email id.Please check the email you have entered")
                        } catch (e: Exception) {
                            showErrorMessage("Cannot connect to the internet.Please check your connection")
                        }
                    }

                }
        }

        // setup password views
        HelperFunctions.setPasswordView(password)
        HelperFunctions.setPasswordView(confirmPassword)

        // email and password login
        radioGroup.setOnCheckedChangeListener { _, checkedId ->

            Log.d("123", checkedId.toString())

            if (checkedId == R.id.radio_email_login) {
                emailLayout.visibility = View.VISIBLE
                mobileLayout.visibility = View.GONE
            } else {
                emailLayout.visibility = View.GONE
                mobileLayout.visibility = View.VISIBLE
            }

        }

    }

    private fun showErrorMessage(msg: String) {
        dialog.findViewById<LinearLayout>(R.id.loading).visibility = View.GONE
        dialog.findViewById<LinearLayout>(R.id.error_view).visibility = View.VISIBLE
        dialog.findViewById<TextView>(R.id.error_message).text = msg
        dialog.findViewById<MaterialButton>(R.id.close_button).setOnClickListener {
            dialog.dismiss()
        }
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

        val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
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
                        otpPinView.setText(code)

                        // after setting this code
                        // to OTP edittext field we
                        // are calling our verify code method.
                        verifyCode(code)
                    }
                    Toast.makeText(this@Signup, "verification done", Toast.LENGTH_SHORT).show()
                }

                // this method is called when firebase doesn't
                // sends our OTP code due to any error or issue.
                override fun onVerificationFailed(e: FirebaseException) {

                    dialog.setContentView(R.layout.error_layout)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                    showErrorMessage("Invalid mobile number entered. Please enter a valid mobile number")

                }

                override fun onCodeSent(
                    s: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(s, forceResendingToken)

                    // when we receive the OTP it
                    // contains a unique id which
                    // we are storing in our string
                    // which we have already created.
                    verificationId = s

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verify code method.
                    dialog.dismiss()
                    otpView.visibility = View.VISIBLE
                    getOtpBtn.text = getString(R.string.resend_otp)

                    Toast.makeText(this@Signup, "OTP sent", Toast.LENGTH_LONG).show()

                    // focus keyboard after one second
                    Handler(Looper.getMainLooper()).postDelayed({
                        otpPinView.isFocusableInTouchMode = true
                        otpPinView.requestFocus()
                        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                            .showSoftInput(otpPinView, InputMethodManager.SHOW_IMPLICIT)
                    }, 1000)
                }
            }

        val options = PhoneAuthOptions.newBuilder(mAuth)
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
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // if the code is correct and the task is successful
                    // we are sending our user to new activity.
                    Log.d("TAG", "signInWithCredential:success")
                    val i = Intent(this, PatientHomePage::class.java)
                    startActivity(i)
                    finish()

                } else {

                    // if the code is not correct then we are
                    // displaying an error message to the user.
                    otpPinView.setText("")
                    showErrorMessage(task.exception?.message.toString())
                    //    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

}