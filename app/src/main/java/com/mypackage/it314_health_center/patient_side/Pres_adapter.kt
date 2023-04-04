package com.mypackage.it314_health_center.patient_side

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.models.prescription_class
import java.util.*

class pres_adapter(private val pres_list: List<prescription_class>) :
    RecyclerView.Adapter<pres_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prescriptions_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos=pres_list[position]
        val pres_img = pres_list[position].image_url
        val issued_by = pres_list[position].issued_by
        val date = pres_list[position].date
        holder.setData(date, issued_by)
    }

    override fun getItemCount(): Int {
        return pres_list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var pres_image_id: ImageView? = null
        private var date_id: TextView? = null
        private var time_id: TextView? = null
        private var issue_id: TextView? = null

        init {
            pres_image_id = itemView.findViewById(R.id.pres_image_id)
            date_id = itemView.findViewById(R.id.date_id)
            time_id = itemView.findViewById(R.id.time_id)
            issue_id = itemView.findViewById(R.id.issue_id)
        }

        fun setData(date: Date, name: String) {

        }
    }
}