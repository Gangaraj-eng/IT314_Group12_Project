package com.mypackage.it314_health_center.patient_side

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.adapters.LabReportAdapter
import com.mypackage.it314_health_center.helpers.dbPaths
import com.mypackage.it314_health_center.models.LabReport

class ActivityDownloadReports : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LabReportAdapter
    private lateinit var report_list: ArrayList<LabReport>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloadreport_main)

        recyclerView = findViewById(R.id.reports_list)
        report_list = ArrayList()
        adapter = LabReportAdapter(this, report_list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().reference.child(dbPaths.LabReports)
            .child(userId).addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val report = snapshot.getValue(LabReport::class.java)
                    if (report != null) {
                        report_list.add(report)
                        adapter.notifyItemInserted(report_list.size)
                    }

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }
    }
}