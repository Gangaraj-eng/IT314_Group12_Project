package com.mypackage.it314_health_center.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.ActivityOrderMedicines
import com.mypackage.it314_health_center.ActivityUpdateMedicalHistory
import com.mypackage.it314_health_center.BookAppointment
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.databinding.FragmentHomeBinding
import com.mypackage.it314_health_center.helpers.dbPaths
import com.mypackage.it314_health_center.models.Doctor
import com.mypackage.it314_health_center.patient_side.ActivityDownloadReports
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
    private lateinit var downLoadReports:MaterialCardView
    private lateinit var orderMedicines:MaterialCardView
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
        downLoadReports=root.findViewById(R.id.download_reports)
        updateMedicalHistoryView=root.findViewById(R.id.update_medical_hist)
        orderMedicines=root.findViewById(R.id.orderMedicines)

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
        downLoadReports.setOnClickListener {
            startActivity(Intent(context,ActivityDownloadReports::class.java))
        }

        orderMedicines.setOnClickListener {
            startActivity(Intent(context,ActivityOrderMedicines::class.java))
        }
//        addDoctors()
        return root
    }

    private fun addDoctors() {
        val names= arrayOf("DR.Y.K Mishra","Dr. Sandeep","Dr. Rajeev","Dr.Ajay","Dr.Naresh","Dr.Vinod","Dr.Arun","Dr. Meharwal"
        ,"Dr. Nanda Das","Dr. Sarin","Dr. Sandhya","Dr. J.B.Sharma","Dr. Prakash","Dr.Rajul")
        val types= arrayOf("Cardiologist","Cardiologist","Dentist","Dentist",   "General Physician","General Physician","Neurologist","Neurologist",
        "Ophthalmologist","Ophthalmologist","Orthopedic","Orthopedic",  "Psychiatrist","Psychiatrist")
        for(x in 0..13)
        {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(types[x].lowercase()+x%2+1+ "@gmail.com","doctor")
                .addOnSuccessListener {
                    val cdoctor=Doctor(it.user!!.uid,names[x],types[x].lowercase()+x%2+1+ "@gmail.com",types[x])
                    FirebaseDatabase.getInstance().reference.child(dbPaths.DOCTORS)
                        .child(types[x]).child(it.user!!.uid).setValue(cdoctor)
                        .addOnSuccessListener {
                            FirebaseDatabase.getInstance().reference.child(dbPaths.DoctorIds)
                                .child(cdoctor.id).setValue(types[x])
                        }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}