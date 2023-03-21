package com.mypackage.it314_health_center.startups

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.adapters.relative_detail_adapter
import com.mypackage.it314_health_center.models.Relative_detail

class user_details : AppCompatActivity() {


    private lateinit var day_dropdown: TextInputLayout
    private lateinit var month_dropdown: TextInputLayout
    private lateinit var year_dropdown: TextInputLayout
    private lateinit var day_text:AutoCompleteTextView
    private lateinit var year_text:AutoCompleteTextView
    private lateinit var month_text:AutoCompleteTextView
    private lateinit var gender_select: TextView
    private lateinit var relative_recyclerview: RecyclerView
    private lateinit var relativeList: ArrayList<Relative_detail>
    private lateinit var adapter: relative_detail_adapter
    private lateinit var add_btn: Button
    private lateinit var next_btn:Button
    private lateinit var part1:ConstraintLayout
    private lateinit var part2:ConstraintLayout
    private lateinit var backbtn:AppCompatImageButton
    private lateinit var confirm_btn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        day_dropdown=findViewById(R.id.day_dropdown)
        month_dropdown=findViewById(R.id.month_dropdown)
        year_dropdown=findViewById(R.id.year_dropdown)

        gender_select=findViewById(R.id.gender_select)
        relative_recyclerview = findViewById(R.id.relative_list_view)
        relativeList = ArrayList()
        next_btn=findViewById(R.id.next_btn)
        relativeList.add(Relative_detail())
        part1=findViewById(R.id.details_part_1)
        part2=findViewById(R.id.detail_part_2)
        confirm_btn=findViewById(R.id.confirm)
        adapter = relative_detail_adapter(this, relativeList)
        relative_recyclerview.adapter = adapter
        relative_recyclerview.layoutManager = LinearLayoutManager(this)
        add_btn = findViewById(R.id.add_relative_button)
        backbtn=findViewById(R.id.go_back)

        next_btn.setOnClickListener {
            // verify details first

            part2.visibility= View.VISIBLE
            part1.visibility=View.GONE
        }

        backbtn.setOnClickListener {
            part1.visibility=View.VISIBLE
            part2.visibility=View.GONE
        }

        confirm_btn.setOnClickListener {
            // verify details and save
        }
        set_up()
    }

    private fun set_up() {
        val daylist=ArrayList<String>()
        val monthlist=ArrayList<String>()
        val yearList=ArrayList<String>()

        for( i in 1..31){
            if(i<10)daylist.add("0$i")
            else
                daylist.add(i.toString())}

        for(i in 1..12) {
            if(i<10)monthlist.add("0$i")
            else
                monthlist.add(i.toString())
        }
        for (i in 1900..2023)
            yearList.add(i.toString())
        val day_adapter=
            ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,daylist)
        val month_adapter=
            ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,monthlist)
        val yearAdapter=
            ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,yearList)

        day_text=findViewById(R.id.day_selector)
        month_text=findViewById(R.id.month_selector)
        year_text=findViewById(R.id.year_selector)

        day_text.setAdapter(day_adapter)
        month_text.setAdapter(month_adapter)
        year_text.setAdapter(yearAdapter)

        gender_select.setOnClickListener {btnview->
            Log.d("123","Clicked")
            val gender_select_menu= PopupMenu(this,btnview)
            gender_select_menu.menuInflater.inflate(R.menu.gender_menu,gender_select_menu.menu)
            gender_select_menu.setOnMenuItemClickListener {
                gender_select.text=it.title
                return@setOnMenuItemClickListener true
            }
            gender_select_menu.show()

        }

        add_btn.setOnClickListener {
            if (relativeList.size < 4) {
                relativeList.add(Relative_detail())
                adapter.notifyItemInserted(relativeList.size)
                relative_recyclerview.scrollToPosition(relativeList.size - 1)
            } else {
                Toast.makeText(this, "Maximum allowed 4 relatives only", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }


}