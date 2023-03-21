package com.mypackage.it314_health_center

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class verifyOtp : AppCompatActivity(){

        private lateinit var edtOtp:EditText
        private lateinit var btnVerify:Button
        private lateinit var mAuth:FirebaseAuth
        private lateinit var realOtp: String
        private lateinit var phone: String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_verify_otp) //activity for verifying otp

            mAuth = FirebaseAuth.getInstance()
            edtOtp = findViewById(R.id.edt_otp)
            btnVerify = findViewById(R.id.btnVerify)
            realOtp = getIntent().getStringExtra("backendOtp") // this is for example , here we will take the backend otp from Login.kt
            phone = getIntent().getStringExtra("phone") // phone number entered by the user

            btnVerify.setOnClickListener {
                val otp = edtOtp.text.toString()

                if(otp.isEmpty() || otp.length < 6){
                    edtOtp.error = "Please enter correct OTP"
                    edtOtp.requestFocus()
                    return@setOnClickListener
                }

                verify(otp,realOtp)
            }

            resendBtn = findViewById(R.id.resendBtn) // resend OTP button
            resendBtn.onclickListener {
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
                        public void onCodeSent(@NonNull String newbackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            // this method is called when the code is sent
                            // you can either open a new activity or
                            // you can directly send the code to the server
                            realOtp = newbackendOtp
                            Toast.makeText(this, "OTP sent", Toast.LENGTH_LONG).show(
                        }
                    }
                );
            }
        }

        private fun verify(otp: String,realOtp: String) {
            //code for verifying the otp

            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(realOtp, otp)

            mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
}