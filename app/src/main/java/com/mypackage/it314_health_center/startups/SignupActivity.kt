package com.mypackage.it314_health_center.startups

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.mypackage.it314_health_center.R
import com.mypackage.it314_health_center.fragments.signup_page_1
import com.mypackage.it314_health_center.fragments.signup_page_2
import com.mypackage.it314_health_center.fragments.signup_page_3

class SignupActivity : AppCompatActivity() {
    private lateinit var page1Fragment:Fragment
    private lateinit var page2Fragment: Fragment
    private lateinit var page3Fragment: Fragment
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentView:FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        page1Fragment=signup_page_1()
        page2Fragment=signup_page_2()
        page3Fragment=signup_page_3()
        fragmentManager=supportFragmentManager
        fragmentView=findViewById(R.id.my_view)
    }

    fun show_page_2()
    {
            val t=fragmentManager.beginTransaction()

            t.replace(R.id.my_view,page2Fragment).commit()
    }

    fun show_page_3()
    {
        val t=fragmentManager.beginTransaction()

        t.replace(R.id.my_view,page3Fragment).commit()
    }

    fun show_page_1()
    {
        val t=fragmentManager.beginTransaction()

        t.replace(R.id.my_view,page1Fragment).commit()
    }
}