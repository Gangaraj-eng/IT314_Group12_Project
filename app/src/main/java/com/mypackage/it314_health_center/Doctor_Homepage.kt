package com.mypackage.it314_health_center

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mypackage.it314_health_center.adapters.appointment_doctor_adapter
import com.mypackage.it314_health_center.startups.Login

class Doctor_Homepage : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbRef: DatabaseReference
    private lateinit var aptrecyclerView: RecyclerView
    private lateinit var apt_list: ArrayList<BasicAppiontment>
    private lateinit var empty_view: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_homepage)
        mAuth = FirebaseAuth.getInstance()
        mdbRef = FirebaseDatabase.getInstance().reference
        aptrecyclerView = findViewById(R.id.apts_list)
        empty_view = findViewById(R.id.empty_appointmnet_view)
        apt_list = ArrayList()

        window
        val adapter = appointment_doctor_adapter(this, apt_list)
        aptrecyclerView.adapter = adapter
        aptrecyclerView.layoutManager = LinearLayoutManager(this)

        val uid = mAuth.currentUser!!.uid
        mdbRef.child("doctor_appointments").child(uid)
            .child("upcoming").addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.child("type").value == "Offline")
                        return
                    snapshot.getValue(BasicAppiontment::class.java)?.let { apt_list.add(it) }

                    adapter.notifyItemInserted(apt_list.size)
                    if (apt_list.size >= 1) {
                        empty_view.visibility = View.GONE
                    } else {
                        empty_view.visibility = View.VISIBLE
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        findViewById<ImageButton>(R.id.logout)
            .setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@Doctor_Homepage, Login::class.java))
                finish()
            }
    }
}