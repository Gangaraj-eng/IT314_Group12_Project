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
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import com.mypackage.it314_health_center.Doctor_Homepage
import com.mypackage.it314_health_center.EmailNotVerified
import com.mypackage.it314_health_center.PatientHomePage
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.helpers.HelperFunctions
import com.mypackage.it314_health_center.helpers.dbPaths
import com.poovam.pinedittextfield.PinField
import com.poovam.pinedittextfield.SquarePinField
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern


class Login : AppCompatActivity() {

    // views that we are going to use
    private lateinit var radioGroup: RadioGroup
    private lateinit var emailLayout: LinearLayout
    private lateinit var mobileLayout: LinearLayout
    private lateinit var emailLoginButton: MaterialButton
    private lateinit var getOtpBtn: MaterialButton
    private lateinit var edtMobile: EditText
    private lateinit var verificationId: String
    private lateinit var mAuth: FirebaseAuth
    private lateinit var otpView: LinearLayout
    private lateinit var dialog: Dialog
    private lateinit var mdbRef: DatabaseReference
    private lateinit var signupText: TextView
    private lateinit var otpPinView: SquarePinField
    private lateinit var userTypeView: AutoCompleteTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // get Firebase instances
        mAuth = FirebaseAuth.getInstance()
        mdbRef = FirebaseDatabase.getInstance().reference

        // check if already a user is logged in
        if (mAuth.currentUser != null) {

            startActivity(Intent(this, PatientHomePage::class.java))
            finish() // close this activity

        }

        // initialize views
        emailLayout = findViewById(R.id.email_login_layout)
        mobileLayout = findViewById(R.id.mobile_login_layout)
        emailLoginButton = findViewById(R.id.email_login_button)
        getOtpBtn = findViewById(R.id.getOtpBtn)
        radioGroup = findViewById(R.id.radio_grp)
        edtMobile = findViewById(R.id.edtMobile)
        otpView = findViewById(R.id.otp_view)
        signupText = findViewById(R.id.signupText)
        otpPinView = findViewById(R.id.otp_edit_text)
        dialog = Dialog(this)


        // for setting up views and listeners
        setUp()

    }

    private fun setUp() {

        // when signup text is clicked this will get called
        signupText.setOnClickListener {

            // go to signup page
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
            // don't finish this activity, as user should be able to comeback to this page again
            // from signup page

        }

        // when get otp button is clicked, this will get called
        getOtpBtn.setOnClickListener {

            // start loading dialog box
            dialog.setContentView(R.layout.error_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.findViewById<TextView>(R.id.txt__verify).text =
                getString(R.string.verifyingMessage)
            dialog.show()

            // get the phone number
            var phone = edtMobile.text.toString()

            // validate phone number
            if (phone.isNotEmpty() && isValidMobile(phone) && phone.length == 10) {

                // if valid, then send code
                edtMobile.clearFocus() // move cursor out of textBox
                phone = "+91$phone" // adding country code
                sendVerificationCode(phone)

                // hide keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            } else {

                // show error message if it is empty
                showErrorMessage("Please enter a valid phone number")
                edtMobile.requestFocus() // focusing edittext

            }
        }

        // when user click email login button
        emailLoginButton.setOnClickListener {
            // display dialog
            dialog.setContentView(R.layout.error_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            // get and verify details
            val email = findViewById<EditText>(R.id.email_input).text.toString()
            val password = findViewById<EditText>(R.id.password_input).text.toString()

            if (!isValidMail(email)) {
                showErrorMessage("Email entered is invalid.Please enter a valid email")
            }

            // if details are valid, then login
            login(email, password)

        }

        // when 6 digits otp is entered , this will get called
        otpPinView.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {

                if (TextUtils.isEmpty(enteredText)) {

                    // if the OTP text field is empty display
                    // a message to user to enter OTP
                    Toast.makeText(this@Login, "Please enter OTP", Toast.LENGTH_SHORT).show()

                } else {

                    // if OTP field is not empty calling
                    // method to verify the OTP.
                    verifyCode(enteredText)

                }

                return true
            }
        }

        // set up dropdown for user type
        val userTypes = resources.getStringArray(R.array.user_types)
        val arrayAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, userTypes
        )
        userTypeView = findViewById(R.id.user_type_selector)
        userTypeView.setAdapter(arrayAdapter)
        userTypeView.setSelection(0) // default to patient

        // when radio button is changed for mobile login or email login
        radioGroup.setOnCheckedChangeListener { _, checkedId ->

            // hide one layout and show chosen layout
            if (checkedId == R.id.radio_mobile_login) {
                emailLayout.visibility = View.GONE
                mobileLayout.visibility = View.VISIBLE
            } else {
                emailLayout.visibility = View.VISIBLE
                mobileLayout.visibility = View.GONE
            }

        }

        // for hiding and un-hiding password
        val password = findViewById<EditText>(R.id.password_input)
        HelperFunctions.setPasswordView(password)

    }

    companion object {

        // check if email is valid
        fun isValidMail(email: String): Boolean {
            val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$"

            val pat: Pattern = Pattern.compile(emailRegex)
            return if (email == null) false else pat.matcher(email).matches()
        }

        // check if mobile is valid
        fun isValidMobile(phone: String): Boolean {
            val p = Pattern.compile("^\\d{10}$")

            val m: Matcher = p.matcher(phone)

            return m.matches()
        }
    }

    private fun login(email: String, password: String) {
        //code for logging in the user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    // if sign in success, check if it is doctor or patient
                    val user = mAuth.currentUser
                    val patient = resources.getStringArray(R.array.user_types)[0]
                    val doctor = resources.getStringArray(R.array.user_types)[1]
                    if (userTypeView.text.toString() == patient) {

                        if (user != null) {

                            mdbRef.child(dbPaths.USERS).child(user.uid).get()
                                .addOnSuccessListener {
                                    if (it.exists()) {

                                        // check is user email is verified
                                        if (user.isEmailVerified) {

                                            // check if profile is completed
                                            mdbRef.child(dbPaths.USERS).child(user.uid)
                                                .child(dbPaths.USER_DETAILS).get()
                                                .addOnSuccessListener { it1 ->
                                                    if (it1.exists()) {

                                                        // redirect to main page
                                                        val intent = Intent(
                                                            this,
                                                            PatientHomePage::class.java
                                                        )
                                                        startActivity(intent)
                                                        finish()

                                                    } else {
                                                        val intent =
                                                            Intent(
                                                                this,
                                                                UserDetailsActivity::class.java
                                                            )
                                                        startActivity(intent)
                                                        finish()
                                                    }
                                                }
                                        } else {

                                            mAuth.signOut()
                                            val intent = Intent(this, EmailNotVerified::class.java)
                                            startActivity(intent)
                                            finish()

                                        }
                                    } else {

                                        showErrorMessage("Invalid details.Please enter valid details and user type")

                                    }
                                }

                        }
                    } else if (userTypeView.text.toString() == doctor) {

                        //check if really a doctors
                        mdbRef.child(dbPaths.DoctorIds).child(user!!.uid).get()
                            .addOnSuccessListener {
                                if (it.exists()) {

                                    // redirect to doctor homepage
                                    startActivity(Intent(this@Login, Doctor_Homepage::class.java))
                                    finish()

                                } else {

                                    showErrorMessage(
                                        "Invalid credentials entered. Please select the user type correctly and enter" +
                                                "valid details"
                                    )

                                }
                            }
                    }
                } else {

                    // If log in fails, display a message to the user.
                    showErrorMessage("Invalid credentials entered. Please enter valid credentials")

                }

            }
    }

    private fun sendVerificationCode(number: String) {
        // this method is used for getting
        // OTP on user phone number.
        // initializing our callbacks for on
        // verification callback method.

        val mCallBack: OnVerificationStateChangedCallbacks =
            object : OnVerificationStateChangedCallbacks() {
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
                    Toast.makeText(this@Login, "verification done", Toast.LENGTH_SHORT).show()
                }

                // this method is called when firebase doesn't
                // sends our OTP code due to any error or issue.
                override fun onVerificationFailed(e: FirebaseException) {

                    dialog.setContentView(R.layout.error_layout)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                    showErrorMessage("Invalid mobile number entered. Please enter a valid mobile number")

                }

                override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
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

                    Toast.makeText(this@Login, "OTP sent", Toast.LENGTH_LONG).show()

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
                    val user = mAuth.currentUser
                    if (user != null) {
                        mdbRef.child(dbPaths.USERS).child(user.uid)
                            .child(dbPaths.USER_DETAILS).get()
                            .addOnSuccessListener {

                                if (it.exists()) {
                                    val i = Intent(this, PatientHomePage::class.java)
                                    startActivity(i)
                                    finish()

                                } else {

                                    val i = Intent(this, UserDetailsActivity::class.java)
                                    startActivity(i)
                                    finish()

                                }
                            }
                    }

                } else {

                    otpPinView.setText("") //reset otp
                    showErrorMessage("You have entered wrong OTP. Please enter correct otp sent to your device or click on resend otp")

                }
            }

    }

    private fun showErrorMessage(msg: String) {

        // shows the error message in dialog box
        dialog.findViewById<LinearLayout>(R.id.loading).visibility = View.GONE
        dialog.findViewById<LinearLayout>(R.id.error_view).visibility = View.VISIBLE
        dialog.findViewById<TextView>(R.id.failed_title).text =
            getString(R.string.LoginFailedMessage)
        dialog.findViewById<TextView>(R.id.error_message).text = msg
        dialog.findViewById<MaterialButton>(R.id.close_button).setOnClickListener {
            dialog.dismiss()
        }

    }

}