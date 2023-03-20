package com.mypackage.it314_health_center.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.startups.SignupActivity


class signup_page_2 : Fragment() {


    private lateinit var view:View

    private lateinit var day_spinner:AppCompatSpinner
    private lateinit var month_spinner:AppCompatSpinner
    private lateinit var year_spinner:AppCompatSpinner
    private lateinit var gender_select:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_signup_page_2, container, false)

        day_spinner=view.findViewById(R.id.day_spinner)
        month_spinner=view.findViewById(R.id.month_spinner)
        year_spinner=view.findViewById(R.id.year_spinner)

         gender_select=view.findViewById(R.id.gender_select)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<AppCompatImageButton>(R.id.go_back)
            .setOnClickListener {
                (activity as SignupActivity).show_page_1()
            }
        view.findViewById<Button>(R.id.next_btn)
            .setOnClickListener {
                (activity as SignupActivity).show_page_3()
            }
        val daylist=ArrayList<String>()
        val monthlist=ArrayList<String>()
        val yearList=ArrayList<String>()

        for( i in 1..31){
            if(i<10)daylist.add("0$i")
            else
            daylist.add(i.toString())}

        for(i in 1..12) {
            if(i<10)monthlist.add("0$i")
            else
            monthlist.add(i.toString())
        }
        for (i in 1900..2023)
            yearList.add(i.toString())
        val day_adapter=ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,daylist)
        val month_adapter=ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,monthlist)
        val yearAdapter=ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,yearList)
        day_spinner.adapter=day_adapter
        month_spinner.adapter=month_adapter
        year_spinner.adapter=yearAdapter
        year_spinner.setSelection(100)

        gender_select.setOnClickListener {btnview->
            Log.d("123","Clicked")
            val gender_select_menu=PopupMenu(context,btnview)
            gender_select_menu.menuInflater.inflate(R.menu.gender_menu,gender_select_menu.menu)
            gender_select_menu.setOnMenuItemClickListener {
                gender_select.text=it.title
                return@setOnMenuItemClickListener true
            }
            gender_select_menu.show()

        }
    }

}