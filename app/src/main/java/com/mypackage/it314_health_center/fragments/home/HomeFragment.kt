package com.mypackage.it314_health_center.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.mypackage.it314_health_center.ActivityUpdateMedicalHistory
import com.mypackage.it314_health_center.BookAppointment
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.databinding.FragmentHomeBinding
import com.mypackage.it314_health_center.patient_side.my_prescriptions
import com.mypackage.it314_health_center.videocalling.PatientOnlineConsultation

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var myprescriptionview: MaterialCardView
    private lateinit var bookAppointmentView: MaterialCardView
    private lateinit var online_consultation_view: MaterialCardView
    private lateinit var updateMedicalHistoryView: MaterialCardView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        myprescriptionview = root.findViewById(R.id.my_prescription_view)
        bookAppointmentView = root.findViewById(R.id.book_appointment_view)
        online_consultation_view = root.findViewById(R.id.online_consultation_view)
        updateMedicalHistoryView=root.findViewById(R.id.update_medical_hist)
        myprescriptionview.setOnClickListener {
            startActivity(Intent(context, my_prescriptions::class.java))
//            (context as Activity).finish()
        }
        bookAppointmentView.setOnClickListener {
            startActivity(Intent(context, BookAppointment::class.java))
        }
        online_consultation_view.setOnClickListener {
            startActivity(Intent(context, PatientOnlineConsultation::class.java))
        }
        updateMedicalHistoryView.setOnClickListener {
            startActivity(Intent(context,ActivityUpdateMedicalHistory::class.java))
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}