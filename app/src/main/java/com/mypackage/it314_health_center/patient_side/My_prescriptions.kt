package com.mypackage.it314_health_center.patient_side

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.models.prescription_class

class my_prescriptions : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var pres_list: ArrayList<prescription_class>
    private lateinit var adapter: pres_adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myprescriptions_main)
        initData()
        initRecyclerView()

        Log.d("123",adapter.itemCount.toString())
    }

    private fun initData() {
        pres_list = ArrayList()

        pres_list.add(
            prescription_class("123",
                "123",
                "https://www.researchgate.net/publication/345830022/figure/fig4/AS:957640029003789@1605330583881/Sample-prescription-used-as-input-to-the-GUI-developed-in-the-present-work.png",
                "Issued by Dr. Reddy",
            )
        )
    }

    private fun initRecyclerView() {

        recyclerView = findViewById(R.id.prescirption_list)
        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        adapter = pres_adapter(pres_list)
        recyclerView.setAdapter(adapter)
        adapter.notifyDataSetChanged()

    }
}