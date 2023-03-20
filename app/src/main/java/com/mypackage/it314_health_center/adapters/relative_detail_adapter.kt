package com.mypackage.it314_health_center.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.models.Relative_detail


class relative_detail_adapter(
    val context: Context?,
    val relative_list: ArrayList<Relative_detail>
) : RecyclerView.Adapter<relative_detail_adapter.RelativeViewHolder>() {


    class RelativeViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val relativeName: EditText = itemview.findViewById<EditText>(R.id.relative_name)
        val relativecount: TextView = itemview.findViewById<TextView>(R.id.item_position)
        val relativeContact: EditText = itemview.findViewById<EditText>(R.id.relative_contact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelativeViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.relative_detail_layout, parent, false)
        return RelativeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return relative_list.size
    }

    override fun onBindViewHolder(holder: RelativeViewHolder, position: Int) {

        val count = position + 1
        holder.relativecount.text = count.toString()
        if (relative_list[position].relativeName != null)
            holder.relativeName.setText(relative_list[position].relativeName)

        if (relative_list[position].relativeContact != null)
            holder.relativeContact.setText(relative_list[position].relativeContact)
        holder.relativeName.addTextChangedListener {
            relative_list[position].relativeName = it.toString()
        }
        holder.relativeContact.addTextChangedListener {
            relative_list[position].relativeContact = it.toString()
        }
    }
}