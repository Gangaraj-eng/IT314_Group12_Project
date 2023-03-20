package com.mypackage.it314_health_center.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.startups.SignupActivity


class signup_page_1 : Fragment() {

    private lateinit var next_btn:Button
    private lateinit var view:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_signup_page_1, container, false)
        next_btn=view.findViewById(R.id.next_btn)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next_btn.setOnClickListener {
            (activity as SignupActivity).show_page_2()
        }
    }
}