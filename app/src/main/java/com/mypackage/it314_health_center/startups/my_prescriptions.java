package com.mypackage.it314_health_center.startups;

import android.os.Bundle;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mypackage.it314_health_center.R;

import java.util.ArrayList;
import java.util.List;

public class my_prescriptions extends AppCompatActivity {


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
        pres_list.add(new prescription_class(R.drawable.prescription1,"03 April 2023 | 1:00 pm","Issued by Dr. Reddy", "__________________________________"));

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
