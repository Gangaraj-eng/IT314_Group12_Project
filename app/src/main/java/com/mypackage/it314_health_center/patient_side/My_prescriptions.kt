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
//        val uid="Hmq5nKTNfoTcyLLSGPN5CPxfzvG3"
//        temp_list.add(prescription_class(uid,"1","gs://healthcenter-70246.appspot.com/prescription1.jpg", issued_by = "Dr. Reddy"))
//        temp_list.add(prescription_class(uid,"2","gs://healthcenter-70246.appspot.com/prescription2.jpg", issued_by = "Dr. Raju"))
//        temp_list.add(prescription_class(uid,"3","gs://healthcenter-70246.appspot.com/prescription3.jpg", issued_by = "Dr. Rahul"))
//        temp_list.add(prescription_class(uid,"4","gs://healthcenter-70246.appspot.com/prescription3.jpg", issued_by = "Dr. Rahul"))
//        for(i in 0..3)
//        {
//            mdbref.child("prescriptions").child(uid)
//                .child(temp_list[i].appointment_id)
//                .setValue(temp_list[i])
//        }
//        val reportList=ArrayList<LabReport>()
//        reportList.add(LabReport("Blood test","Dr. Ravi","21-04-23","23-04-23"))
//        reportList.add(LabReport("X-ray","Dr. Rahul","22-04-23","24-04-23"))
//        reportList.add(LabReport("Insulin test","Dr. Reddy","23-04-23","25-04-23"))
//        reportList.add(LabReport("X-ray","Dr. Raju","20-04-23","22-04-23"))
//        reportList.add(LabReport("Blood test","Dr. Ram","19-04-23","23-04-23"))
//        for(i in 0..4)
//        {
//            reportList[i].ReportURL="https://firebasestorage.googleapis.com/v0/b/healthcenter-70246.appspot.com/o/PDFs%2Fraw%3A%2Fstorage%2Femulated%2F0%2FDownload%2F15-Hedglin-Phillips-Reilley%20(1).pdf?alt=media&token=20c2a7f5-8ebd-4315-b927-5482c59fad58"
//            reportList[i].id=UUID.randomUUID().toString()
//        mdbref.child(dbPaths.LabReports).child(uid).child(reportList[i].id)
//            .setValue(reportList[i])
//        }
    }
}