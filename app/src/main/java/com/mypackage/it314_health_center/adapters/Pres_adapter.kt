package com.mypackage.it314_health_center.patient_side

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.helpers.ImageManager
import com.mypackage.it314_health_center.models.prescription_class
import java.text.SimpleDateFormat
import java.util.*

class pres_adapter(private val pres_list: List<prescription_class>) :
    RecyclerView.Adapter<pres_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.prescriptions_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pres_img = pres_list[position].image_url
        val issued_by = pres_list[position].issued_by
        val date = pres_list[position].date

        ImageManager.loadImageIntoView(holder.pres_image_id, pres_img)
        holder.date_id.text = convertDateInMillisToString(date)
        holder.issue_id.text = issued_by
        holder.time_id.text = convertTimeInMillisToString(date)
        holder.parent.setOnClickListener {
            // start imageviewer activity with transition
            holder.pres_image_id.invalidate()
            val bitmap = holder.pres_image_id.drawable.toBitmap()
            val intent = Intent(it.context, Activtiy_ImageViewr::class.java)
            intent.putExtra("Image_bitmap", bitmap)
            intent.putExtra("image_url", pres_list[position].image_url)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                it.context as Activity, holder.parent, "fade"
            )
            it.context.startActivity(intent, options.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return pres_list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val pres_image_id: ImageView = itemView.findViewById(R.id.pres_image_id)
        val date_id: TextView = itemView.findViewById(R.id.date_id)
        val time_id: TextView = itemView.findViewById(R.id.time_id)
        val issue_id: TextView = itemView.findViewById(R.id.issue_id)
        val parent: MaterialCardView = itemView.findViewById(R.id.materialCardView2)
    }

    fun convertTimeInMillisToString(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
        val date = Date(time)
        return simpleDateFormat.format(date)
    }

    fun convertDateInMillisToString(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())
        val date = Date(time)
        return simpleDateFormat.format(date)
    }
}