package com.mypackage.it314_health_center.fragments.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.BasicAppiontment
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.adapters.past_apt_adapter
import com.mypackage.it314_health_center.databinding.AppointmentHistoryBinding
import com.mypackage.it314_health_center.helpers.dbPaths

class HistoryFragment : Fragment() {

    private var _binding: AppointmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = AppointmentHistoryBinding.inflate(inflater, container, false)
        recyclerView = binding.root.findViewById(R.id.appointment_history_list)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val apt_list = ArrayList<BasicAppiontment>()
        val adapter = past_apt_adapter(context, apt_list)
        recyclerView.adapter = adapter
        FirebaseDatabase.getInstance().reference.child(dbPaths.APPOINTMENTS)
            .child("past").child(FirebaseAuth.getInstance().uid.toString())
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val apt = snapshot.getValue(BasicAppiontment::class.java)
                    if (apt != null) {
                        apt_list.add(apt)
                        adapter.notifyItemInserted(apt_list.size)
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}