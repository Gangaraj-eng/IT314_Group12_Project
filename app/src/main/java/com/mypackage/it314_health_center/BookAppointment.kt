package com.mypackage.it314_health_center

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class BookAppointment : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var mAuth: FirebaseAuth
    private var mDatabase: FirebaseDatabase? = null
    private lateinit var btnDateTime: Button
    private lateinit var showDateTime: TextView
    private lateinit var problemDescription:EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var type: String
    private lateinit var btnBookAppointment: Button
    private var patientId: String =""// getting the patient id
    private var patientName =""// getting the patient name
    private lateinit var ifBooked: ConstraintLayout
    private lateinit var ifNotBooked: ConstraintLayout
    private lateinit var showDetails: TextView

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        btnDateTime = findViewById(R.id.btnDateTime)
        showDateTime = findViewById(R.id.showDateTime)
        radioGroup=findViewById(R.id.radio_grp)
        problemDescription = findViewById(R.id.problemDescription)
        btnBookAppointment = findViewById(R.id.btnBookAppointment)
        ifBooked = findViewById(R.id.ifBooked)
        ifNotBooked = findViewById(R.id.ifNotBooked)
        showDetails = findViewById(R.id.showDetails)
        Log.d("TAG",mAuth.currentUser.toString())

        patientId=mAuth.currentUser!!.uid
       mDatabase?.reference?.child("users")?.child(patientId)?.child("user_details")?.child("userName")?.get()?.addOnSuccessListener {
            patientName = it.value.toString()
        }
        // to check whether the pending appointment has been finished
        // appointment is finished after 30 minutes of the scheduled time
        // difference between the current time and the scheduled time is calculated
        // if the difference is greater than 30 minutes, the appointment is finished
        mDatabase?.reference?.child("appointments")?.child("upcoming")?.child(patientId)?.get()?.addOnSuccessListener {
            if(it.exists())
            {
                val time = it.child("time").value.toString()
                val date = it.child("date").value.toString()
                val appointmentId = it.child("appointmentId").value.toString()
                val currentTime = Calendar.getInstance()
                val scheduledTime = Calendar.getInstance()
                val scheduledDate1 = date.split("/") // scheduled date
                val scheduledTime1 = time.split(":") // scheduled time
                scheduledTime.set(scheduledDate1?.get(2)?.toInt()!!,scheduledDate1?.get(1)?.toInt()!!,scheduledDate1?.get(0)?.toInt()!!,scheduledTime1?.get(0)?.toInt()!!,scheduledTime1?.get(1)?.toInt()!!)
                val difference = currentTime.timeInMillis - scheduledTime.timeInMillis
                val differenceInMinutes = difference/60000
                if(differenceInMinutes>30)
                {
                    mDatabase?.reference?.child("appointments")?.child("upcoming")?.child(patientId)?.child(appointmentId)?.removeValue()
                    mDatabase?.reference?.child("appointments")?.child("past")?.child(patientId)?.child(appointmentId)?.setValue(it)
                }
            }
        }

        // to check whether the user has booked an appointment previously and is still pending
        mDatabase?.reference?.child("appointments")?.child("upcoming")?.child(patientId)?.get()?.addOnSuccessListener {
            if(it.exists())
            {
                Toast.makeText(this,"You have already booked an appointment",Toast.LENGTH_SHORT).show()
                val date = it.child("date").value.toString()
                val time = it.child("time").value.toString()
                val problemDescription = it.child("problemDescription").value.toString()
                val type = it.child("type").value.toString()

                ifBooked.visibility = View.VISIBLE
                showDetails.text = "Date: $date\nTime: $time\n\nProblem Description: $problemDescription\n\nType: $type"

            }
            else{
                ifNotBooked.visibility = View.VISIBLE
            }
        }

        // to set date and time
        btnDateTime.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }

        // reading the problem description
        val problemDescription = problemDescription.text.toString()

        // to set type( online or offline)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if(checkedId==R.id.radio_online)
            {
                type = "online"
            }
            else{
                type = "offline"
            }
        }

        // to book the appointment
        btnBookAppointment.setOnClickListener {
            bookAppointment()

            //adding the doctor's appointment database
            val doctorId = "zaafhqrZPUPVd8WZchSnMnkx1Dm1"
            val appointmentId = UUID.randomUUID().toString()
            val appointment = basicAppointment(patientName,"$savedDay/$savedMonth/$savedYear","$savedHour:$savedMinute",problemDescription,type)
            FirebaseDatabase.getInstance().reference.child("doctor_appointments").child(doctorId).child("upcoming").child(appointmentId).setValue(appointment)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun bookAppointment() {
        val time = "$savedHour:$savedMinute"
        val date = "$savedDay/$savedMonth/$savedYear"
        val problemDescription = problemDescription.text.toString()
        val type = type
        val appointmentId = UUID.randomUUID().toString()
        val appointment = basicAppointment(patientName,date,time,problemDescription,type)

        // to add the appointment to the database
        FirebaseDatabase.getInstance().reference.child("appointments").child("upcoming").child(patientId).child(appointmentId).setValue(appointment)
            .addOnCompleteListener {
                Toast.makeText(this,"Appointment booked successfully",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Appointment booking failed",Toast.LENGTH_SHORT).show()
            }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

//    private fun pickDate() {
//        btnDateTime.setOnClickListener {
//            getDateTimeCalendar()
//            DatePickerDialog(this, this, year, month, day).show()
//        }
//    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()
        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        showDateTime.text = "$savedDay/$savedMonth/$savedYear,  $savedHour:$savedMinute"

    }


}