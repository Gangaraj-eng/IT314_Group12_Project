package com.mypackage.it314_health_center

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.helpers.dbPaths
import com.mypackage.it314_health_center.videocalling.PatientOnlineConsultation
import java.text.SimpleDateFormat
import java.util.*


class BookAppointment : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var mAuth: FirebaseAuth
    private var mDatabase: FirebaseDatabase? = null
    private lateinit var edtDate: TextView
    private lateinit var edtTime: TextView
    private lateinit var showDateTime: TextView
    private lateinit var problemDescription: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var progressBar: ProgressBar
    private var type: String = ""
    private lateinit var btnBookAppointment: Button
    private var patientId: String = ""// getting the patient id
    private var patientName = ""// getting the patient name
    private lateinit var ifBooked: ConstraintLayout
    private lateinit var ifNotBooked: ConstraintLayout
    private lateinit var showDetails: TextView
    private lateinit var doctorTypeView: AutoCompleteTextView
    private lateinit var joinButton: Button


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
        edtDate = findViewById(R.id.edtDate)
//        edtDate.isEnabled = false
        edtTime = findViewById(R.id.edtTime)
//        edtTime.isEnabled = false
//        showDateTime = findViewById(R.id.showDateTime)
        radioGroup = findViewById(R.id.radio_grp)
        problemDescription = findViewById(R.id.problemDescription)
        btnBookAppointment = findViewById(R.id.btnBookAppointment)
        ifBooked = findViewById(R.id.ifBooked)
        ifNotBooked = findViewById(R.id.ifNotBooked)
        showDetails = findViewById(R.id.showDetails)
        doctorTypeView = findViewById(R.id.doctor_type_selector)
        doctorTypeView.isEnabled = false
        joinButton = findViewById(R.id.joinButton)
        progressBar = findViewById(R.id.progressBar)

        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
            finish()
        }

        Log.d("TAG", mAuth.currentUser.toString())

        patientId = mAuth.currentUser!!.uid
        mDatabase?.reference?.child("users")?.child(patientId)?.child("user_details")
            ?.child("userName")?.get()?.addOnSuccessListener {
                patientName = it.value.toString()
            }

        ifNotBooked.visibility = View.GONE
        ifBooked.visibility = View.GONE
        progressBar.visibility = View.VISIBLE


        //creating doctors with different types:
//        for(i in 0..6){
//            val doctorType = resources.getStringArray(R.array.doctor_types)[i]
//            val doctorId = UUID.randomUUID().toString()
//            val name = "Dr. $doctorType"
//            val email = "$doctorType@gmail.com"
//            val basicDoctor = basicDoctor(name, email)
//
//            FirebaseDatabase.getInstance().reference.child("doctors").child(doctorType)
//                .child(doctorId).setValue(basicDoctor)
//        }

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
                        val doctorId = aptcurr?.doctorId.toString()
                        val appointmentId = apt.key
                        val doctorType = aptcurr?.doctorType.toString()

                        val fmillis =
                            (SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date + " " + time)).time
                        val cmillis = System.currentTimeMillis()
                        val difference = cmillis - fmillis
                        val differenceInMinutes = difference / 60000
                        if (differenceInMinutes > 5) {
                            if (appointmentId != null) {
                                mDatabase?.reference?.child("appointments")?.child("upcoming")
                                    ?.child(patientId)?.child(appointmentId)?.removeValue()
                                mDatabase?.reference!!.child("doctor_appointments")
                                    .child(doctorId).child("upcoming")
                                    .child(appointmentId).removeValue()
                            }
                            if (appointmentId != null) {
                                mDatabase?.reference?.child("appointments")?.child("past")
                                    ?.child(patientId)?.child(appointmentId)?.setValue(aptcurr)
                            }
                            recreate()
                        } else {
//                            Toast.makeText(
//                                this,
//                                "You have already booked an appointment",
//                                Toast.LENGTH_SHORT
//                            ).show()

                            if (type == "online") {
                                joinButton.visibility = View.VISIBLE
                                joinButton.setOnClickListener {
                                    val intent = Intent(this, PatientOnlineConsultation::class.java)
                                    startActivity(intent)
                                }
                            } else {
                                joinButton.visibility = View.GONE
                            }


                            if (problemDescription.isEmpty())
                                showDetails.text =
                                    "Date: $date \nTime: $time \n\nAppointment mode: $type \n\nDoctor type: $doctorType"
                            else
                                showDetails.text =
                                    "Date: $date\nTime: $time\n\nProblem Description: $problemDescription\n\nAppointment mode: $type \n\nDoctor type: $doctorType"

                            progressBar.visibility = View.GONE
                            ifNotBooked.visibility = View.GONE
                            ifBooked.visibility = View.VISIBLE
                        }
                    }
                }
            }
//        findViewById<ImageButton>(R.id.back_btn).setOnClickListener {
//            finish()
//        }

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
                    val doctorType = aptcurr?.doctorType.toString()
//                val appointmentId = it.key

//                    Toast.makeText(
//                        this,
//                        "You have already booked an appointment",
//                        Toast.LENGTH_SHORT
//                    ).show()


                    if (problemDescription.isEmpty())
                        showDetails.text =
                            "Date: $date \nTime: $time \n\nAppointment mode: $type \n\nDoctor type: $doctorType"
                    else
                        showDetails.text =
                            "Date: $date\nTime: $time\n\nProblem Description: $problemDescription\n\nAppointment mode: $type \n\nDoctor type: $doctorType"

                    progressBar.visibility = View.GONE
                    ifNotBooked.visibility = View.GONE
                    ifBooked.visibility = View.VISIBLE

                } else {
                    progressBar.visibility = View.GONE
                    ifBooked.visibility = View.GONE
                    ifNotBooked.visibility = View.VISIBLE
                }
            }

        // to set date and time

        edtDate.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }

        edtTime.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(this, this, hour, minute, true).show()
        }

        // reading the problem description


        // to select the doctor type
        val doctorTypes = resources.getStringArray(R.array.doctor_types)
        val arrayAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, doctorTypes
        )

        doctorTypeView.setAdapter(arrayAdapter)
        doctorTypeView.setSelection(0) // default to General Physician

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

            val time = String.format("%02d:%02d", savedHour, savedMinute)
            val problemDescription = problemDescription.text.toString()
            val date = "$savedDay/$savedMonth/$savedYear"
            val type = type
            val doctorType = doctorTypeView.text.toString()
            Log.d("123", doctorType)
            val appointmentId = UUID.randomUUID().toString()

            if (edtDate.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select a valid date", Toast.LENGTH_SHORT).show()
                edtDate.error = "Please select a valid date"
                return@setOnClickListener
            }

            if (edtTime.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select a valid time", Toast.LENGTH_SHORT).show()
                edtTime.error = "Please select a valid time"
                return@setOnClickListener
            }

            val fmillis = (SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date + " " + time)).time
            val cmillis = System.currentTimeMillis()
            val difference = fmillis - cmillis

            if (difference < 0) {
                Toast.makeText(this, "Please select a valid date and time", Toast.LENGTH_SHORT)
                    .show()
//                showDateTime.error = "Please select a valid date and time"
                return@setOnClickListener
            }

            if (type == "") {
                Toast.makeText(this, "Please select the appointment mode", Toast.LENGTH_SHORT)
                    .show()
                val textView6 = findViewById<TextView>(R.id.textView12)
                textView6.error = "Please select the appointment mode"
                return@setOnClickListener
            }

            //adding the doctor's appointment database
            // here we have to take the first id from the doctor type's list
            var doctorId: String = ""

            FirebaseDatabase.getInstance().reference.child(dbPaths.DOCTORS)
                .child(doctorType).limitToFirst(1).get().addOnSuccessListener {
                    if (it.exists()) {
                        for (ch in it.children) {
                            doctorId = ch.key.toString()

                            val appointment = BasicAppiontment(
                                patientName,
                                date,
                                time,
                                problemDescription,
                                type,
                                doctorType,
                                doctorId
                            )
                            appointment.timeStamp = timeStamp
                            appointment.patientID = mAuth.currentUser!!.uid
                            bookAppointment(appointment, appointmentId)


                            FirebaseDatabase.getInstance().reference.child("doctor_appointments")
                                .child(doctorId)
                                .child("upcoming").child(appointmentId).setValue(appointment)

                            // check if notification is enabled or not
                            val mPrefs = getSharedPreferences("settings", 0)
                            val str = mPrefs.getString(dbPaths.NOTIFICATION_ENABLED, "1")
                            if (str == "1") {
                                //to send notification to the user

                                //Create a NotificationChannel
                                createNotificationChannel()

                                // Schedule the Notification
                                val notificationIntent =
                                    Intent(this, MyBroadcastReceiver::class.java)
                                val pendingIntent = PendingIntent.getBroadcast(
                                    this,
                                    0,
                                    notificationIntent,
                                    PendingIntent.FLAG_IMMUTABLE
                                )

                                val futureInMillis =
                                    (SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date + " " + time)).time

                                val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                                alarmManager.set(
                                    AlarmManager.RTC_WAKEUP,
                                    futureInMillis,
                                    pendingIntent
                                )

//                                Toast.makeText(this, "Notification Scheduled", Toast.LENGTH_LONG)
//                                    .show()
                            }
                        }
//                        Log.d("123",doctorId)
                    } else {
                        Toast.makeText(this, "No doctor available", Toast.LENGTH_SHORT).show()
                    }
                }

            val intent = Intent(this, PatientHomePage::class.java)
            startActivity(intent)
            finish()

        }
    }

    // to create a notification channel
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My channel"
            val descriptionText = "My channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("my_channel_id", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun bookAppointment(appointment: BasicAppiontment, id: String) {

        // to add the appointment to the database
        FirebaseDatabase.getInstance().reference.child("appointments").child("upcoming")
            .child(patientId).child(id).setValue(appointment)
            .addOnCompleteListener {
//                Snackbar.make(window.decorView.rootView, "Click the pin for more options", Snackbar.LENGTH_LONG).show();
                Toast.makeText(this, "Appointment booked successfully", Toast.LENGTH_LONG).show()
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

        val showDate = "$savedDay/$savedMonth/$savedYear"
//        edtDate.isEnabled = true
        edtDate.text = showDate
//        edtDate.isEnabled = false

//        getDateTimeCalendar()
//        TimePickerDialog(this, this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        savedHour = hourOfDay
        savedMinute = minute


//        if(hourOfDay.toString().length == 1)
//            savedHour = "0$hourOfDay"
//        if(minute.toString().length == 1)
//            savedMinute = "0$minute"

        val showtime = (String.format("%02d:%02d", savedHour, savedMinute));
//        edtTime. isEnabled = true
        edtTime.text = showtime
//        edtTime. isEnabled = false
//        showDateTime.text = "$savedDay/$savedMonth/$savedYear,  $showtime"

    }


}