package com.mypackage.it314_health_center.fragments.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.mypackage.it314_health_center.BookAppointment
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.databinding.FragmentHomeBinding
import com.mypackage.it314_health_center.patient_side.my_prescriptions

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var myprescriptionview:MaterialCardView
    private lateinit var bookAppointmentView:MaterialCardView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        myprescriptionview=root.findViewById(R.id.my_prescription_view)
        bookAppointmentView=root.findViewById(R.id.book_appointment_view)
        myprescriptionview.setOnClickListener {
            startActivity(Intent(context,my_prescriptions::class.java))
//            (context as Activity).finish()
        }
        bookAppointmentView.setOnClickListener {
            startActivity(Intent(context, BookAppointment::class.java))
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}