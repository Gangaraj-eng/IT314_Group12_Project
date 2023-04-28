package com.mypackage.it314_health_center.startups

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.PatientHomePage
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.adapters.relative_detail_adapter
import com.mypackage.it314_health_center.helpers.dbPaths
import com.mypackage.it314_health_center.models.Relative_detail
import com.mypackage.it314_health_center.models.User_Details
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class UserDetailsActivity : AppCompatActivity() {


    private lateinit var dayDropdown: TextInputLayout
    private lateinit var monthDropdown: TextInputLayout
    private lateinit var yearDropdown: TextInputLayout
    private lateinit var dayText: AutoCompleteTextView
    private lateinit var yearText: AutoCompleteTextView
    private lateinit var monthText: AutoCompleteTextView
    private lateinit var genderSelect: TextView
    private lateinit var relativeRecyclerView: RecyclerView
    private lateinit var relativeList: ArrayList<Relative_detail>
    private lateinit var adapter: relative_detail_adapter
    private lateinit var addButton: Button
    private lateinit var nextButton: Button
    private lateinit var part1: ConstraintLayout
    private lateinit var part2: ConstraintLayout
    private lateinit var backButton: AppCompatImageButton
    private lateinit var confirmButton: Button
    private lateinit var nameView: EditText
    private lateinit var ageView: EditText
    private lateinit var addressView1: EditText
    private lateinit var addressView2: EditText
    private lateinit var cityView: EditText
    private lateinit var stateView: EditText
    private lateinit var pinCodeView: EditText
    private lateinit var countryView: EditText
    private lateinit var errorText: TextView
    private var mdbRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        // initialize views
        dayDropdown = findViewById(R.id.day_dropdown)
        monthDropdown = findViewById(R.id.month_dropdown)
        yearDropdown = findViewById(R.id.year_dropdown)

        nameView = findViewById(R.id.user_name)
        ageView = findViewById(R.id.age_view)
        addressView1 = findViewById(R.id.address_line_1)
        addressView2 = findViewById(R.id.address_line_2)

        genderSelect = findViewById(R.id.gender_select)
        relativeRecyclerView = findViewById(R.id.relative_list_view)
        relativeList = ArrayList()
        nextButton = findViewById(R.id.next_btn)
        relativeList.add(Relative_detail())
        cityView = findViewById(R.id.city_view)
        stateView = findViewById(R.id.state_view)
        countryView = findViewById(R.id.country_view)
        pinCodeView = findViewById(R.id.pincode_view)
        errorText = findViewById(R.id.error_text)

        part1 = findViewById(R.id.details_part_1)
        part2 = findViewById(R.id.detail_part_2)
        confirmButton = findViewById(R.id.confirm)
        adapter = relative_detail_adapter(this, relativeList)
        relativeRecyclerView.adapter = adapter
        relativeRecyclerView.layoutManager = LinearLayoutManager(this)
        addButton = findViewById(R.id.add_relative_button)
        backButton = findViewById(R.id.go_back)

        setUPDropdowns()
        setUpListeners()
    }

    private fun setUpListeners() {

        // when part1 is done, and part2 is clicked
        nextButton.setOnClickListener {

            // verify details first
            if (nameView.text.toString().isEmpty()) {
                showErrorMessage("Name cannot be empty")
            } else if (ageView.text.toString().isEmpty() || ageView.text.toString()
                    .toInt() >= 100 || ageView.text.toString().toInt() <= 0
            ) {
                showErrorMessage("Enter a valid age between 1-100")
            } else if (genderSelect.text.toString() == "Gender") {
                showErrorMessage("select a gender")
            } else if (addressView1.text.isEmpty()) {
                showErrorMessage("Address line 1 cannot be empty")
            } else if (cityView.text.isEmpty()) {
                showErrorMessage("City name cannot be empty")
            } else if (stateView.text.isEmpty()) {
                showErrorMessage("State name cannot be empty")
            } else if (pinCodeView.text.isEmpty()) {
                showErrorMessage("Pin code cannot be empty")
            } else if (countryView.text.isEmpty()) {
                showErrorMessage("Country name cannot be empty")
            } else {
                part2.visibility = View.VISIBLE
                part1.visibility = View.GONE
            }
        }

        // when back button is pressed
        backButton.setOnClickListener {
            part1.visibility = View.VISIBLE
            part2.visibility = View.GONE
        }

        // when final confirm button is clicked
        // add profile details to database
        confirmButton.setOnClickListener {
            // verify details and save

            val dialog = Dialog(this)
            dialog.setContentView(R.layout.error_layout)
            dialog.findViewById<TextView>(R.id.failed_title).visibility = View.GONE
            dialog.findViewById<TextView>(R.id.txt__verify).text =
                getString(R.string.creatingProfile)
            dialog.show()

            val userDetails = User_Details(
                nameView.text.toString(),
                "${dayText.text}-${monthText.text}-${yearText.text}",
                ageView.text.toString().toInt(),
                genderSelect.text.toString(),
                addressView1.text.toString(),
                addressView2.text.toString(),
                cityView.text.toString(),
                stateView.text.toString(),
                countryView.text.toString(),
                pinCodeView.text.toString().toInt()
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            mdbRef.child(dbPaths.USERS).child(userId).child(dbPaths.USER_DETAILS)
                .setValue(userDetails).addOnSuccessListener {
                    mdbRef.child(dbPaths.USER_DETAILS).child(userId).child(dbPaths.RELATIVE_DETAILS)
                        .setValue(relativeList)
                        .addOnSuccessListener {

                            // redirect to homepage
                            startActivity(Intent(this, PatientHomePage::class.java))

                        }
                }

        }

    }

    companion object
    {
        fun validateProfile(
            name: String,
            dob: String,
            gender: String,
            addr: String,
            city: String,
            state: String,
            country: String,
            pincode: String
        ):Boolean{

            if(name.trim().isEmpty()) return false
            val formatter= SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val dbDate=formatter.parse(dob)
            val cdate=formatter.parse("27/04/2023:")
            if(dbDate>=cdate) return false
            if(gender!="male"  && gender!="female")
                return false
            if(addr.trim().length<20) return false
            if(city.isEmpty()) return false
            if(state.isEmpty()) return false
            if(country.isEmpty()) return false
            if(pincode.length!=6) return  false
            return true

        }
    }

    private fun setUPDropdowns() {

        val dayList = ArrayList<String>()
        val monthList = ArrayList<String>()
        val yearList = ArrayList<String>()

        // fill the list with dates,months and years
        for (i in 1..31) {
            if (i < 10) dayList.add("0$i")
            else
                dayList.add(i.toString())
        }
        for (i in 1..12) {
            if (i < 10) monthList.add("0$i")
            else
                monthList.add(i.toString())
        }
        for (i in 1900..2023)
            yearList.add(i.toString())

        val dayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dayList)
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, monthList)
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, yearList)

        dayText = findViewById(R.id.day_selector)
        monthText = findViewById(R.id.month_selector)
        yearText = findViewById(R.id.year_selector)

        dayText.setAdapter(dayAdapter)
        monthText.setAdapter(monthAdapter)
        yearText.setAdapter(yearAdapter)

        genderSelect.setOnClickListener { buttonView ->

            Log.d("123", "Clicked")

            // create menu
            val genderSelectMenu = PopupMenu(this, buttonView)
            genderSelectMenu.menuInflater.inflate(R.menu.gender_menu, genderSelectMenu.menu)
            genderSelectMenu.setOnMenuItemClickListener {
                genderSelect.text = it.title
                return@setOnMenuItemClickListener true
            }

            genderSelectMenu.show() // show menu

        }

        // to add more relatives in list
        addButton.setOnClickListener {

            if (relativeList.size < 4) {

                relativeList.add(Relative_detail())
                adapter.notifyItemInserted(relativeList.size)
                relativeRecyclerView.scrollToPosition(relativeList.size - 1)

            } else {

                Toast.makeText(this, "Maximum allowed 4 relatives only", Toast.LENGTH_SHORT)
                    .show()

            }
        }

        // when back button is pressed
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // signing out
                FirebaseAuth.getInstance().signOut()
            }
        })

    }

    private fun showErrorMessage(msg: String) {

        errorText.visibility = View.VISIBLE
        errorText.text = msg

        Handler(Looper.getMainLooper()).postDelayed({
            errorText.visibility = View.GONE
        }, 2000)

    }

}