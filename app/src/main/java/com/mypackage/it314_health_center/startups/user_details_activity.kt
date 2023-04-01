package com.mypackage.it314_health_center.startups

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.MainActivity
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.adapters.relative_detail_adapter
import com.mypackage.it314_health_center.models.Relative_detail
import com.mypackage.it314_health_center.models.User_Details

class user_details_activity : AppCompatActivity() {


    private lateinit var day_dropdown: TextInputLayout
    private lateinit var month_dropdown: TextInputLayout
    private lateinit var year_dropdown: TextInputLayout
    private lateinit var day_text: AutoCompleteTextView
    private lateinit var year_text: AutoCompleteTextView
    private lateinit var month_text: AutoCompleteTextView
    private lateinit var gender_select: TextView
    private lateinit var relative_recyclerview: RecyclerView
    private lateinit var relativeList: ArrayList<Relative_detail>
    private lateinit var adapter: relative_detail_adapter
    private lateinit var add_btn: Button
    private lateinit var next_btn: Button
    private lateinit var part1: ConstraintLayout
    private lateinit var part2: ConstraintLayout
    private lateinit var backbtn: AppCompatImageButton
    private lateinit var confirm_btn: Button
    private lateinit var name_view: EditText
    private lateinit var age_view: EditText
    private lateinit var address_view_1: EditText
    private lateinit var address_view_2: EditText
    private lateinit var city_view: EditText
    private lateinit var state_view: EditText
    private lateinit var pincode_view: EditText
    private lateinit var country_view: EditText
    private lateinit var error_text: TextView
    private var mdbRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        day_dropdown = findViewById(R.id.day_dropdown)
        month_dropdown = findViewById(R.id.month_dropdown)
        year_dropdown = findViewById(R.id.year_dropdown)

        name_view = findViewById(R.id.user_name)
        age_view = findViewById(R.id.age_view)
        address_view_1 = findViewById(R.id.address_line_1)
        address_view_2 = findViewById(R.id.address_line_2)

        gender_select = findViewById(R.id.gender_select)
        relative_recyclerview = findViewById(R.id.relative_list_view)
        relativeList = ArrayList()
        next_btn = findViewById(R.id.next_btn)
        relativeList.add(Relative_detail())
        city_view = findViewById(R.id.city_view)
        state_view = findViewById(R.id.state_view)
        country_view = findViewById(R.id.country_view)
        pincode_view = findViewById(R.id.pincode_view)
        error_text = findViewById(R.id.error_text)

        part1 = findViewById(R.id.details_part_1)
        part2 = findViewById(R.id.detail_part_2)
        confirm_btn = findViewById(R.id.confirm)
        adapter = relative_detail_adapter(this, relativeList)
        relative_recyclerview.adapter = adapter
        relative_recyclerview.layoutManager = LinearLayoutManager(this)
        add_btn = findViewById(R.id.add_relative_button)
        backbtn = findViewById(R.id.go_back)


        next_btn.setOnClickListener {
            // verify details first
            if (name_view.text.toString().isEmpty()) {
                show_error("Name cannot be empty")
            } else if (age_view.text.toString().isEmpty() || age_view.text.toString()
                    .toInt() >= 100 || age_view.text.toString().toInt() <= 0
            ) {
                show_error("Enter a valid age between 1-100")
            } else if (gender_select.text.toString() == "Gender") {
                show_error("select a gender")
            } else if (address_view_1.text.isEmpty()) {
                show_error("Address line 1 cannot be empty")
            } else if (city_view.text.isEmpty()) {
                show_error("City name cannot be emtpy")
            } else if (state_view.text.isEmpty()) {
                show_error("State name cannot be emtpy")
            } else if (pincode_view.text.isEmpty()) {
                show_error("Pin code cannot be emtpy")
            } else if (country_view.text.isEmpty()) {
                show_error("Country name cannot be emtpy")
            } else {
                part2.visibility = View.VISIBLE
                part1.visibility = View.GONE
            }
        }

        backbtn.setOnClickListener {
            part1.visibility = View.VISIBLE
            part2.visibility = View.GONE
        }

        confirm_btn.setOnClickListener {
            // verify details and save
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.error_layout)
            dialog.findViewById<TextView>(R.id.failed_title).visibility = View.GONE
            dialog.findViewById<TextView>(R.id.txt__verify).text = "Creating your profile..."
            dialog.show()
            val user_details = User_Details(
                name_view.text.toString(),
                "${day_text.text}-${month_text.text}-${year_text.text}",
                age_view.text.toString().toInt(),
                gender_select.text.toString(),
                address_view_1.text.toString(),
                address_view_2.text.toString(),
                city_view.text.toString(),
                state_view.text.toString(),
                country_view.text.toString(),
                pincode_view.text.toString().toInt()
            )
            val uid = FirebaseAuth.getInstance().currentUser?.uid

            mdbRef.child("users").child(uid.toString()).child("user_details")
                .setValue(user_details).addOnSuccessListener {
                    mdbRef.child("users").child(uid.toString()).child("relative_details")
                        .setValue(relativeList)
                        .addOnSuccessListener {
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                }
        }
        set_up()
    }

    private fun set_up() {
        val daylist = ArrayList<String>()
        val monthlist = ArrayList<String>()
        val yearList = ArrayList<String>()

        for (i in 1..31) {
            if (i < 10) daylist.add("0$i")
            else
                daylist.add(i.toString())
        }

        for (i in 1..12) {
            if (i < 10) monthlist.add("0$i")
            else
                monthlist.add(i.toString())
        }
        for (i in 1900..2023)
            yearList.add(i.toString())
        val day_adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, daylist)
        val month_adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monthlist)
        val yearAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yearList)

        day_text = findViewById(R.id.day_selector)
        month_text = findViewById(R.id.month_selector)
        year_text = findViewById(R.id.year_selector)

        day_text.setAdapter(day_adapter)
        month_text.setAdapter(month_adapter)
        year_text.setAdapter(yearAdapter)

        gender_select.setOnClickListener { btnview ->
            Log.d("123", "Clicked")
            val gender_select_menu = PopupMenu(this, btnview)
            gender_select_menu.menuInflater.inflate(R.menu.gender_menu, gender_select_menu.menu)
            gender_select_menu.setOnMenuItemClickListener {
                gender_select.text = it.title
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

    private fun show_error(msg: String) {
        error_text.visibility = View.VISIBLE
        error_text.text = msg
        Handler().postDelayed({
            error_text.visibility = View.GONE
        }, 2000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        FirebaseAuth.getInstance().signOut()
    }
}