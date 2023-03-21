package com.mypackage.it314_health_center

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LogIn : AppCompatActivity() {

    private lateinit var edtEmail:EditText
    private lateinit var edtPassword:EditText
    private lateinit var btnLogIn:Button
    private lateinit var btnSignUp:Button
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogIn = findViewById(R.id.btnLogIn)
        btnSignUp = findViewById(R.id.btnSignUp)
        edtMobile = findViewById(R.id.edt_mobile)

        btnSignUp.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        btnLogIn.setOnClickListener {

            if(isValidMobile(edtMobile.text.toString())){
                // send verification code to mobile number

                String phone = edtMobile.text.toString().trim()

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phone,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            // this method is called when the code is automatically detected
                            // you can directly send the code to the server
                            // or you can show the code to the user and let the user enter the code
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            // this method is called when the code is not sent
                            // you can display an error message to the user
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            // this method is called when the code is sent
                            // you can either open a new activity or
                            // you can directly send the code to the server
                            Intent intent = new Intent(this, verifyOtp::class.java)
                            intent.putExtra("backendOtp", backendOtp)
                            intent.putExtra("phone", phone)
                            startActivity(intent)
                        }
                    }
                );


            }

            else if(isValidMail(edtEmail.text.toString())){
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()

                login(email, password)
            }
            else {
                edtEmail.error = "Please enter valid email or phone number"
                edtEmail.requestFocus()
                return@setOnClickListener
            }
        }
    }

    private fun login(email: String, password: String) {
        //code for logging in the user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If log in fails, display a message to the user.
                    Toast.makeText(this@LogIn,"User does not exist", Toast.LENGTH_LONG).show()
                }
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

}