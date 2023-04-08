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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class BookAppointment : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var mAuth: FirebaseAuth
    private var mDatabase: FirebaseDatabase? = null
    private lateinit var btnDateTime: Button
    private lateinit var showDateTime: TextView
    private lateinit var problemDescription: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var type: String
    private lateinit var btnBookAppointment: Button
    private var patientId: String = ""// getting the patient id
    private var patientName = ""// getting the patient name
    private lateinit var ifBooked: ConstraintLayout
    private lateinit var ifNotBooked: ConstraintLayout
    private lateinit var showDetails: TextView

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var timeStamp = 0L
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
        radioGroup = findViewById(R.id.radio_grp)
        problemDescription = findViewById(R.id.problemDescription)
        btnBookAppointment = findViewById(R.id.btnBookAppointment)
        ifBooked = findViewById(R.id.ifBooked)
        ifNotBooked = findViewById(R.id.ifNotBooked)
        showDetails = findViewById(R.id.showDetails)
        Log.d("TAG", mAuth.currentUser.toString())

        patientId = mAuth.currentUser!!.uid
        mDatabase?.reference?.child("users")?.child(patientId)?.child("user_details")
            ?.child("userName")?.get()?.addOnSuccessListener {
            patientName = it.value.toString()
        }
        // to check whether the pending appointment has been finished
        // appointment is finished after 30 minutes of the scheduled time
        // difference between the current time and the scheduled time is calculated
        // if the difference is greater than 30 minutes, the appointment is finished
        mDatabase?.reference?.child("appointments")?.child("upcoming")?.child(patientId)?.get()
            ?.addOnSuccessListener {
                if (it.exists()) {
                    var aptcurr: BasicAppiontment? = null

                    for (apt in it.children) {
                        aptcurr = apt.getValue(BasicAppiontment::class.java)
                        val time = aptcurr?.time.toString()
                        val date = aptcurr?.date.toString()
                        val problemDescription = aptcurr?.problemDescription.toString()
                        val type = aptcurr?.type.toString()
                        val appointmentId = apt.key

                        val fmillis =
                            (SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date + " " + time)).time
                        var cmillis = System.currentTimeMillis()
                        val difference = cmillis - fmillis
                        val differenceInMinutes = difference / 60000
                        if (differenceInMinutes > 5) {
                            if (appointmentId != null) {
                                mDatabase?.reference?.child("appointments")?.child("upcoming")
                                    ?.child(patientId)?.child(appointmentId)?.removeValue()
                                mDatabase?.reference!!.child("doctor_appointments")
                                    .child("zaafhqrZPUPVd8WZchSnMnkx1Dm1").child("upcoming")
                                    .child(appointmentId).removeValue()
                            }
                            if (appointmentId != null) {
                                mDatabase?.reference?.child("appointments")?.child("past")
                                    ?.child(patientId)?.child(appointmentId)?.setValue(aptcurr)
                            }
                            recreate()
                        } else {
                            Toast.makeText(
                                this,
                                "You have already booked an appointment",
                                Toast.LENGTH_SHORT
                            ).show()
                            ifBooked.visibility = View.VISIBLE
                            showDetails.text =
                                "Date: $date \n Time: $time \n\n Problem Description: $problemDescription\n\n Type: $type"
                        }
                    }
                }
            }
        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }

        // to check whether the user has booked an appointment previously and is still pending
        mDatabase?.reference?.child("appointments")?.child("upcoming")?.child(patientId)?.get()
            ?.addOnSuccessListener {
                if (it.exists()) {
                    var aptcurr: BasicAppiontment? = null

                    for (apt in it.children) {
                        aptcurr = apt.getValue(BasicAppiontment::class.java)
                    }

                    val time = aptcurr?.time.toString()
                    val date = aptcurr?.date.toString()
                    val problemDescription = aptcurr?.problemDescription.toString()
                    val type = aptcurr?.type.toString()
//                val appointmentId = it.key

                    Toast.makeText(
                        this,
                        "You have already booked an appointment",
                        Toast.LENGTH_SHORT
                    ).show()


                    ifBooked.visibility = View.VISIBLE
                    showDetails.text =
                        "Date: $date\nTime: $time\n\nProblem Description: $problemDescription\n\nType: $type"

                } else {
                    ifNotBooked.visibility = View.VISIBLE
                }
            }

        // to set date and time
        btnDateTime.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }

        // reading the problem description


        // to set type( online or offline)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId == R.id.radio_online) {
                type = "online"
            } else {
                type = "offline"
            }
        }

        // to book the appointment
        btnBookAppointment.setOnClickListener {
            val time = "$savedHour:$savedMinute"
            val problemDescription = problemDescription.text.toString()
            val date = "$savedDay/$savedMonth/$savedYear"
            val type = type
            val appointmentId = UUID.randomUUID().toString()
            val appointment = BasicAppiontment(patientName, date, time, problemDescription, type)
            appointment.timeStamp = timeStamp
            appointment.patientID = mAuth.currentUser!!.uid
            bookAppointment(appointment, appointmentId)

            //adding the doctor's appointment database
            val doctorId = "zaafhqrZPUPVd8WZchSnMnkx1Dm1"

            FirebaseDatabase.getInstance().reference.child("doctor_appointments").child(doctorId)
                .child("upcoming").child(appointmentId).setValue(appointment)

            val intent = Intent(this, PatientHomePage::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun bookAppointment(appointment: BasicAppiontment, id: String) {

        // to add the appointment to the database
        FirebaseDatabase.getInstance().reference.child("appointments").child("upcoming")
            .child(patientId).child(id).setValue(appointment)
            .addOnCompleteListener {
                Toast.makeText(this, "Appointment booked successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Appointment booking failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        year = cal.get(Calendar.YEAR)
        month = cal.get(Calendar.MONTH)
        day = cal.get(Calendar.DAY_OF_MONTH)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
        timeStamp = cal.timeInMillis

    }

//    private fun pickDate() {
//        btnDateTime.setOnClickListener {
//            getDateTimeCalendar()
//            DatePickerDialog(this, this, year, month, day).show()
//        }
//    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
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