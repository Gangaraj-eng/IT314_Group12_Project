package com.mypackage.it314_health_center;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mypackage.it314_health_center.startups.pres_adapter;
import com.mypackage.it314_health_center.startups.prescription_class;

import java.util.ArrayList;
import java.util.List;

public class my_prescriptions extends Activity {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<prescription_class> pres_list;
    RecyclerView.Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_prescriptions);

        initData();
        initRecyclerView();


    }



    private void initData() {
        pres_list = new ArrayList<>();
        pres_list.add(new prescription_class(R.drawable.prescription1,"01 April 2023 | 1:00 pm","Issued by Dr. Reddy", "__________________________________"));
        pres_list.add(new prescription_class(R.drawable.prescription2,"02 April 2023 | 2:00 pm","Issued by Dr. Reddy", "__________________________________"));
        pres_list.add(new prescription_class(R.drawable.prescription3,"03 April 2023 | 3:00 pm","Issued by Dr. Reddy", "__________________________________"));
    }

    private void initRecyclerView() {
        recyclerView=findViewById(R.id.prescription_view);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new pres_adapter(pres_list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
