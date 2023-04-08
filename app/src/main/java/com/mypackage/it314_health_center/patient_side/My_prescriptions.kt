package com.mypackage.it314_health_center.patient_side

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.models.prescription_class

class my_prescriptions : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var pres_list: ArrayList<prescription_class>
    private lateinit var adapter: pres_adapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprescriptions_main)
        mAuth = FirebaseAuth.getInstance()
        mdbref = FirebaseDatabase.getInstance().reference
        initData()
        initRecyclerView()
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }
        Log.d("123", adapter.itemCount.toString())
    }

    private fun initData() {
        pres_list = ArrayList()
        mdbref.child("prescriptions").child(mAuth.currentUser!!.uid)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    val ps = snapshot.getValue(prescription_class::class.java)
                    if (ps != null) {
                        pres_list.add(ps)
                    }
                    adapter.notifyItemInserted(pres_list.size)
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
    }

    private fun initRecyclerView() {

        recyclerView = findViewById(R.id.prescirption_list)
        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        adapter = pres_adapter(pres_list)
        recyclerView.setAdapter(adapter)
        adapter.notifyDataSetChanged()

//        val temp_list=ArrayList<prescription_class>()
//        temp_list.add(prescription_class(mAuth.currentUser!!.uid,"1","gs://healthcenter-70246.appspot.com/prescription1.jpg", issued_by = "Dr. Reddy"))
//        temp_list.add(prescription_class(mAuth.currentUser!!.uid,"2","gs://healthcenter-70246.appspot.com/prescription2.jpg", issued_by = "Dr. Reddy"))
//        temp_list.add(prescription_class(mAuth.currentUser!!.uid,"3","gs://healthcenter-70246.appspot.com/prescription3.jpg", issued_by = "Dr. Reddy"))
//        for(i in 0..2)
//        {
//            mdbref.child("prescriptions").child(mAuth.currentUser!!.uid)
//                .child(temp_list[i].appointment_id)
//                .setValue(temp_list[i])
//        }
    }
}