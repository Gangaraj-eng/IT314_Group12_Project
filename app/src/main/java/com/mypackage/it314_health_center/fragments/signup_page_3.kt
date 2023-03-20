package com.mypackage.it314_health_center.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.adapters.relative_detail_adapter
import com.mypackage.it314_health_center.models.Relative_detail
import com.mypackage.it314_health_center.startups.SignupActivity


class signup_page_3 : Fragment() {

    private lateinit var relative_recyclerview: RecyclerView
    private lateinit var relativeList: ArrayList<Relative_detail>
    private lateinit var adapter: relative_detail_adapter
    private lateinit var add_btn: Button
    private lateinit var view: View
    private lateinit var signup_btn:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_signup_page_3, container, false)
        relative_recyclerview = view.findViewById(R.id.relative_list_view)
        relativeList = ArrayList()

        relativeList.add(Relative_detail())

        adapter = relative_detail_adapter(context, relativeList)
        relative_recyclerview.adapter = adapter
        relative_recyclerview.layoutManager = LinearLayoutManager(context)
        add_btn = view.findViewById(R.id.add_relative_button)
        signup_btn=view.findViewById(R.id.signup_button)
        signup_btn.setOnClickListener {
            // TODO
            //implement signup logic
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<AppCompatImageButton>(R.id.go_back)
            .setOnClickListener {
                (activity as SignupActivity).show_page_2()
            }
        add_btn.setOnClickListener {
            if (relativeList.size < 4) {
                relativeList.add(Relative_detail())
                adapter.notifyItemInserted(relativeList.size)
                relative_recyclerview.scrollToPosition(relativeList.size - 1)
            } else {
                Toast.makeText(context, "Maximum allowed 4 relatives only", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        
    }

}