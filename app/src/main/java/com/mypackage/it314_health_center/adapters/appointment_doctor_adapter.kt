package com.mypackage.it314_health_center.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.BasicAppiontment
import com.mypackage.it314_health_center.startups.videocalling.DoctorSideOnlineConference
import java.text.SimpleDateFormat


class appointment_doctor_adapter(
    val context: Context?,
    val apt_list: ArrayList<BasicAppiontment>
) : RecyclerView.Adapter<appointment_doctor_adapter.RelativeViewHolder>() {


    class RelativeViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val timeView = itemView.findViewById<TextView>(R.id.time_view)
        val dateView = itemView.findViewById<TextView>(R.id.date_view)
        val aptname = itemview.findViewById<TextView>(R.id.appointment_name)
        val patientNameView = itemView.findViewById<TextView>(R.id.patient_name)
        val start_btn = itemview.findViewById<MaterialButton>(R.id.start_apt_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelativeViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_side_online_appointment, parent, false)
        return RelativeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return apt_list.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RelativeViewHolder, position: Int) {

        val count = position + 1
        val apt_curr = apt_list[position]
        holder.aptname.text = "Appointment " + count.toString()
        holder.dateView.text = apt_curr.date
        holder.timeView.text = apt_curr.time
        holder.patientNameView.text = apt_curr.name.toString().split(' ')[0]

        val fmillis =
            (SimpleDateFormat("dd/MM/yyyy hh:mm").parse(apt_curr.date + " " + apt_curr.time)).time
        val cmillis = System.currentTimeMillis()
        Log.d("123", fmillis.toString())
        Log.d("123", cmillis.toString())
        Log.d("123", (fmillis - cmillis).toString())
        if (fmillis > cmillis) {
            val timer = object : CountDownTimer(fmillis - cmillis, fmillis - cmillis) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    holder.start_btn.isEnabled = true
                }
            }
            timer.start()
        } else {
            holder.start_btn.isEnabled = true
        }
        holder.start_btn.setOnClickListener {
            val intent = Intent(context, DoctorSideOnlineConference::class.java)
            intent.putExtra("patient_id", apt_curr.patientID)
            Log.d("123", apt_curr.date.toString())
            Log.d("123", "sending = " + apt_curr.patientID)
            holder.aptname.context.startActivity(intent)
        }
    }
}