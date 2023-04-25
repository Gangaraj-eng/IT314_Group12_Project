package com.mypackage.it314_health_center.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.it314_health_center.BasicAppiontment
import com.mypackage.it314_health_center.R


class past_apt_adapter(
    val context: Context?,
    val apt_list: ArrayList<BasicAppiontment>
) : RecyclerView.Adapter<past_apt_adapter.AptViewHolder>() {


    class AptViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val timeView = itemView.findViewById<TextView>(R.id.time_view)
        val dateView = itemView.findViewById<TextView>(R.id.date_view)
        val aptname = itemview.findViewById<TextView>(R.id.appointment_name)
        val description = itemView.findViewById<TextView>(R.id.problemDescription)
        val type = itemview.findViewById<TextView>(R.id.type_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AptViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.past_apt_view, parent, false)
        return AptViewHolder(view)
    }

    override fun getItemCount(): Int {
        return apt_list.size
    }

    override fun onBindViewHolder(holder: AptViewHolder, position: Int) {

        val count = position + 1
        val apt_curr = apt_list[position]
        holder.aptname.text = "Appointment " + count.toString()
        holder.dateView.text = apt_curr.date
        holder.timeView.text = apt_curr.time
        holder.description.text = apt_curr.problemDescription
        holder.type.text = apt_curr.type
    }
}