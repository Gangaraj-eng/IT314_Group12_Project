package com.mypackage.it314_health_center

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var edtName: EditText
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up) //set the layout for this activity

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignUp)
        edtName = findViewById(R.id.edt_name)

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()

            signUp(name,email,password)
        }
    }

    private fun signUp(name:String, email: String, password: String) {
        //code for signing up the user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign ain success, update UI with the signed-in user's information
                    addUserToDatabase(name,email, mAuth.currentUser?.uid)

                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp,"Some error occurred",Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        if (uid != null) {
            mDbRef.child("user").child(uid).setValue(User(name,email,uid)) //add user to database
        }
    }
}