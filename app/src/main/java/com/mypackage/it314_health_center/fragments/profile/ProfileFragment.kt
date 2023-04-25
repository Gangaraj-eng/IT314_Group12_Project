package com.mypackage.it314_health_center.fragments.profile

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.databinding.FragmentProfileBinding
import com.mypackage.it314_health_center.helpers.dbPaths
import com.mypackage.it314_health_center.models.User_Details
import com.mypackage.it314_health_center.startups.Login

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editName = view.findViewById<EditText>(R.id.editName)
//        val editMobile = view.findViewById<EditText>(R.id.editMobile)
        val profileFragment = view.findViewById<FrameLayout>(R.id.fragment_profile)
        val editAddress1 = view.findViewById<EditText>(R.id.editAddress1)
        val editAddress2 = view.findViewById<EditText>(R.id.editAddress2)
        val editAge = view.findViewById<EditText>(R.id.editAge)
        val editGender = view.findViewById<EditText>(R.id.editGender)
        val editDate = view.findViewById<EditText>(R.id.editDate)
        val editState = view.findViewById<EditText>(R.id.editState)
        val editPinCode = view.findViewById<EditText>(R.id.editPinCode)
        val editCountry = view.findViewById<EditText>(R.id.editCountry)
        val editCity = view.findViewById<EditText>(R.id.editCity)
        val button = view.findViewById<Button>(R.id.button)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val linear_layout = view.findViewById<LinearLayout>(R.id.linear_layout)
        val constLayout = view.findViewById<ConstraintLayout>(R.id.details_part_1)



        progressBar.visibility = View.VISIBLE
        constLayout.visibility = View.GONE

        editName.isEnabled = false
        editAddress1.isEnabled = false
        editAddress2.isEnabled = false
        editAge.isEnabled = false
        editGender.isEnabled = false
        editDate.isEnabled = false
        editState.isEnabled = false
        editPinCode.isEnabled = false
        editCountry.isEnabled = false
        editCity.isEnabled = false

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        val user_details = FirebaseDatabase.getInstance().getReference(dbPaths.USERS).child(userId)
            .child(dbPaths.USER_DETAILS).get().addOnSuccessListener {
                val aptcurr = it.getValue(User_Details::class.java)

                editName.setText(aptcurr?.userName.toString())
                editDate.setText(aptcurr?.date_of_birth.toString())
                editAge.setText(aptcurr?.age.toString())
                editGender.setText(aptcurr?.gender.toString())
                editAddress1.setText(aptcurr?.address_line_1.toString())
                editAddress2.setText(aptcurr?.address_line_2.toString())
                editState.setText(aptcurr?.state.toString())
                editPinCode.setText(aptcurr?.pincode.toString())
                editCity.setText(aptcurr?.city.toString())
                editCountry.setText(aptcurr?.country.toString())

                progressBar.visibility = View.GONE
                constLayout.visibility = View.VISIBLE
            }

        button.setOnClickListener {

            if (button.text == "UPDATE") {
                button.text = "SAVE"
                editName.isEnabled = true
                editAddress1.isEnabled = true
                editAddress2.isEnabled = true
                editAge.isEnabled = true
                editGender.isEnabled = true
                editDate.isEnabled = true
                editState.isEnabled = true
                editPinCode.isEnabled = true
                editCountry.isEnabled = true
                editCity.isEnabled = true
            } else {

                // verify details first
                if (editName.text.toString().isEmpty()) {
                    editName.error = "Enter a valid name"
                    return@setOnClickListener
                } else if (checkAge(editAge.text.toString().toInt())) {
                    editAge.error = "Enter a valid age"
                    return@setOnClickListener
                } else if (editAddress1.text.toString().isEmpty() && editAddress2.text.toString()
                        .isEmpty()
                ) {
                    editAddress1.error = "Enter a valid address"
                    return@setOnClickListener
                } else if (editDate.text.toString().isEmpty()) {
                    editDate.error = "Enter a valid date"
                    return@setOnClickListener
                } else if (editState.toString().isEmpty()) {
                    editState.error = "Enter a valid state"
                    return@setOnClickListener
                } else if (editPinCode.toString().isEmpty()) {
                    editPinCode.error = "Enter a valid pin code"
                    return@setOnClickListener
                } else if (editCity.toString().isEmpty()) {
                    editCity.error = "Enter a valid city"
                    return@setOnClickListener
                } else if (editCountry.text.toString().isEmpty()) {
                    editCountry.error = "Enter a valid country"
                    return@setOnClickListener
                } else {
                    // save details
                    val new_details = User_Details(
                        editName.text.toString(),
                        editDate.text.toString(),
                        editAge.text.toString().toInt(),
                        editGender.text.toString(),
                        editAddress1.text.toString(),
                        editAddress2.text.toString(),
                        editCity.text.toString(),
                        editState.text.toString(),
                        editCountry.text.toString(),
                        editPinCode.text.toString().toInt()
                    )

                    FirebaseDatabase.getInstance().getReference(dbPaths.USERS).child(userId)
                        .child(dbPaths.USER_DETAILS).setValue(new_details).addOnSuccessListener {
                            Handler(Looper.getMainLooper()).postDelayed({
                                button.text = "UPDATE"
                                editName.isEnabled = false
                                editAddress1.isEnabled = false
                                editAddress2.isEnabled = false
                                editAge.isEnabled = false
                                editGender.isEnabled = false
                                editDate.isEnabled = false
                                editState.isEnabled = false
                                editPinCode.isEnabled = false
                                editCountry.isEnabled = false
                                editCity.isEnabled = false
                            }, 1000)
                        }

                    button.text = "UPDATE"
                    editName.isEnabled = false
                    editAddress1.isEnabled = false
                    editAddress2.isEnabled = false
                    editAge.isEnabled = false
                    editGender.isEnabled = false
                    editDate.isEnabled = false
                    editState.isEnabled = false
                    editPinCode.isEnabled = false
                    editCountry.isEnabled = false
                    editCity.isEnabled = false
                }
            }
        }
    }


    private fun checkMobile(phone: String): Boolean {
        if (phone.isNotEmpty() && Login.isValidMobile(phone) && phone.length == 10) {
            return true
        }
        return false
    }


    private fun checkAge(age: Int): Boolean {
        return age.toString().length != 2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}