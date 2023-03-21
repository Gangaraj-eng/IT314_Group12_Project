package com.mypackage.it314_health_center

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    private lateinit var btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn=findViewById(R.id.btn)

        btn.setOnClickListener {
            btn.visibility= View.GONE

        }
    }
}